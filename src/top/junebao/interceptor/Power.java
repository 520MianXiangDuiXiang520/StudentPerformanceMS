package top.junebao.interceptor;

import top.junebao.dao.StudentDao;
import top.junebao.dao.SuperUserDao;
import top.junebao.dao.TeacherDao;
import top.junebao.domain.Student;
import top.junebao.domain.SuperUser;
import top.junebao.domain.Teacher;
import top.junebao.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

// 权限管理,必须在用户认证之后

public class Power {

    public static boolean power(String id, String play, HttpServletResponse response){
        if("student".equals(play.toLowerCase())) {
            return studentPower(id, response);
        } else if("teacher".equals(play.toLowerCase())) {
            return teacherPower(id, response);
        } else if("superuser".equals(play.toLowerCase())) {
            return superUserPower(id, response);
        } else {
            return false;
        }

    }

    private static boolean studentPower(String id, HttpServletResponse response) {
        if(StudentDao.getStudentInfoById(id) == null ){
            response.setStatus(403);
            return false;
        }
        return true;
    }

    private static boolean teacherPower(String id, HttpServletResponse response) {
        if(TeacherDao.getTeacherInfoById(id) == null ){
            response.setStatus(403);
            return false;
        }
        return true;
    }

    private static boolean superUserPower(String id, HttpServletResponse response) {
        if(SuperUserDao.getSuperUserInfoById(id) == null ){
            response.setStatus(403);
            return false;
        }
        return true;
    }
}
