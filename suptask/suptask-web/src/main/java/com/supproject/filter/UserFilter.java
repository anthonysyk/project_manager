package com.supproject.filter;

import com.supproject.controller.SuptaskBean;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter(filterName="UserFilter", urlPatterns={"/private/*"})
public class UserFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SuptaskBean suptaskBean = null;
        
         if (suptaskBean != null /****/) {
             // FIXME
            String contextPath = ((HttpServletRequest)request).getContextPath();
            ((HttpServletResponse)response).sendRedirect(
                    contextPath + "/site/login.xhtml?faces-redirect=true");
        }
         
        chain.doFilter(request, response);
    }
    @Override
    public void destroy() {
    }

}
