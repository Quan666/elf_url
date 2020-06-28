package club.myelf.Interceptor;

import club.myelf.annotation.NeedAdmin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    // json处理工具
    private ObjectMapper jsonmapper = new ObjectMapper();
    //这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            NeedAdmin needadmin = ((HandlerMethod) handler).getMethodAnnotation(NeedAdmin.class);
            if (null == needadmin) {
                needadmin = ((HandlerMethod) handler).getMethod().getDeclaringClass()
                        .getAnnotation(NeedAdmin.class);
            }


        }
        //暂时设置为
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
