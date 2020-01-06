package top.junebao.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import top.junebao.domain.StudentClass;
import top.junebao.utils.DruidUtils;

import java.util.List;
import java.util.Map;

public class StudentClassDao {
    private static JdbcTemplate jdbcTemplate = null;

    public static StudentClass insert(String className, String magor) {
        return null;
    }

    /**
     * 当班级表中插入新数据时，自动创建该班级的视图
     * @param className 班级名
     * @return 创建成功返回true，否则返回false
     */
    private static boolean creatViewWhenInsert(String className) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "CREATE VIEW class" + className + " AS SELECT student.id AS studentId," +
                " student.name AS studentName,student.sex, student.student_class AS studentClass," +
                " student.place, student.tel, studentclass.magor AS Magor FROM student, studentclass" +
                " WHERE studentclass.className = ? AND student.student_class = studentclass.className;";
        int update = jdbcTemplate.update(sql, className);
        return update == 1;
    }

    /**
     * 根据班级获取该班所有学生
     * TODO：使用视图加快查询速度
     * @param classId 班级号
     * @return List<Map<String, Object>> 没有查询到返回空list
     */
    public static List<Map<String, Object>> selectAllStudentByClassId(String classId) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT student.id, student.name, student.sex FROM student WHERE student.student_class = ?;";
        return jdbcTemplate.queryForList(sql, classId);
    }
}
