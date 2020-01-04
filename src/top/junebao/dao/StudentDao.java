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
        String sql = "SELECT * FROM student WHERE id=?";
        List<Student> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Student>(Student.class), id);
        if(query.size() <= 0)
            return null;
        else
            return query.get(0);
    }
}
