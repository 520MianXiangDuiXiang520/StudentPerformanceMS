package top.junebao.servlet;

import top.junebao.dao.CourseDao;
import top.junebao.dao.SCDao;
import top.junebao.dao.StudentClassDao;
import top.junebao.dao.StudentDao;
import top.junebao.domain.User;
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

@WebServlet("/StudentCourseServlet")
public class StudentCourseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);
        String courseId = null;
        if(AuthAndPowerUtils.authAndPower(request, response, "Student")) {
            // 需要三个参数 id, key, value
            String fromDataString = JSONUtil.toFormDataString(request);
            Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
            User user = (User) request.getAttribute("user");
            if(map.get("courseId") == null) {
                JsonResponse.jsonResponse(response, 400, "参数缺失");
            }else {
                courseId = (String) map.get("courseId");
                if(!CourseDao.isHaveCourse(courseId)) {
                    JsonResponse.jsonResponse(response, 400, "课程不存在");
                }else {
                    if(SCDao.isHaveSC(user.getId(), courseId)) {
                        JsonResponse.jsonResponse(response, 400, "记录已存在");
                    } else {
                        boolean b = SCDao.insertSC(user.getId(), courseId);
                        if(b){
                            doGet(request, response);
                        } else {
                            JsonResponse.jsonResponse(response, 500, "插入失败");
                        }
                    }
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);
        if(AuthAndPowerUtils.authAndPower(request, response, "Student")) {
            User user = (User) request.getAttribute("user");
            JsonResponse.jsonResponse(response, 200, SCDao.selectAllNo(user.getId()), "ok");
        }
    }
}
