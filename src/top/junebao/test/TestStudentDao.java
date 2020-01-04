package top.junebao.test;

import org.junit.jupiter.api.Test;
import top.junebao.dao.StudentDao;
import top.junebao.domain.Student;

public class TestStudentDao {
    @Test
    public void testUpdateStudentInfoById(){
        Student student = StudentDao.updateStudentInfoById("XY-N1", "tel", "13856963256");
        System.out.println(student);
    }
}
