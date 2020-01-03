package top.junebao.test;

import org.junit.jupiter.api.Test;
import top.junebao.dao.UserDao;
import top.junebao.domain.User;

public class UserDaoTest {
    /**
     * Login()功能单元测试
     */
    @Test
    public void testLogin() {
        User user = new User("LS-N3", "wy111111");
//        User user = new User();
        UserDao userDao = new UserDao();
        User user1 = userDao.login(user);
        System.out.println(user1);
    }
}
