package club.myelf.Interceptor;

import club.myelf.annotation.IsURL;
import club.myelf.annotation.NeedLogin;
import club.myelf.common.ApiResult;
import club.myelf.common.ResultCode;
import club.myelf.entity.Url;
import club.myelf.entity.User;
import club.myelf.service.UrlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class IsURLInterceptor implements HandlerInterceptor {
    // json处理工具
    private ObjectMapper jsonmapper = new ObjectMapper();
    //这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
    @Autowired
    private UrlService urlService;
    @Override
    @ResponseBody
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            IsURL isURL = ((HandlerMethod) handler).getMethodAnnotation(IsURL.class);
            if (null == isURL) {
                isURL = ((HandlerMethod) handler).getMethod().getDeclaringClass()
                        .getAnnotation(IsURL.class);
            }
            // 有登录验证注解，则校验登录
            if (null != isURL) {
                //获取请求参数
                String queryString = request.getRequestURI();
                System.out.println("请求参数:{}"+ queryString);
//                Url url = new Url();
//                url.setShortUrl(shortURL);
//                url.setUrl(urlService.selectByKey(url).getUrl());
//                if(url.getUrl()!=null){
//                    System.out.println(url.getUrl());
////                    return "redirect:"+url.getUrl();
//                }else {
////                    return "redirect:/404.html";
//                }

//                User user = (User) request.getSession()
//                        .getAttribute("USER");
//                //如果session中没有，表示没登录
//                if (null == user) {
//                    response.setCharacterEncoding("UTF-8");
//                    response.setContentType("application/json; charset=utf-8");
//                    String json = jsonmapper.writeValueAsString(new ApiResult<>(ResultCode.login.getCode(),"请登录！", ResultCode.login.getDescr(), request.getRequestURI()));
//                    response.getWriter().write(json);
//                    return false;
//                }
            }

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
