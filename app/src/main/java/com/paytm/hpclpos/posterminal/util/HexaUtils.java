package com.paytm.hpclpos.posterminal.util;

import android.util.Log;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HexaUtils {
    private HexaUtils() {
    }

    private static final int NUMBER_24 = 24;

    public static byte[] HexStringToByteArray(String s) throws IllegalArgumentException {
        int len = s.length();
        if (len % 2 == 1) {
            throw new IllegalArgumentException("Hex string must have even number of characters");
        }
        byte[] data = new byte[len / 2]; // Allocate 1 byte per 2 hex characters
        for (int i = 0; i < len; i += 2) {
            // Convert each character into a integer (base-16), then bit-shift into place
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String ByteArrayToHexString(byte[] bytes) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2]; // Each byte has two hex characters (nibbles)
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF; // Cast bytes[j] to int, treating as unsigned value
            hexChars[j * 2] = hexArray[v >>> 4]; // Select hex character from upper nibble
            hexChars[j * 2 + 1] = hexArray[v & 0x0F]; // Select hex character from lower nibble
        }
        return new String(hexChars);
    }

    public static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    public static String getCardNameByTrack1(String track1) throws NullPointerException {
        String name = track1.substring(track1.indexOf('^')+1, track1.indexOf('^') + NUMBER_24);
        Log.d("HexaUtils","CardHolderName -> "+name);
        return name.trim();
    }

    public static String getVehicleNumberByTrack1(String track1) {
        String name = track1.substring(track1.lastIndexOf('^') - NUMBER_24, track1.lastIndexOf('^'));
        return name.trim();
    }

    public static String stringToHexDecimal(String str) {
        StringBuffer sb = new StringBuffer();
        //Converting string to character array
        char ch[] = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            String hexString = Integer.toHexString(ch[i]);
            sb.append(hexString);
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public static byte[] concatByteArray(byte[] first,byte[] second,byte[] third) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(first);
        outputStream.write(second);
        outputStream.write(third);
        byte[] result = outputStream.toByteArray();
        System.out.println(new String(result));
        return result;
    }

    public static byte[] concatByteArray(byte[] first,byte[] second) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(first);
        outputStream.write(second);
        byte[] result = outputStream.toByteArray();
        return result;
    }

    public static String byte2HexStr(byte[] data) {
        if (data == null || data.length <= 0) {
            return null;
        }
        try {
            String stmp = "";
            StringBuilder sb = new StringBuilder("");
            for (int n = 0; n < data.length; n++) {
                stmp = Integer.toHexString(data[n] & 0xFF);
                sb.append(stmp.length() == 1 ? "0" + stmp : stmp);
            }
            return sb.toString().toUpperCase().trim();
        } catch (Exception e) {
        }
        return null;
    }

    public static byte[] hexStr2Bytes(String src) {
        if (src == null || src.length() <= 0) {
            return null;
        }
        try {
            int m = 0;
            int n = 0;
            if (src.length() % 2 != 0) {
                src = "0" + src;
            }
            int l = src.length() / 2;

            byte[] ret = new byte[l];
            for (int i = 0; i < l; i++) {
                m = i * 2 + 1;
                n = m + 1;
                ret[i] = Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, n)).byteValue();
            }
            return ret;
        } catch (Exception e) {
        }
        return null;
    }

    public static String str2Asc(String str) throws UnsupportedEncodingException {
        if (str == null || str.length() <= 0) return null;

        String ascStr = "";
        for (int i = 0; i < str.length(); i++) {
            ascStr += String.format("%02X", str.substring(i, i + 1).getBytes("ISO-8859-1")[0]);
        }
        return ascStr;
    }
}
