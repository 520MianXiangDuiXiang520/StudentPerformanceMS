package top.junebao.test;

import org.junit.jupiter.api.Test;
import top.junebao.domain.Student;

import java.lang.reflect.Field;

public class TestFields {

    @Test
    public void testGetField(){
        try {
            Field name = Student.class.getField("name");
            System.out.println(name.getName());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
