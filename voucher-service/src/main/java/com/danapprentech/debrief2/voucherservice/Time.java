package com.danapprentech.debrief2.voucherservice;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@Configuration
public class Time {

    @PostConstruct
    public void init() {

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        System.out.println("Date in UTC: " + new Date().toString());
    }
}
