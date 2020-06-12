package com.okta.examples.adapter.parser;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Parser {

    public static JSONObject parseJSON(String data){
        JSONParser parser = new JSONParser();
        JSONObject json = new JSONObject();
        try {
            json = (JSONObject) parser.parse(data);
        } catch (ParseException e){
            System.out.println(e.getLocalizedMessage());
        }
        return json;
    }

    public static String toJsonString(Object data){
        Gson gson = new Gson();
        return gson.toJson(data);
    }
}
