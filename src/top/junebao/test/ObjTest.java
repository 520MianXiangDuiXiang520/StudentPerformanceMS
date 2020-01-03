package top.junebao.test;

import top.junebao.domain.User;

class Change {
    public void change(User user) {
        user.setSex("nv");
    }
}

public class ObjTest {
    public static void main(String[] args) {
        User user = new User();
        Change c = new Change();
        c.change(user);
        System.out.println(user);
    }
}
