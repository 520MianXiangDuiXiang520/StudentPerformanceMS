package top.junebao.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.jdbc.core.JdbcTemplate;
import top.junebao.utils.DruidUtils;
import top.junebao.utils.JSONUtil;

import java.util.*;

public class TCDao {
    private static JdbcTemplate jdbcTemplate = null;

    /**
     * 查询所有的老师授课信息
     * @return List<Map<String, Object>>
     */
    public static List<Map<String, Object>> getAllTC() {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT teacher.name AS teacherName, teacher.id AS teacherId," +
                "course.name AS courseName, course.id AS courseId, tc.classno AS className, tc.id AS tcId" +
                " FROM tc, teacher, course WHERE tc.tno = teacher.id AND tc.cno = course.id;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        if(maps.size() < 1)
            return null;
        else
            return maps;
    }

    public static Object selectTCByTnoCnoClassName(String tno, String cno, String className) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT * FROM tc WHERE tno = ? AND cno = ? and classno = ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, tno, cno, className);
        if(maps.size() < 1)
            return null;
        else
            return maps;
    }

    public static Object selectTCByIde(int id) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT * FROM tc WHERE id = ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, id);
        if(maps.size() < 1)
            return null;
        else
            return maps;
    }

    public static boolean isHaveTC(String tno, String cno, String className) {
        return selectTCByTnoCnoClassName(tno, cno, className) != null;
    }

    public static boolean isHavingById(int id) {
        return selectTCByIde(id) != null;
    }

    public static boolean deleteTCById(int id) {
        String sql = "DELETE FROM tc WHERE  id= ?";
        int row = jdbcTemplate.update(sql, id);
        return row == 1;
    }

    public static boolean insertNewTC(String tno, String cno, String className) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "INSERT INTO tc(tno, cno, classno) VALUES(?,?,?);";
        int update = jdbcTemplate.update(sql, tno, cno, className);
        return update == 1;
    }


    /**
     * 查询老师交的额所有课程和班级
     * [{courseName=高等数学, classes=[17070144, 17070045, 17070046], courseId=L-01-1},
     * {courseName=Python程序设计, classes=[17070046], courseId=JK-02-2}]
     * @param tno 教师号
     * @return ArrayList
     */
    public static Object selectClassAndCourseByTno (String tno) {

        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT course.id AS courseId, course.name AS courseName," +
                " tc.classno AS className FROM course, tc WHERE tc.tno= ? AND tc.cno = course.id;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, tno);
        if(maps.size() < 1)
            return null;
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i=0;i<maps.size(); i++) {
            int count = 1;
            Map map = maps.get(i);
            String courseId = (String) map.get("courseId");
            String courseName = (String) map.get("courseName");
            String className = (String) map.get("className");
            if(result.size() < 1){
                asNewMap(result, courseId, courseName, className);
            }else {
                for (int j = 0; j<result.size();j++) {
                    Map m = result.get(j);
                    // 如果m中的courseName的值与courseName相同，就把
                    if(m.get("courseName").equals(courseName)){
                        ((List)m.get("classes")).add(className);
                        break;
                    }
                    if(count == result.size()){
                        asNewMap(result, courseId, courseName, className);
                        break;
                    }
                    count ++;
                }
            }
        }
        return result;
    }

    private static void asNewMap(List<Map<String, Object>> result, String courseId, String courseName, String className) {
        Map<String, Object> inMap = new HashMap<>();
        List<String> classes = new ArrayList<String>(Collections.singletonList(className));
        inMap.put("courseName", courseName);
        inMap.put("classes", classes);
        inMap.put("courseId", courseId);
        result.add(inMap);
    }
}
