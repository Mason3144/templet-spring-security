package com.templete.servletFilter;

import javax.servlet.*;
import java.io.IOException;

public class ThirdFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("Third Filter 생성");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("----------------Third Filter 시작 ----------------");
        chain.doFilter(request, response);
        System.out.println("----------------Third Filter 종료 ----------------");
    }

    @Override
    public void destroy() {
        System.out.println("Third Filter 종료");
        Filter.super.destroy();
    }
}