package top.junebao.utils;

import top.junebao.domain.User;
import top.junebao.interceptor.Auth;
import top.junebao.interceptor.Power;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthAndPowerUtils {
    /**
     * 一个用来检查用户权限和认证的工具类
     * @param request 请求
     * @param response 响应
     * @param play 用户角色
     * @return 检查通过返回true否则返回false
     * @throws IOException
     */
    public static boolean authAndPower(HttpServletRequest request, HttpServletResponse response, String play) throws IOException {
        boolean auth = Auth.auth(request, response);
        String id = null;
        if(!auth){
            JsonResponse.jsonResponse(response, 401, "您还没登录");
            return false;
        } else{
            User user = (User) request.getAttribute("user");
            id = user.id;
            if(!Power.power(id, play, response)){
                JsonResponse.jsonResponse(response, 403, "无权访问！");
                return false;
            } else {
                return true;
            }
        }
    }
}
