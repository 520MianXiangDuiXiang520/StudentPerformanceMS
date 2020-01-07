package top.junebao.servlet;

import top.junebao.dao.SuperUserDao;
import top.junebao.dao.TeacherDao;
import top.junebao.domain.Teacher;
import top.junebao.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@WebServlet("/SUTeacherManageServlet")
public class SUTeacherManageServlet extends HttpServlet {
    // 添加老师
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id, name, password;
        SetType.set(request, response);
        if(AuthAndPowerUtils.authAndPower(request, response, "superUser")) {
            // 检查参数（需要Id, name, password 三个必须字段）
            String fromDataString = JSONUtil.toFormDataString(request);
            Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
            if(!CheckParametersUtil.checkRequestParam(map, new ArrayList<>(Arrays.asList("id", "name", "password")))){
                JsonResponse.jsonResponse(response, 400, "参数缺失");
            } else {
                id = (String) map.get("id");
                name = (String) map.get("name");
                password = (String) map.get("password");
                // 判断值是否合法
                if(CheckParametersUtil.checkValueByKey("id", id) && CheckParametersUtil.checkValueByKey("name", name)
                && CheckParametersUtil.checkValueByKey("password", password)){
                    boolean b = TeacherDao.insertNewTeacher(id, name, password);
                    if(b) {
                        JsonResponse.jsonResponse(response, 200, "ok");
                    } else {
                        JsonResponse.jsonResponse(response, 500, "添加失败");
                    }
                } else {
                    JsonResponse.jsonResponse(response, 400, "参数不合法！");
                }

            }
        }
    }

    // 查看老师
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);
        if(AuthAndPowerUtils.authAndPower(request, response, "superUser")) {
            JsonResponse.jsonResponse(response, 200, SuperUserDao.selectAllTeacher(), "ok");
        }
    }

    // 修改老师信息
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
                if (!CheckParametersUtil.checkFieldIsInClass(Teacher.class, key)){
                    JsonResponse.jsonResponse(resp, 400, "参数错误！");
                } else {
                    // 判断新值是否合法
                    if(!CheckParametersUtil.checkValueByKey(key, value)) {
                        JsonResponse.jsonResponse(resp, 400, "新值不合法！");
                    } else {
                        // 调用 teacherDao.
                        Teacher teacher = TeacherDao.updateTeacherInfoById(id, key, value);
                        if(teacher == null) {
                            JsonResponse.jsonResponse(resp, 500, "修改失败！");
                        } else {
                            this.doGet(req, resp);
                        }
                    }
                }
            }
        }
    }

    // 删除老师
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id;
        SetType.set(req, resp);
        if(AuthAndPowerUtils.authAndPower(req, resp, "superUser")) {
            // 检查参数（需要Id, name, password 三个必须字段）
            String fromDataString = JSONUtil.toFormDataString(req);
            Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
            if(!CheckParametersUtil.checkRequestParam(map, new ArrayList<>(Collections.singletonList("id")))){
                JsonResponse.jsonResponse(resp, 400, "参数缺失");
            } else {
                id = (String) map.get("id");
                // 判断值是否合法
                if(CheckParametersUtil.checkValueByKey("id", id)){
                    if(TeacherDao.getTeacherInfoById(id) == null) {
                        JsonResponse.jsonResponse(resp, 400, "老师不存在");
                    } else {
                        boolean b = TeacherDao.deleteTeacher(id);
                        if(b) {
                            JsonResponse.jsonResponse(resp, 200, "ok");
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
