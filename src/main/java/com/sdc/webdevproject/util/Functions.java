package com.sdc.webdevproject.util;

import java.util.Base64;

public class Functions {
    public static String base64Encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
}
