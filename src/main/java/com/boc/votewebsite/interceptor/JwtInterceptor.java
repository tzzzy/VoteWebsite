package com.boc.votewebsite.interceptor;

import cn.hutool.core.util.StrUtil;
import com.boc.votewebsite.service.StaffService;
import com.boc.votewebsite.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private StaffService staffService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        if(token == null || token.isEmpty()){
            return false;
        }
        try{
            JWTUtils.verify(token);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
