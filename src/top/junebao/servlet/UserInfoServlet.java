package top.junebao.servlet;

import top.junebao.domain.User;
import top.junebao.interceptor.Auth;
import top.junebao.interceptor.Power;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@WebServlet("/UserInfoServlet")
public class UserInfoServlet extends HttpServlet {
    private String id;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);
        boolean auth = Auth.auth(request, response);
        if(!auth){
            JsonResponse.jsonResponse(response, 401, "您还没登录");
        } else{
            Object play = request.getAttribute("play");
            if("Student".equals(play.toString()) || "Teacher".equals(play.toString()) || "SuperUser".equals(play.toString())){
                System.out.println(play.toString());
                User user = (User) request.getAttribute("user");
                id = user.id;
                Object objUser = null;
                if(!Power.power(id, play.toString(), response)){
                    JsonResponse.jsonResponse(response, 403, "无权访问！");
                } else {
                    try {
                        Class<?> aClass = Class.forName("top.junebao.dao."+play.toString() + "Dao");
                        Method method = aClass.getMethod("get" + play.toString() + "InfoById", String.class);
                        objUser = method.invoke(aClass.newInstance(), id);
                    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    if(objUser == null) {
                        JsonResponse.jsonResponse(response, 400, "用户不存在！");
                    }else{
                        JsonResponse.jsonResponse(response, objUser);
                    }
                }
            } else {
                JsonResponse.jsonResponse(response, 403, "该接口不向用户开放");
            }
        }
    }

    /**
     * 修改用户信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 修改学生信息
        SetType.set(req, resp);
        String fromDataString = JSONUtil.toFormDataString(req);
        Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
        // 1. 确保用户已经登录，如果登录，拿到session中保存的User.id
        boolean auth = Auth.auth(req, resp);
        if(!auth){
            JsonResponse.jsonResponse(resp, 401, "您还没登录");
        } else {
            Object play = req.getAttribute("play");
            if("Student".equals(play.toString()) || "Teacher".equals(play.toString()) || "SuperUser".equals(play.toString())){
                User user = (User) req.getAttribute("user");
                id = user.id;
                if(!Power.power(id, play.toString(), resp)){
                    JsonResponse.jsonResponse(resp, 403, "无权访问！");
                } else {
                    String playStr = play.toString();
                    try {
                        Class playClass = Class.forName("top.junebao.domain." + playStr);
                        Class<?> playDaoClass = Class.forName("top.junebao.dao." + playStr + "Dao");
                        // 2. 确保req中包含key, newValue字段
                        if(!CheckParametersUtil.checkRequestParam(map, new ArrayList<>(Arrays.asList("key", "newValue")))){
                            JsonResponse.jsonResponse(resp, 400, "参数缺失");
                        } else {
                            String key = (String) map.get("key");
                            if("magor".equals(key) || "studentClass".equals(key) ||
                                    "id".equals(key) || "name".equals(key)){
                                JsonResponse.jsonResponse(resp, 400, "不能修改该字段！");
                            } else {
                                String newValue = (String) map.get("newValue");
                                // 3. 确保key是student表中的字段
                                if (!CheckParametersUtil.checkFieldIsInClass(playClass, key)){
                                    JsonResponse.jsonResponse(resp, 400, "参数错误！");
                                } else {
                                    // 4. 确保newValue字段合法（反射！！）
                                    if(!CheckParametersUtil.checkValueByKey(key, newValue)) {
                                        JsonResponse.jsonResponse(resp, 400, "新值不合法！");
                                    } else {
                                        // 5. 调用StudentDao中的updateStudentInfoById方法更新学生数据
                                        // 使用反射动态调用教师或学生Dao中的这个方法
                                        Method method = playDaoClass.getMethod("update" + playStr + "InfoById",
                                                String.class, String.class, String.class);
                                        System.out.println(id+key + newValue);
                                        Object invoke = method.invoke(playDaoClass.newInstance(), id, key, newValue);
//                                Student student = StudentDao.updateStudentInfoById(id, key, newValue);
                                        if(invoke == null) {
                                            JsonResponse.jsonResponse(resp, 400, "修改失败！！");
                                        } else {
                                            // 6. 将更新后的Student()对象返回
                                            JsonResponse.jsonResponse(resp, invoke);
                                        }
                                    }
                                }
                            }
                        }
                    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                JsonResponse.jsonResponse(resp, 403, "该接口不向用户开放");
            }

        }
    }
}
