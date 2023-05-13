package com.templete.servletFilter;

import javax.servlet.*;
import java.io.IOException;

public class FirstFilter implements Filter {
    // (1) 초기화 작업
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("First Filter 생성");
    }

    // (2)
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        // (2-1) 이곳에서 request(ServletRequest)를 이용해 다음 Filter로 넘어가기 전처리 작업을 수행한다.
        System.out.println("----------------First Filter 시작 ----------------");
        // (2-2)
        chain.doFilter(request, response);

        // (2-3) 이곳에서 response(ServletResponse)를 이용해 response에 대한 후처리 작업을 할 수 있다.
        System.out.println("----------------First Filter 종료 ----------------");
    }

    // (3)
    public void destroy() {
        // (5) Filter가 사용한 자원을 반납하는 처리
        System.out.println("FirstFilter Destroy");
        Filter.super.destroy();
    }
}