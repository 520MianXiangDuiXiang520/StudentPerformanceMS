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
     */
    public static List<Map<String, Object>> selectSCBySno(String Sno) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT student.id AS studentId, student.NAME AS studentName," +
                " course.id AS courseID, course.NAME AS courseName, sc.score," +
                " course.cscore, course.ctime, teacher.NAME AS teacherName" +
                "  FROM student, sc, course, tc, teacher " +
                "WHERE student.id = ? AND student.id = sc.sno " +
                "AND course.id = sc.cno AND student.student_class = tc.classno" +
                " AND course.id = tc.cno AND teacher.id = tc.tno;";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, Sno);
        if(rows.size() < 1)
            return null;
        else
            return rows;
    }

    /**
     * 查询某个班某门课所有人的信息和成绩
     * @param classId 班号
     * @param cno 课号
     * @return List<Map<String, Object>>
     */
    public static List<Map<String, Object>> selectAllStudentScoreByClassIdCno(String classId, String cno) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT student.id AS studentId, student.name AS studentName, student.tel, student.sex, sc.score " +
                "FROM student, sc WHERE student.student_class =  ? AND sc.cno = ? AND student.id = sc.sno ";
        return jdbcTemplate.queryForList(sql, classId, cno);
    }

    /**
     * 修改某位同学的某门课成绩
     * @param sno
     * @param cno
     * @param newScore
     * @return
     */
    public static boolean updateStudentScoreBySnoCno(String sno, String cno, float newScore) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "UPDATE sc SET score = ? WHERE sno = ? AND cno = ?";
        int update = jdbcTemplate.update(sql, newScore, sno, cno);
        return update == 1;
    }
}
