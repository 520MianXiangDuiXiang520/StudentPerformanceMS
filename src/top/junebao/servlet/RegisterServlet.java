package top.junebao.servlet;

import top.junebao.dao.UserDao;
import top.junebao.domain.Student;
import top.junebao.domain.User;
import top.junebao.utils.JSONUtil;
import top.junebao.utils.JsonResponse;
import top.junebao.utils.SetType;
import top.junebao.utils.URLDecode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private UserDao userDao = new UserDao();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);
        String fromDataString = JSONUtil.toFormDataString(request);
        Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
        // 首先确保请求体中包含id,password,passAgain,name, type这四个字段，并且都不为空。
        System.out.println(map.toString());
        Object id = map.get("id");
        Object password1 = map.get("password");
        Object passAgain1 = map.get("passAgain");
        Object nameObj = map.get("name");
        Object typeObj = map.get("type");
        if(id == null || passAgain1 == null || password1 == null || nameObj == null || typeObj == null) {
            JsonResponse.jsonResponse(response, 400, "参数错误！");
        } else {
            String user_id = URLDecode.getURLDecoderString(id.toString());
            String password = URLDecode.getURLDecoderString(password1.toString());
            String passAgain = URLDecode.getURLDecoderString(passAgain1.toString());
            String name = URLDecode.getURLDecoderString(nameObj.toString());
            String type = URLDecode.getURLDecoderString(typeObj.toString());
            String table;
            // 全是if-else，还是python大法好啊
            if("0".equals(type)){
                table = "teacher";
            }else if("1".equals(type)) {
                table = "student";
            } else {
                table = null;
            }
            // TODO: 不应该这样用多个 if-else
            if(!password.equals(passAgain)) {
                JsonResponse.jsonResponse(response, 400, "两次密码不一致！");
            } else {
                // 判断密码长度是否合法
                if(password.length()<7 ||password.length()>12){
                    JsonResponse.jsonResponse(response, 400, "密码长度限制在7-12位");
                } else {
                    // 判断用户名长度是否合法
                    if(name.length()<1 || name.length() > 10) {
                        JsonResponse.jsonResponse(response, 400, "姓名长度限制在1-10位");
                    } else {
                        // 判断id长度是否合法
                        if(user_id.length() < 1 || user_id.length() > 10) {
                            JsonResponse.jsonResponse(response, 400, "id长度限制在1-10位");
                        } else {
                            if(table == null) {
                                JsonResponse.jsonResponse(response, 400, "参数错误！！！！！");
                            } else {
                                User newUser = new User(user_id, password);
                                newUser.setName(name);
                                // TODO: 只完成了学生注册
                                User obj = userDao.userRegister(table, newUser);
                                if(obj == null) {
                                    JsonResponse.jsonResponse(response, 400, "注册失败！");
                                } else {
                                    // 到这说明已经存到数据库里面了，就应该设置Session了
                                    HttpSession session = request.getSession();
                                    session.setAttribute("user", obj);
                                    JsonResponse.jsonResponse(response, 200, obj,"ok");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);
        response.getWriter().write("<a href='juneBao.top'>junebao.top</a>");
    }
}
