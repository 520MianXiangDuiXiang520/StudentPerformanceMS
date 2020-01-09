package top.junebao.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import top.junebao.domain.Course;
import top.junebao.utils.DruidUtils;

import java.util.List;
import java.util.Map;

public class CourseDao {
    private static JdbcTemplate jdbcTemplate = null;

    /**
     * 通过ID查询一门课程
     * @param id
     * @return
     */
    public static Map<String, Object> selectCourseById(String id) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT * FROM course WHERE id = ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, id);
        if(maps.size() < 1){
            return null;
        } else {
            return maps.get(0);
        }
    }

    /**
     * 获得所有课程
     * @return
     */
    public static List<Map<String, Object>> getAllCourses(){
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT * FROM course;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        if(maps.size() < 1){
            return null;
        } else {
            return maps;
        }
    }

    public static boolean insertNewCourse(String id, String name) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "INSERT INTO course(id, name) VALUES(?, ?);";
        int update = jdbcTemplate.update(sql, id, name);
        return update == 1;
    }

    public static boolean isHaveCourse(String id) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT * FROM course WHERE id = ?;";
        return jdbcTemplate.queryForList(sql, id).size() == 1;
    }

    public static Object updateCourseById(String id, String key, String value) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "UPDATE course SET "+ key +" = ? WHERE id = ?";
        int row = jdbcTemplate.update(sql, value, id);
        if(row != 1){
            return null;
        } else {
            if(key.equals(id))
                return selectCourseById(value);
            else
                return selectCourseById(id);
        }
    }

    public static boolean deleteCourse(String id) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "DELETE FROM course WHERE  id= ?";
        int row = jdbcTemplate.update(sql, id);
        return row == 1;
    }
}
