package top.junebao.test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import top.junebao.domain.User;
import top.junebao.utils.JSONUtil;

import java.io.IOException;

public class JsonTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * obj对象转JSON字符串单元测试
     */
    @Test
    public void testObjToJson() {
        User user = new User();
        user.setId("LS-N4");
        user.setName("公孙胜");
        user.setPassword("gss111111");
        user.setSex("男");
        String json = "";

        try {
            json = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(json);
    }

    /**
     * json字符串转obj单元测试
     */
    @Test
    public void testJsonToObj() {
        User user = null;
        String json = "{\"id\":\"LS-N4\",\"name\":\"公孙胜\",\"sex\":\"男\",\"tel\":null,\"place\":null}";
        try {
            user = objectMapper.readValue(json, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(user);
    }

    @Test
    public void testFromDataToJsonUtil() throws JsonProcessingException {
        String s = JSONUtil.formDataToJson("id=%26111%26&password=%26yyy");
        System.out.println(s);
    }
}
