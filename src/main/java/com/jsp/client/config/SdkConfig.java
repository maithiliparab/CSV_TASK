package com.jsp.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kibocommerce.sdk.catalogadministration.api.ProductAttributesApi;
import com.kibocommerce.sdk.catalogadministration.api.ProductTypesApi;
import com.kibocommerce.sdk.catalogadministration.api.ProductVariationsApi;
import com.kibocommerce.sdk.catalogadministration.api.ProductsApi;
import com.kibocommerce.sdk.common.ApiCredentials;
import com.kibocommerce.sdk.common.KiboConfiguration;

@Configuration
public class SdkConfig {

    @Value("${kibo.tenant.id}")
    private int tenantId;

    @Value("${kibo.site.id}")
    private int siteId;

    @Value("${kibo.client.id}")
    private String clientId;

    @Value("${kibo.client.secret}")
    private String clientSecret;

    @Value("${kibo.tenant.host}")
    private String tenantHost;

    @Value("${kibo.home.host}")
    private String homeHost;

    @Bean
    public KiboConfiguration kiboConfiguration() {

        return KiboConfiguration.builder()
                .withTenantId(tenantId)
                .withSiteId(siteId)
                .withCredentials(
                        ApiCredentials.builder()
                                .setClientId(clientId)
                                .setClientSecret(clientSecret)
                                .build())
                .withTenantHost(tenantHost)
                .withHomeHost(homeHost)
                .build();
    }

    @Bean
    public ProductAttributesApi productAttributesApi(KiboConfiguration config) {
        return new ProductAttributesApi(config);
    }
    
    @Bean
    public ProductTypesApi productTypesApi(KiboConfiguration config) {
        return new ProductTypesApi(config);
    }
    
    @Bean
    public ProductsApi productsApi(KiboConfiguration config) {
        return new ProductsApi(config);
    }
    
    
    @Bean
    public ProductVariationsApi productVariationsApi(KiboConfiguration config) {
        return new ProductVariationsApi(config);
    }

}