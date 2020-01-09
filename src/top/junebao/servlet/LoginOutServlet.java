package top.junebao.servlet;

import top.junebao.interceptor.Auth;
import top.junebao.utils.JsonResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginOutServlet")
public class LoginOutServlet extends HttpServlet {
    /**
     * 登出， 需要认证
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean auth = Auth.auth(request, response);
        String id = null;
        if(!auth){
            JsonResponse.jsonResponse(response, 401, "您还没登录");
        } else{
            HttpSession session = request.getSession();
            session.invalidate();
            JsonResponse.jsonResponse(response);
        }
    }
}
