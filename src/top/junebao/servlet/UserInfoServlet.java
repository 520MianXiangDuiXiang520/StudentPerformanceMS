package top.junebao.servlet;

import top.junebao.domain.User;
import top.junebao.interceptor.Auth;
import top.junebao.utils.JSONUtil;
import top.junebao.utils.JsonResponse;
import top.junebao.utils.SetType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UserInfoServlet")
public class UserInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);
        boolean auth = Auth.auth(request, response);
        if(!auth){
            JsonResponse.jsonResponse(response, 401, "您还没登录");
        } else{
            JsonResponse.jsonResponse(response, request.getAttribute("user"));
        }
    }
}
