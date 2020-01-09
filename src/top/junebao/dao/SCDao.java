package top.junebao.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.jdbc.core.JdbcTemplate;
import top.junebao.utils.DruidUtils;
import java.util.List;
import java.util.Map;

public class SCDao {
    private static JdbcTemplate jdbcTemplate = null;

    /**
     * 查询学生选修了的所有选修课
     * @param id 学号
     * @return
     */
    public static List<Map<String, Object>> selectAllChoiceC(String id) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT course.`name` AS courseName, course.Cscore, course.Ctime," +
                " course.id AS courseId, sc.score, teacher.`name` AS teacherName" +
                " FROM course, teacher, sc, tc WHERE sc.scstatus='选修' AND sc.sno = ?" +
                " AND sc.cno = course.id AND tc.cno = course.id AND teacher.id = tc.tno;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, id);
        return maps;
    }

    /**
     * 查询某个学生的某一科目成绩
     * @param Sno 学号
     * @param Cno 课程号
     * @return 返回查询到的list，没有查询到返回null
     * @throws JsonProcessingException
     */
    public static List<Map<String, Object>> selectSCBySnoCno(String Sno, String Cno) {
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
     * 为某个班所有学生添加某门课程
     * @param className
     * @param courseId
     * @return
     */
    public static boolean insertAllSCByClassName(String className, String courseId){
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "INSERT INTO sc(sc.sno, sc.cno)" +
                " SELECT student.id, '" + courseId +"' FROM student WHERE student.studentClass = ?;";
        int update = jdbcTemplate.update(sql, className);
        return update >= 1;
    }

    public static boolean isHaveSCBySnoCno(String sno, String cno) {
        return selectSCBySnoCno(sno, cno) != null;
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
                "AND course.id = sc.cno AND student.studentClass = tc.classno" +
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
                "FROM student, sc WHERE student.studentClass =  ? AND sc.cno = ? AND student.id = sc.sno ";
        return jdbcTemplate.queryForList(sql, classId, cno);
    }

    /**
     * 查询某个班所有人选课信息
     * @param className 班级号
     * @return
     */
    public static List<Map<String, Object>> selectAllSCByClassName(String className) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT student.id AS studentId, student.name AS studentName," +
                "course.id AS courseId, course.name AS courseName, sc.score, sc.id AS scno " +
                "FROM student, sc, course " +
                "WHERE student.studentClass =  ? AND course.id = sc.cno AND student.id = sc.sno ";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, className);
        if(maps.size() < 1)
            return null;
        else
            return maps;
    }

    public static Object selectFirstSC(int num) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT student.id AS studentId, student.name AS studentName," +
                "course.id AS courseId, course.name AS courseName, sc.score, sc.id AS scno " +
                "FROM student, sc, course " +
                "WHERE course.id = sc.cno AND student.id = sc.sno LIMIT "+ num +";";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        if(maps.size() < 1)
            return null;
        else
            return maps;
    }

    public static boolean insertNewCS(String sno, String cno) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "INSERT INTO sc(sno, cno, scstatus) VALUES(?,?, '选修');";
        int update = jdbcTemplate.update(sql, sno, cno);
        return update == 1;
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

    public static boolean insertSC(String sno, String cno) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "INSERT INTO sc(sno, cno) VALUES(?,?);";
        int update = jdbcTemplate.update(sql, sno, cno);
        return update == 1;
    }

    public static boolean isHaveSC(String sno, String cno) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT * FROM sc WHERE sno = ? AND cno = ?;";
        return jdbcTemplate.queryForList(sql, sno, cno).size() == 1;
    }

    public static boolean deleteSCById(int id){
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "DELETE FROM sc WHERE  id= ?";
        int row = jdbcTemplate.update(sql, id);
        return row == 1;
    }

    /**
     * 查询所有这个学生没选过的课程
     * @param sno 学号
     * @return
     */
    public static Object selectAllNo(String sno) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT course.id AS courseId, course.`name` AS courseName, course.Cscore," +
                " course.Ctime, teacher.id AS teacherId, teacher.name AS teacherName" +
                " FROM course, teacher, tc WHERE course.type = '选修'" +
                " AND course.id NOT IN (SELECT sc.cno FROM sc WHERE sc.sno=?)" +
                "  AND course.id = tc.cno AND teacher.id = tc.tno;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, sno);
        return maps;
    }
}
