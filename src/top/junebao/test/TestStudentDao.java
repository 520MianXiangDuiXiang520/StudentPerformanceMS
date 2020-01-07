package top.junebao.test;

import org.junit.jupiter.api.Test;
import top.junebao.dao.StudentDao;
import top.junebao.domain.Student;

import java.util.List;
import java.util.Map;

public class TestStudentDao {
    @Test
    public void testUpdateStudentInfoById(){
        Student student = StudentDao.updateStudentInfoById("XY-N1", "tel", "13856963256");
        System.out.println(student);
    }

    @Test
    public void selectFirstClassStudentsTest() {
        List<Student> maps = StudentDao.selectFirstClassStudents(20);
        System.out.println(maps);
    }

    @Test
    public void selectStudentsByClassNameTest() {
        List<Map<String, Object>> maps = StudentDao.selectStudentsByClassName("17070144");
        System.out.println(maps);
    }
}
