package top.junebao.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import top.junebao.domain.Student;
import top.junebao.domain.User;
import top.junebao.utils.DruidUtils;

import java.util.List;
import java.util.Map;

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
                "student, studentClass WHERE student.id = ? AND student.studentClass = studentClass.className;";
        List<Student> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Student>(Student.class), id);
        if(query.size() <= 0)
            return null;
        else
            return query.get(0);
    }

    public static Student isHaveStudent(String id) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT * FROM student WHERE id = ?";
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
            if(key.equals("id")){
                return isHaveStudent(newValue);
            }
            return isHaveStudent(id);
        }
    }

    /**
     * 按班级顺序返回一些学生
     * @return
     */
    public static List<Student> selectFirstClassStudents(int num) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT * FROM student ORDER BY student.studentClass LIMIT "+ num +";";
        List<Student> maps = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Student>(Student.class));
        return maps;
    }


    public static List<Map<String, Object>> selectStudentsByClassName(String className) {
        return StudentClassDao.selectAllStudentByClassId(className);
    }

    public static boolean insertNewStudent(String id, String name, String password, String className) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "INSERT INTO student(id, name, password, studentClass) VALUES(?,?,?,?);";
        int update = jdbcTemplate.update(sql, id, name, password, className);
        return update == 1;
    }

    public static boolean deleteStudent(String id) {
        String sql = "DELETE FROM student WHERE  id= ?";
        int row = jdbcTemplate.update(sql, id);
        return row == 1;
    }
}
