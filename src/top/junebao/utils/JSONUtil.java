package top.junebao.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class JSONUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static BufferedReader br = null;

    /**
     * 将请求体中的数据格式化为 a=xxxx&b=xxxx的形式
     * @param request 请求
     * @return String
     * @throws IOException
     */
    public static String toFormDataString(HttpServletRequest request) throws IOException {
         br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(),
                "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        return sb.toString();
    }

    /**
     * JavaBean对象转换为Json字符串
     * @param obj JavaBean对象
     * @return JSON字符串
     * @throws JsonProcessingException
     */
    public static String toJSON(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * 将Form-Data格式的数据（id=%26111%26&password=%26yyy）转换为Map
     * @param string Form-Data格式的字符串
     * @return Json字符串
     * @throws JsonProcessingException
     */
    public static Map<String, Object> fromDataToMap(String string) {
        string = URLDecode.getURLDecoderString(string);
        Map<String, Object> map = new HashMap<>();
        String[] splits = string.split("&");
        for (String sp:splits
        ) {
            String[] kvs = sp.split("=");
            Object v = null;
            try{
                v = kvs[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            map.put(kvs[0], v);
        }
        return map;
    }

    /**
     * 将Form-Data格式的数据（id=%26111%26&password=%26yyy）转换为JSON格式
     * @param string Form-Data格式的字符串
     * @return Json字符串
     * @throws JsonProcessingException
     */
    public static String formDataToJson(String string) throws JsonProcessingException {
        return toJSON(fromDataToMap(string));
    }

    /**
     * 将JSON字符串转换为JavaBean对象
     * @param jsonString JSON字符串
     * @param classT JavaBean.class
     * @return Object
     * @throws IOException
     */
    public static Object toObject(String jsonString, Class classT) throws IOException {
        return objectMapper.readValue(jsonString, classT);
    }

    /**
     * 将Form-Data格式的数据转换为JavaBean对象
     * @param string Form-Data格式的字符串
     * @param classT JavaBean.class
     * @return Object
     * @throws IOException
     */
    public static Object formDataToObject(String string, Class classT) throws IOException {
        return toObject(formDataToJson(string), classT);
    }

    /**
     * 将请求体中的数据直接格式化为Json字符串
     * @param request
     * @return
     * @throws IOException
     */
    public static String reqBodyToJsonString(HttpServletRequest request) throws IOException {
        return formDataToJson(toFormDataString(request));
    }

    /**
     * 将请求体中的数据直接格式化为JavaBean对象
     * @param request
     * @param classT
     * @return
     * @throws IOException
     */
    public static Object reqBodyToObject(HttpServletRequest request, Class classT) throws IOException {
        return toObject(reqBodyToJsonString(request), classT);
    }
}
