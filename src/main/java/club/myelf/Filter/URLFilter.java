package club.myelf.Filter;

import club.myelf.entity.Url;
import club.myelf.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@Slf4j
@Component
//@WebFilter(filterName = "/A_index_route", urlPatterns = {"/*"})
public class URLFilter implements Filter {
    @Autowired
    private UrlService urlService;
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
        if(uri.equals("index")||uri.length()<=1) {
            ((HttpServletResponse) servletResponse).setStatus(302);//设置状态码为302
            ((HttpServletResponse) servletResponse).setHeader("Location","/index.html") ;//新网址
//            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/index.html");
//            dispatcher.forward(servletRequest, servletResponse);
            return;
        }else if(!uri.equals("insert")&&!uri.equals("admin")){
            log.info(uri.length()+"");
            if(uri.length()==6) {
//                Url url = new Url();
//                url.setShortUrl(uri);
//                url.setUrl(urlService.selectByKey(url).getUrl());
//                if(url.getUrl()!=null){
//                    System.out.println(url.getUrl());
////                  return "redirect:"+url.getUrl();
//                    ((HttpServletResponse) servletResponse).setStatus(302);//设置状态码为302
//                    ((HttpServletResponse) servletResponse).setHeader("Location",url.getUrl()) ;//新网址
//                }else {
////                  return "redirect:/404.html";
//                    ((HttpServletResponse) servletResponse).setStatus(302);//设置状态码为302
//                    ((HttpServletResponse) servletResponse).setHeader("Location","/404.html") ;//新网址
//                }
                RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/s/"+uri);
                dispatcher.forward(servletRequest, servletResponse);
//                ((HttpServletResponse) servletResponse).setStatus(302);//设置状态码为301
//                ((HttpServletResponse) servletResponse).setHeader("Location","/index.html") ;//新网址
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
