/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.valuequest.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author nana.suryana
 * @email nana.suryana@dwidasa.com;nana.ns.suryana@gmail.com
 */
@Configuration
public class JasyptConfig {

    @Bean(name = "configurationEncryptor")
    public StandardPBEStringEncryptor standardPBEStringEncryptor() {
        StandardPBEStringEncryptor sse = new StandardPBEStringEncryptor();
        sse.setConfig(environmentStringPBEConfig());
        return sse;
    }

    @Bean(name = "environmentVariablesConfiguration")
    public EnvironmentStringPBEConfig environmentStringPBEConfig() {
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm("PBEWithMD5AndTripleDES");
        config.setPasswordEnvName("APP_ENCRYPT_PASSWORD");
        config.setPassword("dwidasa123");
        return config;
    }

}
