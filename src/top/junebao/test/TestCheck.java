package top.junebao.test;

import org.junit.jupiter.api.Test;
import top.junebao.domain.Teacher;
import top.junebao.utils.CheckParametersUtil;

public class TestCheck {

    @Test
    public void testCheckValueByKey() {
        boolean tel = CheckParametersUtil.checkValueByKey("name", "15364980976");
        System.out.println(tel);
    }

    @Test
    public void testMethod() throws NoSuchMethodException {
        CheckParametersUtil.class.getMethod("checkName", String.class);
    }

    @Test
    public void testCheckFieldIsInClass() {
        boolean dept = CheckParametersUtil.checkFieldIsInClass(Teacher.class, "dept");
        System.out.println(dept);
    }

}
