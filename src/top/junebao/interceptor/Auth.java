package top.junebao.interceptor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Auth{
    public static boolean auth(HttpServletRequest request, HttpServletResponse response){
//        1. 判断session是否存在
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        System.out.println(user);
        if(user == null) {
            // 如果session不存在，设置状态码为401
            response.setStatus(401);
            return false;
        } else {
            request.setAttribute("user", user);
            return true;
        }
    }
}
