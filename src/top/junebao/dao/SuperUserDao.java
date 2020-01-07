package top.junebao.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import top.junebao.domain.SuperUser;
import top.junebao.domain.Teacher;
import top.junebao.utils.DruidUtils;

import java.util.List;

public class SuperUserDao {
    private static JdbcTemplate jdbcTemplate = null;

    /**
     * 通过Id查询某个管理员全部信息
     * @param id 管理员Id
     * @return 如果存在，返回一个Student对象，不存在返回null
     */
    public static SuperUser getSuperUserInfoById(String id){
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT * FROM superUser WHERE id=?";
        List<SuperUser> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<SuperUser>(SuperUser.class), id);
        if(query.size() <= 0)
            return null;
        else
            return query.get(0);
    }
}
