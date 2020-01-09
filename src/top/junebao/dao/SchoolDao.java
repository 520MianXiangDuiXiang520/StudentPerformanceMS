package top.junebao.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import top.junebao.utils.DruidUtils;

import java.util.List;
import java.util.Map;

public class SchoolDao {
    private static JdbcTemplate jdbcTemplate = null;

    public static Object getAllSchools() {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql2 = "SELECT id, schoolName FROM school;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql2);
        if(maps.size() < 1) {
            return null;
        } else {
            return maps;
        }
    }

    public static boolean isHaveSchool(String id){
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql2 = "SELECT id, schoolName FROM school WHERE id=?;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql2, id);
        return maps.size() >= 1;
    }
}
