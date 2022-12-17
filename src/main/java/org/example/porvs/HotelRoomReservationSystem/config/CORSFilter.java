package org.example.porvs.HotelRoomReservationSystem.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CORSFilter implements Filter {
    @Override
    public void init(FilterConfig fc) { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain fc) throws IOException, ServletException {
//        if(((HttpServletResponse) servletResponse).containsHeader("Access-Control-Allow-Origin")) {
//            ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
//            ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers","*");
//            ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Credentials", "true");
//            ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
//        }
        fc.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() { }
}