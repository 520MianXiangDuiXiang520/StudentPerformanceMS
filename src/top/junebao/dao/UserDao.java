package top.junebao.dao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import top.junebao.domain.User;
import top.junebao.utils.DruidUtils;
import java.util.List;

public class UserDao {
    private JdbcTemplate jdbcTemplate = null;

    /**
     * 用户登录
     * @param id 学号/教师号
     * @param password 密码
     * @return 账号密码匹配返回true，否则返回false
     */
    private User login(String id, String password) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql = "SELECT * FROM student WHERE id=? AND password=?";
        List<User> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class), id, password);
        if(query.size() <= 0)
            return null;
        else
            return query.get(0);
    }

    /**
     * 登录（检查用户是否存在）
     * @param user User对象，必须包含id和password两个字段，反则认为不存在
     * @return Boolean
     */
    public User login(User user) {
        if(user.id != null && user.password != null) {
            return login(user.id, user.password);
        } else {
            return null;
        }
    }

    private User register(String table, String id, String password, String name) {
        jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        // 检查账号是否存在，如果存在返回null
        String isIdInDB = "SELECT * FROM "+ table +" WHERE id = ?";
        String addNewUser = "INSERT INTO "+ table + "(id,name,password) VALUES(?, ?, ?)";
        List<Object> query = jdbcTemplate.query(isIdInDB, new BeanPropertyRowMapper<>(Object.class), id);
        if(query.size() > 0) {
            return null;
        } else {
            // 不存在就保存在数据库中,返回User
            int count = jdbcTemplate.update(addNewUser, id, name, password);
            if(count <= 0) {
                return null;
            }
            else{
                User returnUser = new User(id, password);
                returnUser.setName(name);
                return returnUser;
            }
        }
    }

    public User userRegister(String table, User user) {
        if(user.getId()==null || user.getPassword() == null) {
            return null;
        }else {
            return register(table, user.getId(), user.getPassword(), user.getName());
        }
    }
}
