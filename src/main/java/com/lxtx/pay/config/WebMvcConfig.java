package com.lxtx.pay.config;

import com.lxtx.pay.Interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private PlatformProperties platformProperties;

    @Value("${cors.allowed-origins:http://localhost:9999,http://127.0.0.1:9999}")
    private String corsAllowedOrigins;

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
        // 从环境变量读取允许的源，默认为本地开发地址
        List<String> allowedOrigins = Arrays.stream(corsAllowedOrigins.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins.toArray(new String[0]))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Content-Type", "Authorization", "X-Requested-With")
                .exposedHeaders("X-Request-Id", "Content-Disposition")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
