package top.junebao.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonResponse {
    public static void jsonResponse(HttpServletResponse response, int state, Object obj, String msg) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("code", state);
        if(msg != null)
            result.put("msg", msg);
        if(obj != null)
            result.put("data", obj);
        response.getWriter().write(JSONUtil.toJSON(result));
    }

    public static void jsonResponse(HttpServletResponse response, Object obj) throws IOException {
        jsonResponse(response, 200, obj, "ok");
    }

    public static void jsonResponse(HttpServletResponse response) throws IOException {
        jsonResponse(response, 200, null, "ok");
    }

    public static void jsonResponse(HttpServletResponse response, int state, String msg) throws IOException {
        jsonResponse(response, state, null, msg);
    }
}
