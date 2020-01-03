package top.junebao.test;

import top.junebao.dao.UserDao;
import top.junebao.domain.User;
import top.junebao.utils.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        String params = sb.toString();

        System.out.println(params);
        User loginUser = (User) JSONUtil.formDataToObject(params, User.class);
        System.out.println(loginUser);
        UserDao userDao = new UserDao();
        User user = userDao.login(loginUser);
        if(user != null){
            Map<String, Object> result = new HashMap<>();
            result.put("code", "200");
            result.put("data", user);
            // 登陆成功，设置session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            String jsonResp = JSONUtil.toJSON(result);
            System.out.println("==========success==========");
            response.getWriter().write(jsonResp);
        } else {
            Map<String, Object>  fileResult = new HashMap<>();
            fileResult.put("code", "401");
            fileResult.put("msg", "用户名或密码错误！");
            response.getWriter().write(JSONUtil.toJSON(fileResult));
        }
//        response.getWriter().write(JSONUtil.toJSON(loginUser));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=UTF-8");
        User user = new User();
        user.setSex("nan");
        user.setId("jjj");
        user.setName("zhan");
        user.setPlace("lllll");
        response.getWriter().write(JSONUtil.toJSON(user));
    }
}
