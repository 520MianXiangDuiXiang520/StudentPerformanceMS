package top.junebao.test;

import org.junit.jupiter.api.Test;
import top.junebao.dao.TCDao;
import top.junebao.servlet.TeacherManageScoreServlet;

import java.util.List;
import java.util.Map;

public class TestTCDao {

    @Test
    public void selectClassAndCourseByTnoTest() {
        Object t19950820 = TCDao.selectClassAndCourseByTno("T19990203");
        System.out.println(t19950820);
    }

    @Test
    public void teacherManageServletDoGetTest() {
        Object t19950820 = TCDao.selectClassAndCourseByTno("T19950820");
        assert t19950820 != null;
        System.out.println(((List)((Map) ((List) t19950820).get(0)).get("classes")).get(0));
        System.out.println(t19950820);
    }

//    @Test
//    public void teacherHasClassCourseTest() {
//        Object t19950820 = TCDao.selectClassAndCourseByTno("T19990203");
//        boolean b = new TeacherManageScoreServlet().teacherHasClassCourse((List<Map<String, Object>>) t19950820, "JK-02-2", "17070046");
//        System.out.println(b);
//    }


}
