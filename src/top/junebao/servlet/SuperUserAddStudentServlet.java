package top.junebao.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SuperUserAddStudentServlet")
public class SuperUserAddStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 检查超级用户有没有登录
        // 2. 检查参数（需要id, password, className, sex 四个必须字段，可以选择传入 tel和place）
        // 3. 调用StudentDao.insertNewStudent()插入新生
        // 4. 返回插入后的学生信息
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
