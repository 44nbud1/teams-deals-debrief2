//package com.danapprentech.debrief2.voucherservice.restcontroller;
//
//import com.danapprentech.debrief2.voucherservice.service.controller.RestControllerServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/user")
//public class RestControllerVoucherUser
//{
//
//    @Autowired
//    RestControllerServiceImpl restControllerService;
//
//    @GetMapping("/show-all-voucher")
//    public ResponseEntity<?> getAllVoucher(
//            @RequestParam Optional<Integer> page,
//            @RequestParam(defaultValue = "voucherName") String sortBy,
//            HttpServletRequest httpServletRequest)
//    {
//
//        return restControllerService.getAllVoucher(page,sortBy,httpServletRequest);
//    }
//
//    // /api/user/filter-voucher?merchantCategory={category}&page={page}
//    @GetMapping("/filter-voucher")
//    public ResponseEntity<?> filterByMerchantCategory(
//            @RequestParam Optional<Integer> page,
//            @RequestParam Optional<String> merchantCategory,
//            @RequestParam(defaultValue = "merchantCategory") String sortBy,
//            HttpServletRequest httpServletRequest)
//    {
//        return restControllerService.filterByMerchantCategory(page,merchantCategory,sortBy,httpServletRequest);
//    }
//
//    ///api/user/search-voucher?nameVoucher={name}&page={page}
//    @GetMapping("/findByMerchantName-voucher")
//    public ResponseEntity<?> findByMerchantName(
//            @RequestParam Optional<Integer> page,
//            @RequestParam String merchantName,
//            @RequestParam(defaultValue = "merchantName") String sortBy,
//            HttpServletRequest httpServletRequest)
//    {
//        return restControllerService.findByMerchantName(page,merchantName,sortBy,httpServletRequest);
//    }
//
//    ///api/user/sort-voucher?sortBy={name}&page={page} //
//    // discount voucherPrice
//    @GetMapping("/sort-voucher")
//    public ResponseEntity<?> SortByMerchantName(
//            @RequestParam Optional<Integer> page,
//            @RequestParam Optional<String> sortBy,
//            HttpServletRequest httpServletRequest)
//    {
//        return restControllerService.SortByMerchantName(page,sortBy,httpServletRequest);
//    }
//}
