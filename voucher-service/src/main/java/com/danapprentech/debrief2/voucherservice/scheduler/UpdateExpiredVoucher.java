package com.danapprentech.debrief2.voucherservice.scheduler;

import com.danapprentech.debrief2.voucherservice.model.Voucher;
import com.danapprentech.debrief2.voucherservice.model.request.VoucherRequest;
import com.danapprentech.debrief2.voucherservice.model.response.MessageResponse;
import com.danapprentech.debrief2.voucherservice.model.response.VoucherResponse;
import com.danapprentech.debrief2.voucherservice.rabbit.producer.RabbitMqProducer;
import com.danapprentech.debrief2.voucherservice.repository.VoucherRepository;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Component
public class UpdateExpiredVoucher extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(UpdateExpiredVoucher.class);

    @Autowired
    RabbitMqProducer mqProducer;

    @Autowired
    Scheduler scheduler;

    @Autowired
    VoucherRepository voucherRepository;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String voucherName = jobDataMap.getString("voucherName");

        updateStatusExp(voucherName);
    }

    private void updateStatusExp(String vouchers)
    {
        logger.info("Update status to in active");

        Voucher voucher = voucherRepository.findByVoucherName(vouchers);
        voucher.setStatus(false);
        voucherRepository.save(voucher);
        System.out.println("update voucher");

        // kirim keyogi
        Voucher vouchersConsumer = voucherRepository.findByVoucherName(vouchers);
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
        mqProducer.sendToRabbitVoucher(voucherResponse);
    }

    public void setTimeSchedule(VoucherRequest voucherRequest)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String expDate = dateFormat.format(voucherRequest.getExpiredDate());


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime expDateTime = LocalDateTime.parse(expDate, dtf);
        ZoneId timeZone = ZoneId.of("Asia/Jakarta");

        System.out.println(expDateTime);

        try {
            ZonedDateTime dateTime = ZonedDateTime.of(expDateTime, timeZone);
            JobDetail jobDetail = buildJobDetail(voucherRequest);
            Trigger trigger = buildJobTrigger(jobDetail, dateTime);
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException ex) {
            logger.error("Error scheduling email", ex);

        }

    }

        private JobDetail buildJobDetail(VoucherRequest voucherRequest) {

        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("voucherName", voucherRequest.getVoucherName());

        return JobBuilder.newJob(UpdateExpiredVoucher.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("InActive Voucher")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Email Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
