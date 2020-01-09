package top.junebao.servlet;

import top.junebao.dao.CourseDao;
import top.junebao.dao.TeacherDao;
import top.junebao.domain.Course;
import top.junebao.domain.Teacher;
import top.junebao.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/SUCourseManageServlet")
public class SUCourseManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id, name;
        SetType.set(request, response);
        if(AuthAndPowerUtils.authAndPower(request, response, "superUser")) {
            // 检查参数（需要Id, name, password 三个必须字段）
            String fromDataString = JSONUtil.toFormDataString(request);
            Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
            if(!CheckParametersUtil.checkRequestParam(map, new ArrayList<>(Arrays.asList("id", "name")))){
                JsonResponse.jsonResponse(response, 400, "参数缺失");
            } else {
                id = (String) map.get("id");
                name = (String) map.get("name");
                // 判断值是否合法
                if(id.length() < 10 && name.length() <= 20){
                    boolean b = CourseDao.insertNewCourse(id, name);
                    if(b) {
                        this.doGet(request, response);
                    } else {
                        JsonResponse.jsonResponse(response, 500, "添加失败");
                    }
                } else {
                    JsonResponse.jsonResponse(response, 400, "参数不合法！");
                }

            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);
        if(AuthAndPowerUtils.authAndPower(request, response, "superUser")) {
            List<Map<String, Object>> allCourses = CourseDao.getAllCourses();
            JsonResponse.jsonResponse(response, 200, allCourses, "ok");
        }
    }

    private boolean check(String k, String v) {
        if(k.toLowerCase().equals("cscore")) {
            try{
                float v1 = Float.parseFloat(v);
                return v1 > 0;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else if (k.toLowerCase().equals("ctime")) {
            try{
                int i = Integer.parseInt(v);
                return i > 0;
            }catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else if(k.toLowerCase().equals("name")){
            return v.length() <= 20;
        } else if(k.toLowerCase().equals("id")) {
            return !CourseDao.isHaveCourse(v);
        } else {
            return false;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SetType.set(req, resp);
        String id, key, value;
        if(AuthAndPowerUtils.authAndPower(req, resp, "superUser")) {
            // 需要三个参数 id, key, value
            String fromDataString = JSONUtil.toFormDataString(req);
            Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
            if(!CheckParametersUtil.checkRequestParam(map, new ArrayList<>(Arrays.asList("key", "value", "id")))){
                JsonResponse.jsonResponse(resp, 400, "参数缺失");
            } else {
                id = (String) map.get("id");
                key = (String) map.get("key");
                value = (String) map.get("value");
                // 判断 key 是否合法
                if (!CheckParametersUtil.checkFieldIsInClass(Course.class, key)){
                    JsonResponse.jsonResponse(resp, 400, "参数错误！");
                } else {
                    // 判断新值是否合法
                    if(!check(key, value)) {
                        JsonResponse.jsonResponse(resp, 400, "新值不合法！");
                    } else {
                        // 调用 teacherDao.
                        Object o = CourseDao.updateCourseById(id, key, value);
                        if(o == null) {
                            JsonResponse.jsonResponse(resp, 500, "修改失败！");
                        } else {
                            this.doGet(req, resp);
                        }
                    }
                }
            }
        }
    }

    // 删除课程
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id;
        SetType.set(req, resp);
        if(AuthAndPowerUtils.authAndPower(req, resp, "superUser")) {
            String fromDataString = JSONUtil.toFormDataString(req);
            Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
            Object id1 = map.get("id");
            if(id1 == null){
                JsonResponse.jsonResponse(resp, 400, "参数缺失");
            } else {
                id = (String) map.get("id");
                // 判断值是否合法
                if(CheckParametersUtil.checkValueByKey("id", id)){
                    if(!CourseDao.isHaveCourse(id)) {
                        JsonResponse.jsonResponse(resp, 400, "课程不存在");
                    } else {
                        boolean b = CourseDao.deleteCourse(id);
                        if(b) {
                            doGet(req, resp);
                        } else {
                            JsonResponse.jsonResponse(resp, 500, "删除失败");
                        }
                    }
                } else {
                    JsonResponse.jsonResponse(resp, 400, "参数不合法！");
                }

            }
        }
    }
}
