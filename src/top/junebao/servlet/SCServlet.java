package top.junebao.servlet;

import top.junebao.dao.CourseDao;
import top.junebao.dao.SCDao;
import top.junebao.dao.StudentDao;
import top.junebao.domain.Student;
import top.junebao.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@WebServlet(name = "SCServlet")
public class SCServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);
        String sno, cno;
        if(AuthAndPowerUtils.authAndPower(request, response, "student")) {
            // 需要三个参数 id, key, value
            String fromDataString = JSONUtil.toFormDataString(request);
            Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
            if (!CheckParametersUtil.checkRequestParam(map, new ArrayList<>(Arrays.asList("sno", "cno")))) {
                JsonResponse.jsonResponse(response, 400, "参数缺失");
            } else {
                sno = (String) map.get("sno");
                cno = (String) map.get("cno");
                Student haveStudent = StudentDao.isHaveStudent(sno);
                if(haveStudent == null){
                    JsonResponse.jsonResponse(response, 400,"没有这个学生");
                } else {
                    if(!CourseDao.isHaveCourse(cno)) {
                        JsonResponse.jsonResponse(response, 400,"没有这门课程");
                    } else {
                        if(SCDao.isHaveSC(sno, cno)) {
                            JsonResponse.jsonResponse(response, 400, "您已经选过这门课了");
                        } else {
                            JsonResponse.jsonResponse(response, 200);
                        }
                    }
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 所有没选过的课程
    }
}
