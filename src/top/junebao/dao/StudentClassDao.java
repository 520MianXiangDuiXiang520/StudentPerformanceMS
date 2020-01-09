package top.junebao.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import top.junebao.domain.StudentClass;
import top.junebao.utils.DruidUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentClassDao {
    private static JdbcTemplate jdbcTemplate = null;

    public static boolean deleteClass(String className) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "DELETE FROM studentClass WHERE  className = ?";
        int row = jdbcTemplate.update(sql, className);
        return row == 1;
    }

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
                " student.name AS studentName,student.sex, student.studentClass AS studentClass," +
                " student.place, student.tel, studentclass.magor AS Magor FROM student, studentclass" +
                " WHERE studentclass.className = ? AND student.studentClass = studentclass.className;";
        int update = jdbcTemplate.update(sql, className);
        return update == 1;
    }

    /**
     * 根据班级获取该班所有学生
     * @param classId 班级号
     * @return List<Map<String, Object>> 没有查询到返回空list
     */
    public static List<Map<String, Object>> selectAllStudentByClassId(String classId) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT * FROM student WHERE student.studentClass = ?;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, classId);
        if(maps.size() < 1) {
            return null;
        }else {
            return maps;
        }
    }

    public static StudentClass selectStudentClassByClassName(String className) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT * FROM studentClass WHERE className = ?;";
        List<StudentClass> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<StudentClass>(StudentClass.class), className);
        if(query.size() < 1){
            return null;
        } else {
            return query.get(0);
        }
    }

    /**
     * 获取所有班级
     * @return
     */
    public static List<Map<String, Object>> getAllClasses() {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "select studentclass.className, magor," +
                "  COUNT(1) AS num FROM student, studentclass WHERE" +
                " student.studentClass = studentclass.className GROUP BY student.studentClass;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        if(maps.size() < 1)
            return null;
        else{
            return maps;
        }
    }

    /**
     * 查询某学院所有班级
     * @param schoolId
     * @return
     */
    public static Map<String, Object> getClassesBySchool(String schoolId) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT * FROM studentClass WHERE school = ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, schoolId);
        if(maps.size() < 1) {
            return null;
        } else {
            Map<String, Object> result = new HashMap<>();
            result.put("schoolId", schoolId);
            result.put("classes", maps);
            return result;
        }
    }

    public static boolean insertNewClass(String className, String magor) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "INSERT INTO studentClass(className, magor) VALUES(?,?);";
        int update = jdbcTemplate.update(sql, className, magor);
        return update == 1;
    }

    public static boolean isHaveClass(String className) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT * FROM studentClass WHERE className = ?";
        return jdbcTemplate.queryForList(sql, className).size() == 1;
    }
}
