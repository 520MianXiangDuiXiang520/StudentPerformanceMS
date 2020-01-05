package top.junebao.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.jdbc.core.JdbcTemplate;
import top.junebao.utils.DruidUtils;
import top.junebao.utils.JSONUtil;
import java.util.List;
import java.util.Map;

public class SCDao {
    private static JdbcTemplate jdbcTemplate = null;

    /**
     * 查询某个学生的某一科目成绩
     * @param Sno 学号
     * @param Cno 课程号
     * @return 返回查询到的list，没有查询到返回null
     * @throws JsonProcessingException
     */
    public static List<Map<String, Object>> selectSCBySnoCno(String Sno, String Cno) throws JsonProcessingException {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT student.id AS studentId, student.name AS studentName," +
                " course.id AS courseID, course.name AS courseName ,sc.score " +
                "FROM student, sc, course WHERE student.id= ? AND sc.cno = ?" +
                " AND student.id = sc.sno AND course.id=sc.cno;";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, Sno, Cno);
        if(rows.size() < 1)
            return null;
        else {
           return rows;
        }
    }

    /**
     * 查询某个学生的所有科目的成绩
     * @param Sno 学号
     * @return 返回查询到的list
     * @throws JsonProcessingException
     */
    public static List<Map<String, Object>> selectSCBySno(String Sno) throws JsonProcessingException {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT student.id AS studentId, student.name AS studentName," +
                " course.id AS courseID, course.name AS courseName ,sc.score " +
                "FROM student, sc, course WHERE student.id= ? " +
                " AND student.id = sc.sno AND course.id=sc.cno;";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, Sno);
        if(rows.size() < 1)
            return null;
        else
            return rows;
    }
}
