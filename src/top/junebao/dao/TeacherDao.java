package top.junebao.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import top.junebao.domain.Teacher;
import top.junebao.utils.DruidUtils;

import java.util.List;

public class TeacherDao {
    private static JdbcTemplate jdbcTemplate = null;

    /**
     * 通过Id查询某位老师的全部信息
     * @param id 教师id（职工号）
     * @return 如果存在，返回一个Student对象，不存在返回null
     */
    public static Teacher getTeacherInfoById(String id){
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT * FROM teacher WHERE id=?";
        List<Teacher> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Teacher>(Teacher.class), id);
        if(query.size() <= 0)
            return null;
        else
            return query.get(0);
    }

    /**
     * 通过id更新某一的某一项数据
     * @param id 学号
     * @param key 属性，这里不对key做判断，调用该方法前必须确保key值与数据库中字段名一致
     * @param newValue 新值
     * @return 如果成功则返回更新后的Student对象，否则返回null
     */
    public static Teacher updateTeacherInfoById(String id, String key, String newValue) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "UPDATE teacher SET "+ key +" = ? WHERE id = ?";
        int row = jdbcTemplate.update(sql, newValue, id);
        if(row != 1){
            return null;
        } else {
            return getTeacherInfoById(id);
        }
    }

    /**
     * 添加一个新老师
     * @param id
     * @param name
     * @param password
     * @return
     */
    public static boolean insertNewTeacher(String id, String name, String password) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "INSERT INTO teacher(id, name, password) VALUES(?, ?, ?) ";
        int row = jdbcTemplate.update(sql, id, name, password);
        return row == 1;
    }

    public static boolean deleteTeacher(String id) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "DELETE FROM teacher WHERE  id= ?";
        int row = jdbcTemplate.update(sql, id);
        return row == 1;
    }
}
