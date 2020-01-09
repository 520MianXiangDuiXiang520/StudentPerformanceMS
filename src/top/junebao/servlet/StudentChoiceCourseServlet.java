package top.junebao.servlet;

import top.junebao.dao.SCDao;
import top.junebao.domain.User;
import top.junebao.utils.AuthAndPowerUtils;
import top.junebao.utils.JsonResponse;
import top.junebao.utils.SetType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/StudentChoiceCourseServlet")
public class StudentChoiceCourseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);
        String id;
        // 查询选课信息
        if(AuthAndPowerUtils.authAndPower(request, response, "student")) {
            User user = (User) request.getAttribute("user");
            id = user.id;
            JsonResponse.jsonResponse(response, 200, SCDao.selectAllChoiceC(id), "ok");
        }
    }
}
