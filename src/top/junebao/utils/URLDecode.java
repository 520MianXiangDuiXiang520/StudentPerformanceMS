package top.junebao.utils;

import java.io.UnsupportedEncodingException;

public class URLDecode {
    public static String getURLDecoderString(String str) {
        return getString(str);
    }

    private static String getString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
