package top.junebao.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class SetType {
    public static void set(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("text/json;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
    }
}
