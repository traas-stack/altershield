/**
 * Alipay.com Inc. Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alipay.altershield;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xiangyue
 * @version : AlterShieldApplication.java, v 0.1 2023-04-27 11:28 xiangyue Exp $$
 */
@SpringBootApplication
public class AlterShieldApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AlterShieldApplication.class);
        app.run(args);
    }
}
