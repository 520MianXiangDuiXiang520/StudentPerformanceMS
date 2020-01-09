package top.junebao.servlet;

import top.junebao.dao.SchoolDao;
import top.junebao.utils.AuthAndPowerUtils;
import top.junebao.utils.JsonResponse;
import top.junebao.utils.SetType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SUSchoolManageServlet")
public class SUSchoolManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);
        if(AuthAndPowerUtils.authAndPower(request, response, "superUser")) {
            JsonResponse.jsonResponse(response, 200, SchoolDao.getAllSchools(), "ok");
        }
    }
}
