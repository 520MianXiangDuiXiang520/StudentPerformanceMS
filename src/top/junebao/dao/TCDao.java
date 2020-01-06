package top.junebao.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.jdbc.core.JdbcTemplate;
import top.junebao.utils.DruidUtils;
import top.junebao.utils.JSONUtil;

import java.util.*;

public class TCDao {
    private static JdbcTemplate jdbcTemplate = null;

    private static Object check(List<Map<String, Object>> results, String courseId) {


        return null;
    }

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
                    System.out.println(m.get("courseName"));
                    System.out.println(courseName);
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
