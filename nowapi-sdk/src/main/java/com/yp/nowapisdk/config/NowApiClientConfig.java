package com.yp.nowapisdk.config;

import com.yp.nowapisdk.client.NowApiClient;
import com.yp.nowapisdk.service.ApiService;
import com.yp.nowapisdk.service.impi.ApiServiceImpl;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties("now.api.client")
@ComponentScan
public class NowApiClientConfig {
    /**
     * 访问密钥
     */
    private String accessKey;

    /**
     * 秘密密钥
     */
    private String secretKey;

    /**
     * 网关
     */
    private String host;

    @Bean
    public NowApiClient nowApiClient() {
        return new NowApiClient(accessKey, secretKey);
    }

    @Bean
    public ApiService apiService() {
        ApiServiceImpl apiService = new ApiServiceImpl();
        apiService.setNowApiClient(new NowApiClient(accessKey, secretKey));
        if (StringUtils.isNotBlank(host)) {
            apiService.setGatewayHost(host);
        }
        return apiService;
    }
}
