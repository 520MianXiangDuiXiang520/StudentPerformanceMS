package top.junebao.servlet;

import top.junebao.dao.SCDao;
import top.junebao.dao.StudentClassDao;
import top.junebao.dao.TCDao;
import top.junebao.domain.User;
import top.junebao.interceptor.Auth;
import top.junebao.utils.JsonResponse;
import top.junebao.utils.SetType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/TeacherManageScoreServlet")
public class TeacherManageScoreServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id, courseId;
        String className;
        Map<String, Object> result = null;
        Map<String, Object> classData = null;
        // 先要获取这个老师教的所有课程和班级，然后根据班级找到这个班所有学生
        // 1. 判断有没有登录，没登陆滚去登录
        SetType.set(request, response);
        boolean auth = Auth.auth(request, response);
        if(!auth){
            JsonResponse.jsonResponse(response, 401, "您还没登录");
        } else{
            User user = (User) request.getAttribute("user");
            id = user.id;
            // 2. 调用TCDao中的selectClassAndCourseByTno()方法，获取老师授课信息
            Object obj = TCDao.selectClassAndCourseByTno(id);
            if(obj == null) {
                // 3. 如果没有查询到老师授课信息，返回空
                JsonResponse.jsonResponse(response, 2000, "该老师没有授课信息");
            }else {
                className = request.getParameter("class");
                courseId = request.getParameter("course");
                if(className != null && courseId != null) {
                    // 判断老师有没有带这门课
                    if(!teacherHasClassCourse((List<Map<String, Object>>) obj, courseId, className)){
                        JsonResponse.jsonResponse(response, 400, "参数错误，该老师没有这门课的任课记录");
                    } else {
                        String courseName = "null";
                        for (Map m: (List<Map<String, Object>>) obj
                             ) {
                            if(m.get("courseId").equals(courseId)) {
                                courseName = (String)m.get("courseName");
                                break;
                            }
                        }
                        getResult(response, courseId, className, obj, courseName);
                    }

                    // 4. 如果GET请求中携带了class和course参数，就判断这个老师有没有带这个班，如果带了，就返回这个班所有人信息
                } else {
                    // 5. 如果GET请求没携带参数，则默认返回obj中第一班的信息(第一次请求)
                    // 5. 1 从Obj 中拿到第一个className
                    String firstClassName = (String)((List)((Map) ((List) obj).get(0)).get("classes")).get(0);
                    String firstCourseId = (String)(((Map)((List) obj).get(0)).get("courseId"));
                    String firstCourseName = (String)(((Map)((List) obj).get(0)).get("courseName"));
                    // 5.2 调用StudentClassDao中的selectAllStudentByClassId()方法获取该班的所有学生
                    getResult(response, firstCourseId, firstClassName, obj, firstCourseName);
                }
            }
        }
    }

    // 判断老师有没有带这门课
    private boolean teacherHasThisCourse(List<Map<String, Object>> list, String courseId){
        for (Map map: list
             ) {
            if(map.get("courseName").equals(courseId)){
                return true;
            }
        }
        return false;
    }

    // 判断老师有没有带这个班的这门课
    private static boolean teacherHasClassCourse(List<Map<String, Object>> list, String courseId, String className) {
        for (Map map: list) {
            if(map.get("courseId").equals(courseId)){
                for (String classN: (List<String>)map.get("classes") ) {
                    if(className.equals(classN))
                        return true;
                }
            }
        }
        return false;
    }
    private void getResult(HttpServletResponse response, String courseId, String className, Object obj, String courseName) throws IOException {
        Map<String, Object> classData;
        Map<String, Object> result;
        List<Map<String, Object>> maps = SCDao.selectAllStudentScoreByClassIdCno(className, courseId);
        System.out.println(maps);
        classData = new HashMap<>();
        classData.put("courseId", courseId);
        classData.put("courseName", courseName);
        classData.put("studentList", maps);
        classData.put("classId", className);
        System.out.println(classData);
        result = new HashMap<>();
        result.put("classes", obj);
        result.put("classData", classData);
        JsonResponse.jsonResponse(response, 200, result, "ok");
    }
}
