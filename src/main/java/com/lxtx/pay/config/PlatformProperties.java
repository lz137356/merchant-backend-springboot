package com.lxtx.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "platform")
public class PlatformProperties {

    /**
     * 平台版本: v5=velopay(旧版前端), v6=vvpay(新版前端)
     */
    private String version = "v6";

    public boolean isV5() {
        return "v5".equalsIgnoreCase(version);
    }

    public boolean isV6() {
        return "v6".equalsIgnoreCase(version);
    }
}
