package club.myelf;


import club.myelf.Interceptor.AdminInterceptor;
import club.myelf.Interceptor.ForbidInterceptor;
import club.myelf.Interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private AdminInterceptor adminInterceptor;
    @Autowired
    private ForbidInterceptor forbidInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
        registry.addInterceptor(adminInterceptor).addPathPatterns("/**");
        registry.addInterceptor(forbidInterceptor).addPathPatterns("/**");
    }
}
