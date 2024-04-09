package com.example.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.example.reggie.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lay
 * @version 1.0
 */
@Slf4j
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    // 路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 1.获取请求URI
        String requestURI = request.getRequestURI();
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };
        // 2.判断请求是否需要处理
        for (String url : urls) {
            if (PATH_MATCHER.match(url, requestURI)) {
                // 3.无需处理放行
                filterChain.doFilter(request, response);
                return;
            }
        }
        // 4.判断状态，已登录则放行
        if (request.getSession().getAttribute("employee") != null) {
            filterChain.doFilter(request, response);
            return;
        }
        // 5.未登录返回，通过输出流像前端页面响应数据，前端实现页面跳转
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("未登录");
        return;
    }
}
