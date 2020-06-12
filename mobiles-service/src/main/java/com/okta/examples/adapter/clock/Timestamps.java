package com.okta.examples.adapter.clock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Timestamps {
    public static String getNow(){
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();

        return sdf.format(date);
    }
}
