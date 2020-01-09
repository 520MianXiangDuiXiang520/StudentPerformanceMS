package top.junebao.servlet;

import top.junebao.dao.SchoolDao;
import top.junebao.dao.StudentClassDao;
import top.junebao.utils.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@WebServlet("/SUClassManageServlet")
public class SUClassManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String className, magor;
        SetType.set(request, response);
        if(AuthAndPowerUtils.authAndPower(request, response, "superUser")) {
            // 检查参数（需要Id, name, password 三个必须字段）
            String fromDataString = JSONUtil.toFormDataString(request);
            Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
            if(!CheckParametersUtil.checkRequestParam(map, new ArrayList<>(Arrays.asList("className", "magor")))){
                JsonResponse.jsonResponse(response, 400, "参数缺失");
            } else {
                className = (String) map.get("className");
                magor = (String) map.get("magor");
                // 判断值是否合法
               if(className.length() > 10 || magor.length() > 20) {
                   JsonResponse.jsonResponse(response, 400, "长度不符合要求");
               }else {
                   if(StudentClassDao.isHaveClass(className)){
                       JsonResponse.jsonResponse(response, 400, "该班级已存在");
                   } else {
                       boolean b = StudentClassDao.insertNewClass(className, magor);
                       if(b) {
                           doGet(request, response);
                       } else {
                           JsonResponse.jsonResponse(response, 500, "添加失败");
                       }
                   }
               }
            }
        }
    }

    // 获取班级信息
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SetType.set(request, response);
        String schoolId;
        if(AuthAndPowerUtils.authAndPower(request, response, "superUser")) {
            String school1 = request.getParameter("schoolId");
            if(school1 == null) {
                // 去掉了学院，不会到else里
                JsonResponse.jsonResponse(response, 200, StudentClassDao.getAllClasses(), "ok");
            } else {
                schoolId = school1;
                if(!SchoolDao.isHaveSchool(schoolId)) {
                    JsonResponse.jsonResponse(response, 300, "学校不存在");
                } else {
                    JsonResponse.jsonResponse(response, 200, StudentClassDao.getClassesBySchool(schoolId), "ok");
                }
            }

        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String className;
        SetType.set(req, resp);
        if(AuthAndPowerUtils.authAndPower(req, resp, "superUser")) {
            String fromDataString = JSONUtil.toFormDataString(req);
            Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
            Object c = map.get("className");
            if(c == null){
                JsonResponse.jsonResponse(resp, 400, "参数缺失");
            } else {
                try{
                    className = (String)c ;
                    boolean b = StudentClassDao.deleteClass(className);
                    if(b){
                        doGet(req, resp);
                    } else {
                        JsonResponse.jsonResponse(resp, 400, "班级不存在");
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    JsonResponse.jsonResponse(resp, 400, "参数错误");
                }
            }
        }
    }
}
