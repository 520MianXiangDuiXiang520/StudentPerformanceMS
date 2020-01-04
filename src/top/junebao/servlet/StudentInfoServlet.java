package top.junebao.servlet;

import top.junebao.dao.StudentDao;
import top.junebao.domain.Student;
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

@WebServlet("/StudentInfoServlet")
public class StudentInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 判断用户是否登录
        // 2. 通过session获取用户身份
        // 3. 根据用户id调用StudentDao中的getStudentById()方法获取学生信息并返回
        SetType.set(request, response);
        boolean auth = Auth.auth(request, response);
        if(!auth){
            JsonResponse.jsonResponse(response, 401, "您还没登录");
        } else{
            User user = (User) request.getAttribute("user");
            String id = user.id;
            Student student = StudentDao.getStudentInfoById(id);
            if(student == null) {
                JsonResponse.jsonResponse(response, 400, "用户不存在！");
            }else{
                JsonResponse.jsonResponse(response, student);
            }

        }
    }
}
