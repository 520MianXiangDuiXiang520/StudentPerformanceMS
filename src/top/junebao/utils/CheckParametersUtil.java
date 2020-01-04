package top.junebao.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 一个用来检查参数合法性的工具类
public class CheckParametersUtil {
    public static boolean checkStringLen(String string, int minLen, int maxLen) {
        if(string == null){
            return false;
        } else return string.length() <= maxLen && string.length() >= minLen;
    }
    public static boolean checkId(String id) {
        return checkStringLen(id, 1, 10);
    }

    public static  boolean checkName(String name) {
        return checkStringLen(name, 1, 10);
    }

    public static boolean checkSex(String sex) {
        return "男".equals(sex) || "女".equals(sex);
    }

    public static boolean checkTel(String tel) {
        if(!checkStringLen(tel, 11, 11)){
            return false;
        }
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

    public static boolean checkStudentClass(String sc) {
        return checkStringLen(sc, 1, 10);
    }

    public static boolean checkMagor(String magor) {
        return checkStringLen(magor, 1, 10);
    }

    public static boolean checkDept(String dept) {
        return checkStringLen(dept, 1, 20);
    }

    public static boolean checkJobTitle(String jobTitle) {
        return checkStringLen(jobTitle, 1, 8);
    }

    public static boolean checkDegree(String degree) {
        return checkStringLen(degree, 1, 8);
    }

    /**
     * 判断请求体中有没有需要的全部参数
     * @param map 请求体中参数键值组成的map集合
     * @param needFields 需要的所有参数的字符串列表
     * @return 如果有needFields中的所有参数，返回true，否则返回false
     */
    public static boolean checkRequestParam(Map<String, Object> map, List<String> needFields) {
        for (String need:needFields) {
            Object obj = map.get(need);
            if(obj == null){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个field是不是某个类的属性
     * @param classT Class
     * @param field String
     * @return 是返回true，否则返回false
     */
    public static boolean checkFieldIsInClass(Class classT, String field){
        if("id".equals(field)){
            return false;
        }
        try {
            Field field1 = classT.getField(field);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    /**
     * 检查某个键对应的值是否合法
     * @param key
     * @param value
     * @return
     */
    public static boolean checkValueByKey(String key, String value) {
        String name = "check" + key.substring(0, 1).toUpperCase() + key.substring(1);
        System.out.println(name);
        try {
            Method method = CheckParametersUtil.class.getMethod(name, String.class);
            method.invoke(new CheckParametersUtil(),value);
            return true;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
}
