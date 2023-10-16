package com.CyberChip_Backend.Config;

import com.CyberChip_Backend.Model.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = request.getHeader("token");
            Map<String, String> map = JWT.parseToken(token);
            return true;
        } catch (Exception e) {
            Result result = new Result();
            result.setMessage("TOKEN ERROR");
            ObjectMapper objectMapper = new ObjectMapper();
            String resultInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
            response.getWriter().write(resultInString);
            return false;
        }
    }
}
