package top.junebao.servlet;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@WebServlet("/UserInfoServlet")
public class UserInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);
        boolean auth = Auth.auth(request, response);
        if(!auth){
            JsonResponse.jsonResponse(response, 401, "您还没登录");
        } else{
            // 如果用户已经登录，用户的信息就会被携带在request中，教师登录就会携带教师信息
            // 学生登录就会携带学生信息，所以教师查询个人信息和学生查询个人信息可以直接转发到这而不用区分
            JsonResponse.jsonResponse(response, request.getAttribute("user"));
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
        SetType.set(req, resp);
        // 1. 检查参数，需要的参数有：（1.）要修改的信息名key （2.）新的值newValue
        String fromDataString = JSONUtil.toFormDataString(req);
        Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
        if(!CheckParametersUtil.checkRequestParam(map, new ArrayList<>(Arrays.asList("key", "newValue")))){
            JsonResponse.jsonResponse(resp, 400, "参数错误！");
        }else{
            // 说明有key和newValue,先检查key是不是用户的属性
        }
    }
}
