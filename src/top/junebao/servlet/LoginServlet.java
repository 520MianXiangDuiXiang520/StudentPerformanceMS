package top.junebao.servlet;
import org.apache.commons.beanutils.BeanUtils;
import top.junebao.dao.UserDao;
import top.junebao.domain.User;
import top.junebao.utils.JSONUtil;
import top.junebao.utils.JsonResponse;
import top.junebao.utils.SetType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
        Object play = req.getAttribute("play");
        if("student".equals(play.toString()) || "teacher".equals(play.toString()) || "superUser".equals(play.toString())){
            userDao = new UserDao();
            User user = userDao.login(loginUser, play.toString());
            toLogin(req, resp, user);
        } else {
            JsonResponse.jsonResponse(resp, 403, "该接口不向用户开放");
        }


    }

    public static void toLogin(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        if(user != null){
            Map<String, Object> result = new HashMap<>();
            // 登陆成功，设置session
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            System.out.println("==========success==========");
            JsonResponse.jsonResponse(resp, 200, user, "ok");
        } else {
            System.out.println("=================用户名或密码错误=================");
            JsonResponse.jsonResponse(resp, 401, "用户名或密码错误");

        }
    }
}
