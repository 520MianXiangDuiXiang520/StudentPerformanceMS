package top.junebao.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import top.junebao.domain.Student;
import top.junebao.domain.User;
import top.junebao.utils.DruidUtils;

import java.util.List;

public class StudentDao {
    private static JdbcTemplate jdbcTemplate = null;

    /**
     * 通过Id查询学生的全部信息
     * @param id 学生Id（学号）
     * @return 如果存在，返回一个Student对象，不存在返回null
     */
    public static Student getStudentInfoById(String id){
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
//        String sql = "SELECT * FROM student WHERE id=?";
        // 连接student表和studentClass表，查询学生所有信息
        String sql = "SELECT student.id, name, sex, tel, place, password," +
                " studentClass.className AS studentClass, studentClass.magor FROM " +
                "student, studentClass WHERE student.id = ? AND student.student_class = studentClass.className;";
        List<Student> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Student>(Student.class), id);
        if(query.size() <= 0)
            return null;
        else
            return query.get(0);
    }

    /**
     * 通过id更新某一学生的某一项数据
     * @param id 学号
     * @param key 属性，这里不对key做判断，调用该方法前必须确保key值与数据库中字段名一致
     * @param newValue 新值
     * @return 如果成功则返回更新后的Student对象，否则返回null
     */
    public static Student updateStudentInfoById(String id, String key, String newValue) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "UPDATE student SET "+ key +" = ? WHERE id = ?";
        int row = jdbcTemplate.update(sql, newValue, id);
        if(row != 1){
            return null;
        } else {
            return getStudentInfoById(id);
        }
    }
}
