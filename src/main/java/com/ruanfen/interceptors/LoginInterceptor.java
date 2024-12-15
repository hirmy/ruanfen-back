package com.ruanfen.interceptors;

import com.ruanfen.utils.JwtUtil;
import com.ruanfen.utils.ThreadLocalUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        try{
            //Map={"id", "userName"}
            Map<String, Object> claim = JwtUtil.parseToken(token);
            ThreadLocalUtil.set(claim);
            return true;
        }catch (Exception e){
            throw new RuntimeException("token验证失败");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        ThreadLocalUtil.remove();
    }
}
