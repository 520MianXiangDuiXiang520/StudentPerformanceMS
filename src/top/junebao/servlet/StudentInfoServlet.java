package top.junebao.servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/StudentInfoServlet")
public class StudentInfoServlet extends HttpServlet {
    private String id;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("play", "Student");
        request.getRequestDispatcher("/UserInfoServlet").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 判断用户是否登录
        // 2. 通过session获取用户身份
        // 3. 根据用户id调用StudentDao中的getStudentById()方法获取学生信息并返回
        request.setAttribute("play", "Student");
        request.getRequestDispatcher("/UserInfoServlet").forward(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("play", "Student");
        req.getRequestDispatcher("/UserInfoServlet").forward(req, resp);
    }
}
