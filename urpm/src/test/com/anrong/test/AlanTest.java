package com.anrong.test;

import com.anrong.urpm.util.JacksonUtil;

import java.util.HashMap;

public class AlanTest {
    public static void main(String[] args) {
        String json = "{\"phoneNumber\":\"18610348075\",\"purePhoneNumber\":\"18610348075\",\"countryCode\":\"86\",\"watermark\":{\"timestamp\":1535545065,\"appid\":\"wx90c686a8d5f38c1c\"}}";
        HashMap hashMap = JacksonUtil.readValue(json,HashMap.class);
        System.out.println((String)hashMap.get("phoneNumber"));
    }
}
