package top.junebao.servlet;

import top.junebao.dao.StudentClassDao;
import top.junebao.dao.StudentDao;
import top.junebao.domain.Student;
import top.junebao.domain.StudentClass;
import top.junebao.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/SUStudentManageServlet")
public class SUStudentManageServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id, name, password, className;
        SetType.set(request, response);
        if(AuthAndPowerUtils.authAndPower(request, response, "superUser")) {
            // 检查参数（需要Id, name, password 三个必须字段）
            String fromDataString = JSONUtil.toFormDataString(request);
            Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
            if(!CheckParametersUtil.checkRequestParam(map, new ArrayList<>(Arrays.asList("id", "name", "password", "className")))){
                JsonResponse.jsonResponse(response, 400, "参数缺失");
            } else {
                id = (String) map.get("id");
                name = (String) map.get("name");
                password = (String) map.get("password");
                className = (String) map.get("className");
                // 判断值是否合法
                if(CheckParametersUtil.checkValueByKey("id", id) && CheckParametersUtil.checkValueByKey("name", name)
                        && CheckParametersUtil.checkValueByKey("password", password)){
                    Student studentInfoById = StudentDao.isHaveStudent(id);
                    if(studentInfoById != null) {
                        JsonResponse.jsonResponse(response, 400, "id已存在");
                    } else {
                        // 检查class是否存在
                        StudentClass studentClass = StudentClassDao.selectStudentClassByClassName(className);
                        if(studentClass == null) {
                            JsonResponse.jsonResponse(response, 400, "班级不存在！");
                        } else {
                            boolean b = StudentDao.insertNewStudent(id, name, password, className);
                            if(b) {
                                doGet(request, response);
                            } else {
                                JsonResponse.jsonResponse(response, 500, "添加失败");
                            }
                        }
                    }
                } else {
                    JsonResponse.jsonResponse(response, 400, "参数不合法！");
                }

            }
        }
    }

    private static void getHasClass(HttpServletRequest request, HttpServletResponse response, String className) throws IOException {
        // 判断className是否存在
        List<Map<String, Object>> maps = StudentClassDao.selectAllStudentByClassId(className);
        if(maps == null) {
            JsonResponse.jsonResponse(response, 400, "班级不存在");
        } else {
            JsonResponse.jsonResponse(response, 200, maps, "ok");
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetType.set(request, response);
        if(AuthAndPowerUtils.authAndPower(request, response, "superUser")) {
            // 如果请求中有class参数，返回这个班的，否则返回第一个班的
            String className = request.getParameter("class");

            if(className == null) {
                JsonResponse.jsonResponse(response, 200, StudentDao.selectFirstClassStudents(10), "ok");
            } else {
                getHasClass(request, response, className);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SetType.set(req, resp);
        String id, key, value, className;
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
//                className = (String) map.get("class");
                // 判断 key 是否合法
                if (!CheckParametersUtil.checkFieldIsInClass(Student.class, key)){
                    JsonResponse.jsonResponse(resp, 400, "参数错误！");
                } else {
                    // 判断新值是否合法
                    if(!CheckParametersUtil.checkValueByKey(key, value)) {
                        JsonResponse.jsonResponse(resp, 400, "新值不合法！");
                    } else {
                        if(StudentDao.isHaveStudent(id) == null) {
                            JsonResponse.jsonResponse(resp, 400, "学生不存在");
                        } else {
                            if(key.equals("id")){
                                Student studentInfoById = StudentDao.isHaveStudent(value);
                                if(studentInfoById != null) {
                                    JsonResponse.jsonResponse(resp, 400, "id已存在");
                                } else {
                                    Student student = StudentDao.updateStudentInfoById(id, key, value);
                                    if(student == null) JsonResponse.jsonResponse(resp, 500, "修改失败！");
                                    else doGet(req, resp);
                                }
                            } else if (key.equals("studentClass")) {
                                StudentClass studentClass = StudentClassDao.selectStudentClassByClassName(value);
                                if(studentClass == null) JsonResponse.jsonResponse(resp, 400, "班级不存在！");
                                else {
                                    Student student = StudentDao.updateStudentInfoById(id, key, value);
                                    if(student == null) {
                                        JsonResponse.jsonResponse(resp, 500, "修改失败！");
                                    } else doGet(req, resp);
                                }
                            } else {
                                // 调用 teacherDao.
                                Student student = StudentDao.updateStudentInfoById(id, key, value);
                                if(student == null) JsonResponse.jsonResponse(resp, 500, "修改失败！");
                                else doGet(req, resp);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void insert(HttpServletRequest req, HttpServletResponse resp,
                               String id, String key, String value) throws IOException {

    }

    // 删除学生
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id, className;
        SetType.set(req, resp);
        if(AuthAndPowerUtils.authAndPower(req, resp, "superUser")) {
            // 检查参数（需要Id, name, password 三个必须字段）
            String fromDataString = JSONUtil.toFormDataString(req);
            Map<String, Object> map = JSONUtil.fromDataToMap(fromDataString);
            if(!CheckParametersUtil.checkRequestParam(map, new ArrayList<>(Arrays.asList("id")))){
                JsonResponse.jsonResponse(resp, 400, "参数缺失");
            } else {
                id = (String) map.get("id");
                Object aClass = map.get("class");
                // 判断值是否合法
                if(CheckParametersUtil.checkValueByKey("id", id)){
                    if(StudentDao.getStudentInfoById(id) == null) {
                        JsonResponse.jsonResponse(resp, 400, "学生不存在");
                    } else {
                        boolean b = StudentDao.deleteStudent(id);
                        if(b) {
                            if(aClass != null) {
                                className = (String) aClass;
                                getHasClass(req, resp, className);
                            } else {
                                doGet(req, resp);
                            }

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
