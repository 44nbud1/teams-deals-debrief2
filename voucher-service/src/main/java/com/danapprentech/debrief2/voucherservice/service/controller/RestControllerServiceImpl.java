package com.danapprentech.debrief2.voucherservice.service.controller;

import com.danapprentech.debrief2.voucherservice.exception.controller.CreateVoucherValidation;
import com.danapprentech.debrief2.voucherservice.exception.NotFoundException;
import com.danapprentech.debrief2.voucherservice.exception.controller.FilterByStatusValidation;
import com.danapprentech.debrief2.voucherservice.exception.validate.ValidationRegexImpl;
import com.danapprentech.debrief2.voucherservice.model.Merchant;
import com.danapprentech.debrief2.voucherservice.model.MerchantCategory;
import com.danapprentech.debrief2.voucherservice.model.Voucher;
import com.danapprentech.debrief2.voucherservice.model.request.UpdateVoucherRequest;
import com.danapprentech.debrief2.voucherservice.model.request.VoucherRequest;
import com.danapprentech.debrief2.voucherservice.model.response.MessageResponse;
import com.danapprentech.debrief2.voucherservice.model.response.VoucherResponse;
import com.danapprentech.debrief2.voucherservice.rabbit.producer.RabbitMqProducer;
import com.danapprentech.debrief2.voucherservice.repository.MerchantCategoryRepository;
import com.danapprentech.debrief2.voucherservice.repository.MerchantRepository;
import com.danapprentech.debrief2.voucherservice.repository.VoucherRepository;
import com.danapprentech.debrief2.voucherservice.scheduler.UpdateExpiredVoucher;
import com.danapprentech.debrief2.voucherservice.service.VoucherOutletServiceImpl;
import org.json.simple.JSONArray;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class RestControllerServiceImpl implements RestControllerService{
    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    VoucherOutletServiceImpl voucherOutletService;

    @Autowired
    MerchantRepository merchantRepository;


    @Autowired
    MerchantCategoryRepository merchantCategoryRepository;

    @Autowired
    RabbitMqProducer rabbitMqProducer;

    @Autowired
    ValidationRegexImpl validation;

    @Autowired
    UpdateExpiredVoucher updateExpiredVoucher;

    @Autowired
    FilterByStatusValidation filterByStatusValidation;

    @Autowired
    CreateVoucherValidation createVoucherValidation;

    private static final Logger logger = LoggerFactory.getLogger(RestControllerServiceImpl.class);

    @Autowired
    private Scheduler scheduler;

    @Override
    public ResponseEntity<?> createVoucher(Long idMerchant,
                                           String idUser,
                                           VoucherRequest voucherRequest,
                                           HttpServletRequest httpServletRequest)
    {
        if (voucherRequest.getVoucherName() == null && voucherRequest.getQuota()==null && voucherRequest.getMaxDiscount() ==null)
        {
            return new ResponseEntity<>(new MessageResponse("Your data is invalid.","057","/api/admin/"+idUser+"/merchant/"+idMerchant+"/vouchers",new Date()),
                    HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<?> checkValid = createVoucherValidation.createVoucher(idMerchant, idUser,
                voucherRequest, httpServletRequest);


        if (!checkValid.getStatusCode().is2xxSuccessful())
        {
            return checkValid;
        }

                if (voucherRequest.getExpiredDate() == null)
        {
            return new ResponseEntity<>(new MessageResponse("Please fill form expired date.","057","/api/admin/"+idUser+"/merchant/"+idMerchant+"/vouchers",new Date()),
                    HttpStatus.BAD_REQUEST);
        }

        updateExpiredVoucher.setTimeSchedule(voucherRequest);

        return voucherOutletService.findById(idMerchant)
                .map(merchant -> {
                    Voucher vouchers = new Voucher();
                    vouchers.setIdUser(idUser);
                    vouchers.setMaxDiscount(voucherRequest.getMaxDiscount());
                    vouchers.setExpiredDate(voucherRequest.getExpiredDate());
                    vouchers.setQuota(voucherRequest.getQuota());
                    vouchers.setVoucherPrice(voucherRequest.getVoucherPrice());
                    vouchers.setVoucherName(voucherRequest.getVoucherName());
                    vouchers.setDiscount(voucherRequest.getDiscount());
                    vouchers.setStatus(voucherRequest.getStatus());
                    vouchers.setMerchantName(merchant.getMerchantName());
                    vouchers.setCreateAt(new Date());
                    vouchers.setUpdateAt(new Date());
                    vouchers.setMerchant(merchant);
                    voucherRepository.save(vouchers);
                    System.out.println(voucherRequest.getVoucherName());

                    Voucher voucher = voucherRepository.findByVoucherName(voucherRequest.getVoucherName());

                    // response
                    VoucherResponse voucherResponse = new VoucherResponse();
                    voucherResponse.setVoucherName(voucherRequest.getVoucherName());
                    voucherResponse.setDiscount(voucherRequest.getDiscount());
                    voucherResponse.setVoucherPrice(voucherRequest.getVoucherPrice());
                    voucherResponse.setMaxDiscount(voucherRequest.getMaxDiscount());
                    voucherResponse.setQuota(voucherRequest.getQuota());
                    voucherResponse.setExpiredDate(voucherRequest.getExpiredDate());
                    voucherResponse.setStatus(voucherRequest.getStatus());
                    voucherResponse.setIdMerchant(idMerchant);
                    voucherResponse.setIdVoucher(voucher.getIdVoucher());
                    voucherResponse.setMerchantName(voucher.getMerchant().getMerchantName());

                    List<VoucherResponse> voucherResponses = new ArrayList<>();
                    voucherResponses.add(voucherResponse);
                    Map vouchersRes = new HashMap<>();
                    vouchersRes.put("data", voucherResponses);
                    vouchersRes.put("timestamp", new Date());
                    vouchersRes.put("path", "/api/admin/" + idUser + "/merchant/" + idMerchant + "/vouchers");
                    vouchersRes.put("message", "Create voucher successfully.");
                    vouchersRes.put("status", "042");

                    rabbitMqProducer.sendToRabbitVoucher(voucherResponse);

                    return ResponseEntity.ok(vouchersRes);
                }).orElseThrow(() -> new NotFoundException("id Merchant not found.", "054"));
    }

    @Override
    public ResponseEntity<?> getAllVoucher(
                                            Optional<Integer> page,
                                            String sortBy,
                                            HttpServletRequest httpServletRequest) {

        Page<Voucher> vouchers = voucherRepository.findAll(
                PageRequest.of(page.orElse(0), 10, Sort.Direction.ASC, sortBy));

        List<Page<Voucher>> voucherResponses = new ArrayList<>();
        voucherResponses.add(vouchers);

        List<Voucher> voucherssss = voucherRepository.findAll();

        Map vouchersRes = new HashMap<>();
        vouchersRes.put("data", voucherResponses);
        vouchersRes.put("timestamp", new Date());
        vouchersRes.put("path", "/api/admin/show-all-voucher");
        vouchersRes.put("message", "Vouchers are successfully collected.");
        vouchersRes.put("status", "040");
        return ResponseEntity.ok(vouchersRes);
    }

    @Override
    public ResponseEntity<?> filterByStatus(
                                            Optional<Integer> page,
                                            String filterByStatus,
                                            String sortBy,
                                            HttpServletRequest httpServletRequest)
    {

        Boolean status;
        if (filterByStatus.equalsIgnoreCase("true")) {
            status = Boolean.TRUE;
        } else if (filterByStatus.equalsIgnoreCase("false")) {
            status = Boolean.FALSE;
        } else {
            return new ResponseEntity<>(new MessageResponse("Status invalid.", "063", "/api/admin/filterByStatus-voucher", new Date()),
                    HttpStatus.BAD_REQUEST);
        }


        Page<Voucher> vouchers = voucherRepository.findByStatus(status,
                PageRequest.of(page.orElse(0), 10, Sort.Direction.ASC, sortBy));

        List<Page<Voucher>> voucherResponses = new ArrayList<>();
        voucherResponses.add(vouchers);

        Map vouchersRes = new HashMap<>();
        vouchersRes.put("data", voucherResponses);
        vouchersRes.put("timestamp", new Date());
        vouchersRes.put("path", "/api/admin/filterByStatus-voucher");
        vouchersRes.put("message", "Vouchers are successfully collected.");
        vouchersRes.put("status", "040");

        return ResponseEntity.ok(vouchersRes);
    }

    @Override
    public ResponseEntity<?> findByMerchantName(
                                        Optional<Integer> page,
                                        String merchantName,
                                        String sortBy,
                                        HttpServletRequest httpServletRequest) {
        Merchant merchants = merchantRepository.findByMerchantNameStartsWithIgnoreCase(merchantName);

        if (merchants == null) {
            return new ResponseEntity<>(new MessageResponse("Voucher not found.", "062",
                    "/api/admin/findByMerchantName-voucher", new Date()),
                    HttpStatus.NOT_FOUND);
        }

        List<Voucher> vouc = merchants.getVouchers();
        Pageable pageable = PageRequest.of(page.orElse(0), 10, Sort.Direction.ASC, sortBy);

        Long start = pageable.getOffset();
        Long end = (start + pageable.getPageSize()) > vouc.size() ? vouc.size() : (start + pageable.getPageSize());

        int a = start.intValue();
        System.out.println(a);
        int b = end.intValue();
        System.out.println(b);

        Page<Voucher> pages = new PageImpl<Voucher>(vouc.subList(a, b), pageable, vouc.size());

        List<Page<Merchant>> merchantsResponse = new ArrayList<>();
        List<Voucher> voucherssss = merchants.getVouchers();

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(pages);

        Map merchantResp = new HashMap<>();
        merchantResp.put("data", jsonArray);
        merchantResp.put("timestamp", new Date());
        merchantResp.put("path", "/api/admin/findByMerchantName-voucher");
        merchantResp.put("idMerchant", merchants.getIdMerchant());
        merchantResp.put("merchantName", merchants.getMerchantName());
        merchantResp.put("message", "Vouchers are successfully collected.");
        merchantResp.put("status", "040");
        return ResponseEntity.ok(merchantResp);
    }

    @Override
    public ResponseEntity<?> voucherDetail(Long idVoucher, HttpServletRequest httpServletRequest) {
        if (voucherRepository.findByIdVoucher(idVoucher) == null) {
            return new ResponseEntity<>(new MessageResponse("Voucher not found.", "062", "/api/admin/voucher-detail-voucher/" + idVoucher, new Date()),
                    HttpStatus.NOT_FOUND);
        }

        Voucher vouchers = voucherRepository.findByIdVoucher(idVoucher);
        VoucherResponse voucherResponse = new VoucherResponse();
        voucherResponse.setStatus(vouchers.getStatus());
        voucherResponse.setExpiredDate(vouchers.getExpiredDate());
        voucherResponse.setQuota(vouchers.getQuota());
        voucherResponse.setMaxDiscount(vouchers.getMaxDiscount());
        voucherResponse.setDiscount(vouchers.getDiscount());
        voucherResponse.setIdMerchant(vouchers.getMerchant().getIdMerchant());
        voucherResponse.setVoucherName(vouchers.getVoucherName());
        voucherResponse.setVoucherPrice(vouchers.getVoucherPrice());
        voucherResponse.setMerchantName(vouchers.getMerchantName());
        voucherResponse.setIdVoucher(vouchers.getIdVoucher());

        List<VoucherResponse> merchantsResponse = new ArrayList<>();
        merchantsResponse.add(voucherResponse);

        Map merchantResp = new HashMap<>();
        merchantResp.put("data", merchantsResponse);
        merchantResp.put("timestamp", new Date());
        merchantResp.put("path", "/api/admin/voucher-detail-voucher/" + idVoucher);
        merchantResp.put("message", "Show detail voucher.");
        merchantResp.put("status", "046");

        return ResponseEntity.ok(merchantResp);
    }

    @Override
    public ResponseEntity<?> updateVoucher(
                                            Long idVoucher,
                                            UpdateVoucherRequest updateVoucherRequest,
                                            HttpServletRequest httpServletRequest) {
        Boolean status = null;

        if (updateVoucherRequest.getStatus().equals("true")) {
            status = Boolean.TRUE;
        } else if (updateVoucherRequest.getStatus().equals("false")) {
            status = Boolean.FALSE;
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Status invalid.", "063",
                    "/api/admin/update-status-voucher/{idVoucher}/restock", new Date()));
        }

        Voucher vouchers = voucherRepository.findByIdVoucher(idVoucher);

        if (vouchers == null) {
            return new ResponseEntity<>(new MessageResponse("Voucher not found.", "062",
                    "/admin/update-status-voucher/{idVoucher}/restock", new Date()),HttpStatus.NOT_FOUND);
        }

        if (updateVoucherRequest.getStatus() == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Status invalid.", "063",
                    "/api/admin/update-status-voucher/{idVoucher}/restock", new Date()));
        }

        // only change status
        if (status == true && updateVoucherRequest.getQuota() == null) {

            if (vouchers == null) {
                return new ResponseEntity<>(new MessageResponse("Voucher not found.", "062",
                        "/admin/update-status-voucher/{idVoucher}/restock", new Date()),HttpStatus.NOT_FOUND);
            }

            if (status == false) {
                return ResponseEntity.badRequest().body(new MessageResponse("Status invalid.", "063",
                        "/admin/update-status-voucher/{idVoucher}/restock", new Date()));
            }

            if (vouchers.getQuota() < 5) {
                vouchers.setQuota(5);
            }

            vouchers.setStatus(Boolean.TRUE);
            vouchers.setQuota(vouchers.getQuota());
            vouchers.setUpdateAt(new Date());
            voucherRepository.save(vouchers);

            // kirim keyogi
            Voucher voucher = voucherRepository.findByVoucherName(vouchers.getVoucherName());
            // response
            VoucherResponse voucherResponse = new VoucherResponse();
            voucherResponse.setVoucherName(voucher.getVoucherName());
            voucherResponse.setDiscount(voucher.getDiscount());
            voucherResponse.setVoucherPrice(voucher.getVoucherPrice());
            voucherResponse.setMaxDiscount(voucher.getMaxDiscount());
            voucherResponse.setQuota(voucher.getQuota());
            voucherResponse.setExpiredDate(voucher.getExpiredDate());
            voucherResponse.setStatus(voucher.getStatus());
            voucherResponse.setMerchantName(voucher.getMerchant().getMerchantName());
            voucherResponse.setIdMerchant(voucher.getMerchant().getIdMerchant());
            voucherResponse.setIdVoucher(voucher.getIdVoucher());
            System.out.println("Kirim ke yogi " + voucherResponse);
            rabbitMqProducer.sendToRabbitVoucher(voucherResponse);

            return ResponseEntity.ok(new MessageResponse("Successfully Change Status.", "044",
                    "/admin/update-status-voucher/{idVoucher}/restock", new Date()));
        }

        // only change stock
        if (status == true && updateVoucherRequest.getQuota() != null) {
            if (vouchers.getQuota() + updateVoucherRequest.getQuota() > 1000) {
                return new ResponseEntity<>(new MessageResponse("Maximum voucher quota of 1000.", "068",
                        "/admin/update-status-voucher/" + idVoucher + "/restock", new Date()),
                        HttpStatus.BAD_REQUEST);
            }

            if (vouchers.getStatus() == false) {
                return ResponseEntity.badRequest().body(new MessageResponse("Status invalid.",
                        "063", "/admin/update-status-voucher/{idVoucher}/restock", new Date()));
            }

            if (vouchers == null) {
                return new ResponseEntity<>(new MessageResponse("Voucher not found.", "062",
                        "/admin/update-status-voucher/{idVoucher}/restock", new Date()),HttpStatus.NOT_FOUND);
            }

            if (status == false) {
                return ResponseEntity.badRequest().body(new MessageResponse("Status invalid.", "063",
                        "/admin/update-status-voucher/{idVoucher}/restock", new Date()));
            }

            vouchers.setStatus(Boolean.TRUE);
            vouchers.setQuota(vouchers.getQuota() + updateVoucherRequest.getQuota());
            vouchers.setUpdateAt(new Date());
            voucherRepository.save(vouchers);

            // kirim keyogi
            Voucher voucher = voucherRepository.findByVoucherName(vouchers.getVoucherName());
            // response
            VoucherResponse voucherResponse = new VoucherResponse();
            voucherResponse.setVoucherName(voucher.getVoucherName());
            voucherResponse.setDiscount(voucher.getDiscount());
            voucherResponse.setVoucherPrice(voucher.getVoucherPrice());
            voucherResponse.setMaxDiscount(voucher.getMaxDiscount());
            voucherResponse.setQuota(voucher.getQuota());
            voucherResponse.setExpiredDate(voucher.getExpiredDate());
            voucherResponse.setStatus(voucher.getStatus());
            voucherResponse.setMerchantName(voucher.getMerchant().getMerchantName());
            voucherResponse.setIdMerchant(voucher.getMerchant().getIdMerchant());
            voucherResponse.setIdVoucher(voucher.getIdVoucher());
            rabbitMqProducer.sendToRabbitVoucher(voucherResponse);
            System.out.println("Kirim ke yogi " + voucherResponse);

            return ResponseEntity.ok(new MessageResponse(String.valueOf(voucherResponse.getIdMerchant()), "044",
                    "/admin/update-status-voucher/{idVoucher}/restock", new Date()));
        }

        // only change status
        if (status == false) {
            if (updateVoucherRequest.getQuota() != null) {
                return ResponseEntity.badRequest().body(new MessageResponse("We cannot update the voucher stock due " +
                        "to inactive voucher status.", "045",
                        "/admin/update-status-voucher/{idVoucher}/restock", new Date()));
            }

            vouchers.setStatus(Boolean.FALSE);
            vouchers.setUpdateAt(new Date());
            voucherRepository.save(vouchers);

            // kirim keyogi
            Voucher voucher = voucherRepository.findByVoucherName(vouchers.getVoucherName());
            // response
            VoucherResponse voucherResponse = new VoucherResponse();
            voucherResponse.setVoucherName(voucher.getVoucherName());
            voucherResponse.setDiscount(voucher.getDiscount());
            voucherResponse.setVoucherPrice(voucher.getVoucherPrice());
            voucherResponse.setMaxDiscount(voucher.getMaxDiscount());
            voucherResponse.setQuota(voucher.getQuota());
            voucherResponse.setExpiredDate(voucher.getExpiredDate());
            voucherResponse.setStatus(voucher.getStatus());
            voucherResponse.setIdMerchant(voucher.getMerchant().getIdMerchant());
            voucherResponse.setIdVoucher(voucher.getIdVoucher());
            rabbitMqProducer.sendToRabbitVoucher(voucherResponse);
            System.out.println("Kirim ke yogi " + voucherResponse);

            return ResponseEntity.ok(new MessageResponse("Successfully change status.", "044",
                    "/admin/update-status-voucher/{idVoucher}/restock", new Date()));
        }

//        if (status == false && vouchers.getQuota() != null)
//        {
//            return ResponseEntity.badRequest().body(new MessageResponse("We cannot update the voucher stock due " +
//                    "to inactive voucher status.","045",
//                    "/admin/update-status-voucher/{idVoucher}/restock",new Date()));
//        }

        else {
            return ResponseEntity.badRequest().body(new MessageResponse("Status invalid.", "063",
                    "/admin/update-status-voucher/{idVoucher}/restock", new Date()));
        }

    }

    @Override
    public ResponseEntity<?> filterByMerchantCategory(
                                                        Optional<Integer> page,
                                                        String merchantCategory,
                                                        String sortBy,
                                                        HttpServletRequest httpServletRequest) {
//        Page<MerchantCategory> vouchers = merchantCategoryRepository.findByMerchantCategoryContaining(merchantCategory.orElse("_"),
//                PageRequest.of(page.orElse(0), 10, Sort.Direction.ASC, sortBy));

        System.out.println(merchantCategory);

        if (merchantCategory.isEmpty() || merchantCategory.equalsIgnoreCase("null")) {
            return new ResponseEntity<>(new MessageResponse("Please fill merchant category.", "070",
                    "/api/user/filter-voucher", new Date()),
                    HttpStatus.NOT_FOUND);
        }

        MerchantCategory merchantsCat = merchantCategoryRepository.findByMerchantCategoryContaining(
                merchantCategory);


        if (merchantsCat == null)
        {
            return new ResponseEntity<>(new MessageResponse("Voucher not found.","062",
                    "/api/user/filter-voucher",new Date()),
                    HttpStatus.NOT_FOUND);
        }

        String category = merchantsCat.getMerchantCategory();

        if (category.equalsIgnoreCase("fnb")) {
            Merchant merchants = merchantRepository.findByMerchantNameStartsWithIgnoreCase("kfc");

            List<Voucher> vouc = merchants.getVouchers();
            Pageable pageable = PageRequest.of(page.orElse(0), 10, Sort.Direction.ASC, sortBy);

            Long start = pageable.getOffset();
            Long end = (start + pageable.getPageSize()) > vouc.size() ? vouc.size() : (start + pageable.getPageSize());

            int a = start.intValue();
            System.out.println(a);
            int b = end.intValue();
            System.out.println(b);

            Page<Voucher> pages = new PageImpl<Voucher>(vouc.subList(a, b), pageable, vouc.size());

            List<Page<Merchant>> merchantsResponse = new ArrayList<>();
            List<Voucher> voucherssss = merchants.getVouchers();

            JSONArray jsonArray = new JSONArray();
            jsonArray.add(pages);

            Map merchantResp = new HashMap<>();
            merchantResp.put("data", jsonArray);
            merchantResp.put("timestamp", new Date());
            merchantResp.put("path", "/api/user/filter-voucher");
            merchantResp.put("idMerchantCategory", merchantsCat.getIdMerchantCategory());
            merchantResp.put("merchantCategory", merchantsCat.getMerchantCategory());
            merchantResp.put("message", "Vouchers are successfully collected.");
            merchantResp.put("status", "040");
            return ResponseEntity.ok(merchantResp);
        }

        if (category.equalsIgnoreCase("onlineTransaction")) {
            Merchant merchants = merchantRepository.findByMerchantNameStartsWithIgnoreCase("telkom");

            List<Voucher> vouc = merchants.getVouchers();
            Pageable pageable = PageRequest.of(page.orElse(0), 10, Sort.Direction.ASC, sortBy);

            Long start = pageable.getOffset();
            Long end = (start + pageable.getPageSize()) > vouc.size() ? vouc.size() : (start + pageable.getPageSize());

            int a = start.intValue();
            System.out.println(a);
            int b = end.intValue();
            System.out.println(b);

            Page<Voucher> pages = new PageImpl<Voucher>(vouc.subList(a, b), pageable, vouc.size());

            List<Page<Merchant>> merchantsResponse = new ArrayList<>();
            List<Voucher> voucherssss = merchants.getVouchers();

            JSONArray jsonArray = new JSONArray();
            jsonArray.add(pages);

            Map merchantResp = new HashMap<>();
            merchantResp.put("data", jsonArray);
            merchantResp.put("timestamp", new Date());
            merchantResp.put("path", "/api/user/filter-voucher");
            merchantResp.put("idMerchantCategory", merchantsCat.getIdMerchantCategory());
            merchantResp.put("merchantCategory", merchantsCat.getMerchantCategory());
            merchantResp.put("message", "Vouchers are successfully collected.");
            merchantResp.put("status", "040");
            return ResponseEntity.ok(merchantResp);
        }
        return new ResponseEntity<>(new MessageResponse("Voucher not found.","062",
                "/api/user/filter-voucher",new Date()),
                HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> SortByMerchantName(
                                                    Optional<Integer> page,
                                                    Optional<String> sortBy,
                                                    HttpServletRequest httpServletRequest)
    {
        if (sortBy == null)
        {
            return new ResponseEntity<>(new MessageResponse("Please fill sort criteria.","064",
                    "/api/user/sort-voucher",new Date()),
                    HttpStatus.BAD_REQUEST);
        }

        if (voucherRepository.findAll() == null)
        {
            return new ResponseEntity<>(new MessageResponse("Voucher not found.","062",
                    "/api/user/sort-voucher",new Date()),
                    HttpStatus.NOT_FOUND);
        }

        Page<Voucher> merchants = voucherRepository.findAll(
                PageRequest.of(page.orElse(0), 10, Sort.Direction.DESC, sortBy.orElse("voucherName")));

        List<Page<Voucher>> merchantsResponse = new ArrayList<>();
        merchantsResponse.add(merchants);

        Map merchantResp = new HashMap<>();
        merchantResp.put("data",merchantsResponse);
        merchantResp.put("message","Successfully");
        merchantResp.put("status","200");

        return ResponseEntity.ok(merchantResp);
    }

}