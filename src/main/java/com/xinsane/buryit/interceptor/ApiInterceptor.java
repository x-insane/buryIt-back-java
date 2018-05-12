package com.xinsane.buryit.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
//        if (request.getContentType().contains("json")) {
//            BufferedReader br = request.getReader();
//            StringBuilder builder = new StringBuilder();
//            String line;
//            while((line = br.readLine()) != null)
//                builder.append(line);
//            JsonObject object = new JsonParser().parse(builder.toString()).getAsJsonObject();
//            for (String key : object.keySet())
//                request.getParameterMap().put(key, new String[] { object.get("key").toString() });
//        }
        return true;
    }

}
