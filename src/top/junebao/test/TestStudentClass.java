package top.junebao.test;

import org.junit.jupiter.api.Test;
import top.junebao.dao.StudentClassDao;

import java.util.List;
import java.util.Map;

public class TestStudentClass {

    @Test
    public void selectAllStudentByClassIdTest() {
        List<Map<String, Object>> maps = StudentClassDao.selectAllStudentByClassId("17070144");
        System.out.println(maps);
    }
}
