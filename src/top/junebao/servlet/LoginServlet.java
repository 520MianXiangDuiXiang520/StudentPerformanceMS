package top.junebao.servlet;
import org.apache.commons.beanutils.BeanUtils;
import top.junebao.dao.UserDao;
import top.junebao.domain.User;
import top.junebao.utils.JSONUtil;
import top.junebao.utils.SetType;

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
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private UserDao userDao = null;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SetType.set(req, resp);
        String params = JSONUtil.toFormDataString(req);
        System.out.println(params);
        User loginUser = (User) JSONUtil.formDataToObject(params, User.class);

        System.out.println(loginUser);
        userDao = new UserDao();
        User user = userDao.login(loginUser);
        if(user != null){
            Map<String, Object>  result = new HashMap<>();
            result.put("code", "200");
            result.put("data", user);
            // 登陆成功，设置session
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            String jsonResp = JSONUtil.toJSON(result);
            System.out.println("==========success==========");
            resp.getWriter().write(jsonResp);
        } else {
            Map<String, Object>  fileResult = new HashMap<>();
            fileResult.put("code", "401");
            fileResult.put("msg", "用户名或密码错误！");
            resp.getWriter().write(JSONUtil.toJSON(fileResult));
        }
    }
}
