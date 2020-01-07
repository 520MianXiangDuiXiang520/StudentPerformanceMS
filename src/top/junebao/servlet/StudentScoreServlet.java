package top.junebao.servlet;

import top.junebao.dao.SCDao;
import top.junebao.domain.User;
import top.junebao.interceptor.Auth;
import top.junebao.interceptor.Power;
import top.junebao.utils.JsonResponse;
import top.junebao.utils.SetType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/StudentScoreServlet")
public class StudentScoreServlet extends HttpServlet {
    String id;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * 查询该生的所有科目成绩
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 判断有没有登录，如果登录从session中拿到学号
        SetType.set(request, response);
        boolean auth = Auth.auth(request, response);
        if(!auth){
            JsonResponse.jsonResponse(response, 401, "您还没登录");
        } else{
            User user = (User) request.getAttribute("user");
            id = user.id;
            if(!Power.power(id, "student", response)){
                JsonResponse.jsonResponse(response, 403, "无权访问！");
            } else {
                // 2. 调用SCDao中的selectSCBySno查询学生成绩
                List<Map<String, Object>> selectResult = SCDao.selectSCBySno(id);
                if(selectResult == null){
                    JsonResponse.jsonResponse(response, 2000, "没有查询到任何信息");
                } else {
                    JsonResponse.jsonResponse(response, 200, selectResult, "ok");
                }
            }
        }

    }
}
