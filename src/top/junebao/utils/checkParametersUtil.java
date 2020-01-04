package top.junebao.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 一个用来检查参数合法性的工具类
public class checkParametersUtil {
    private static boolean checkStringLen(String string, int minLen, int maxLen) {
        if(string == null){
            return false;
        } else return string.length() <= maxLen && string.length() >= minLen;
    }
    public static boolean checkId(String id) {
        return checkStringLen(id, 1, 10);
    }

    public static boolean checkName(String name) {
        return checkStringLen(name, 1, 10);
    }

    public static boolean checkSex(String sex) {
        return "男".equals(sex) || "女".equals(sex);
    }

    public static boolean checkTel(String tel) {
        String regExp = "^(1)\\d{10}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(tel);
        return m.matches();
    }

    public static boolean checkPlace(String place) {
        return checkStringLen(place, 2, 60);
    }

    public static boolean checkPassword(String password) {
        return checkStringLen(password, 6, 10);
    }
}
