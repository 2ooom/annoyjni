package com.criteo.annoy;

import java.nio.charset.StandardCharsets;

public class Utils {
    /**
     * Adds ternminal 0 for char* in C string which is passed as byte[]
     * @param str - arbitrary string
     * @return byte[] with last item 0
     */
    public static byte[] toCharBytes(String str) {
        return (str + '\0').getBytes(StandardCharsets.US_ASCII);
    }
}
