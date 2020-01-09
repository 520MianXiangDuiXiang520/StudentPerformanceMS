package top.junebao.servlet;

import top.junebao.dao.CourseDao;
import top.junebao.dao.TCDao;
import top.junebao.dao.TeacherDao;
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

@WebServlet("/SUTCManageServlet")
public class SUTCManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);
        String teacherId, courseId, className;
        if(AuthAndPowerUtils.authAndPower(request, response, "superUser")) {
            // 需要三个参数 id, key, value
            String fromDataString = JSONUtil.toFormDataString(request);
            Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
            if (!CheckParametersUtil.checkRequestParam(map, new ArrayList<>(Arrays.asList("teacherId", "courseId", "className")))) {
                JsonResponse.jsonResponse(response, 400, "参数缺失");
            } else {
                teacherId = (String) map.get("teacherId");
                courseId = (String) map.get("courseId");
                className = (String) map.get("className");
                // 判断老师是否存在
                if(!TeacherDao.isHaveTeacher(teacherId) || !CourseDao.isHaveCourse(courseId)){
                    JsonResponse.jsonResponse(response, 400, "教师或课程不存在");
                } else {
                    if(TCDao.isHaveTC(teacherId, courseId, className)) {
                        JsonResponse.jsonResponse(response, 400, "该记录已经存在！");
                    } else {
                        boolean b = TCDao.insertNewTC(teacherId, courseId, className);
                        if(b){
                            doGet(request, response);
                        } else {
                            JsonResponse.jsonResponse(response, 500, "添加失败！");
                        }
                    }
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);
        if(AuthAndPowerUtils.authAndPower(request, response, "superUser")) {
            JsonResponse.jsonResponse(response, 200, TCDao.getAllTC(), "ok");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id;
        SetType.set(req, resp);
        if(AuthAndPowerUtils.authAndPower(req, resp, "superUser")) {
            String fromDataString = JSONUtil.toFormDataString(req);
            Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
            Object id1 = map.get("id");
            if(id1 == null){
                JsonResponse.jsonResponse(resp, 400, "参数缺失");
            } else {
                try{
                    id = Integer.parseInt((String) id1);
                    boolean b = TCDao.deleteTCById(id);
                    if(b){
                        doGet(req, resp);
                    } else {
                        JsonResponse.jsonResponse(resp, 400, "记录不存在");
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    JsonResponse.jsonResponse(resp, 400, "参数错误");
                }
            }
        }
    }
}
