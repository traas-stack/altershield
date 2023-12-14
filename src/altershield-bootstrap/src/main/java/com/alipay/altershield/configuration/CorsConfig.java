/*
 * MIT License
 *
 * Copyright (c) [2023]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.alipay.altershield.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;


@ServletComponentScan
@Configuration
public class CorsConfig {

    /**
     * 最大生命周期
     */
    private long maxAge = 1600;

    /**
     * Build config cors configuration.
     *
     * @return the cors configuration
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.mvc.cors-filter-config")
    public CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setMaxAge(maxAge);
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    /**
     * Cors filter filter registration bean.
     *
     * @param corsConfiguration the cors configuration
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean corsFilter(CorsConfiguration corsConfiguration)
    {
        if(CollectionUtils.isEmpty(corsConfiguration.getAllowedOriginPatterns()))
        {
            corsConfiguration.addAllowedOriginPattern("*");
        }
        FilterRegistrationBean bean = new FilterRegistrationBean();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        bean.setUrlPatterns(Arrays.asList("/meta/*", "/ops/*", "/exe/*"));
        bean.setName("corsFilter");
        bean.setFilter(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }


}