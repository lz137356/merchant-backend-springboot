package com.lxtx.pay.config;

import com.lxtx.pay.Interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private PlatformProperties platformProperties;

    @Bean
    public SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor(platformProperties);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/pay/cpinfo/login",
                        "/pay/cpinfo/login_v3",
                        "/pay/cpinfo/checkExistGoogle",
                        "/pay/cpinfo/generateToken",
                        "/pay/cpinfo/createGoogleSecretPublic",
                        // 导出端点兼容 Velopay 无 /pay 前缀路径
                        "/paylog/exportPayLog",
                        "/paylog/exportPayLogHistory",
                        "/withdrawlog/exportWithdrawLog",
                        "/moneylog/export"
                );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:9999", "http://127.0.0.1:9999",
                        "http://localhost:10001", "http://127.0.0.1:10001",
                        "http://3.108.59.40")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("X-Request-Id", "Content-Disposition")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
