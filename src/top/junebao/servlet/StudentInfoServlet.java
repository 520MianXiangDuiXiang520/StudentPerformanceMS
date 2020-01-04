package top.junebao.servlet;

import top.junebao.dao.StudentDao;
import top.junebao.domain.Student;
import top.junebao.domain.User;
import top.junebao.interceptor.Auth;
import top.junebao.utils.CheckParametersUtil;
import top.junebao.utils.JSONUtil;
import top.junebao.utils.JsonResponse;
import top.junebao.utils.SetType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@WebServlet("/StudentInfoServlet")
public class StudentInfoServlet extends HttpServlet {
    private String id;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 判断用户是否登录
        // 2. 通过session获取用户身份
        // 3. 根据用户id调用StudentDao中的getStudentById()方法获取学生信息并返回
        SetType.set(request, response);
        boolean auth = Auth.auth(request, response);
        if(!auth){
            JsonResponse.jsonResponse(response, 401, "您还没登录");
        } else{
            User user = (User) request.getAttribute("user");
            id = user.id;
            Student student = StudentDao.getStudentInfoById(id);
            if(student == null) {
                JsonResponse.jsonResponse(response, 400, "用户不存在！");
            }else{
                JsonResponse.jsonResponse(response, student);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 修改学生信息
        SetType.set(req, resp);
        String fromDataString = JSONUtil.toFormDataString(req);
        Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
        // 1. 确保学生已经登录，如果登录，拿到session中保存的User.id
        boolean auth = Auth.auth(req, resp);
        if(!auth){
            JsonResponse.jsonResponse(resp, 401, "您还没登录");
        } else {
            User user = (User) req.getAttribute("user");
            id = user.id;
            // 2. 确保req中包含key, newValue字段
            if(!CheckParametersUtil.checkRequestParam(map, new ArrayList<>(Arrays.asList("key", "newValue")))){
                JsonResponse.jsonResponse(resp, 400, "参数缺失");
            } else {
                String key = (String) map.get("key");
                String newValue = (String) map.get("newValue");
                // 3. 确保key是student表中的字段
                if (!CheckParametersUtil.checkFieldIsInClass(Student.class, key)){
                    JsonResponse.jsonResponse(resp, 400, "参数错误！");
                } else {
                    // 4. 确保newValue字段合法（反射！！）
                    if(!CheckParametersUtil.checkValueByKey(key, newValue)) {
                        JsonResponse.jsonResponse(resp, 400, "新值不合法！");
                    } else {
                        // 5. 调用StudentDao中的updateStudentInfoById方法更新学生数据
                        Student student = StudentDao.updateStudentInfoById(id, key, newValue);
                        if(student == null) {
                            JsonResponse.jsonResponse(resp, 400, "修改失败！！");
                        } else {
                            // 6. 将更新后的Student()对象返回
                            JsonResponse.jsonResponse(resp, student);
                        }
                    }
                }
            }
        }
    }
}
