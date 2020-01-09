package top.junebao.servlet;

import top.junebao.dao.*;
import top.junebao.domain.SC;
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

@WebServlet("/SUSCServlet")
public class SUSCServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);
        String courseId, className;
        if(AuthAndPowerUtils.authAndPower(request, response, "superUser")) {
            // 需要三个参数 id, key, value
            String fromDataString = JSONUtil.toFormDataString(request);
            Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
            if (!CheckParametersUtil.checkRequestParam(map, new ArrayList<>(Arrays.asList("courseId", "className")))) {
                JsonResponse.jsonResponse(response, 400, "参数缺失");
            } else {
                courseId = (String) map.get("courseId");
                className = (String) map.get("className");
                if(!CourseDao.isHaveCourse(courseId) || !StudentClassDao.isHaveClass(className)) {
                    JsonResponse.jsonResponse(response, 400, "学号或班级号不存在");
                }else{
                    boolean b = SCDao.insertAllSCByClassName(className, courseId);
                    if(b){
                        doGet(request, response);
                    }else{
                        JsonResponse.jsonResponse(response, 500, "添加失败！");
                    }
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);
        if(AuthAndPowerUtils.authAndPower(request, response, "superUser")) {
            // 如果请求中有class参数，返回这个班的，否则返回第一个班的
            String className = request.getParameter("className");
            if(className == null) {
                JsonResponse.jsonResponse(response, 200, SCDao.selectFirstSC(10), "ok");
            } else {
                JsonResponse.jsonResponse(response, 200, SCDao.selectAllSCByClassName(className), "ok");
            }
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
                    boolean b = SCDao.deleteSCById(id);
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
