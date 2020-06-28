package club.myelf.Filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Pattern;

@Slf4j
@Component
@WebFilter(filterName = "/A_index_route", urlPatterns = {"/*"})
public class URLFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        // place your code here
        String baseUri = ((HttpServletRequest) servletRequest).getRequestURI();
        String uri = baseUri.substring(baseUri.indexOf("/",8)+1, baseUri.length());
        uri = uri.replace("/","");
        log.info(uri);
        if(uri.equals("index.html")) {
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/index.html");
            dispatcher.forward(servletRequest, servletResponse);
            return;
        }else if(!uri.equals("insert")&&!uri.equals("admin")){
            log.info(uri.length()+"");
            if(uri.length()==6) {

                RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/s/"+uri);
                dispatcher.forward(servletRequest, servletResponse);
                return;
            }
        }

        // pass the request along the filter chain
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
