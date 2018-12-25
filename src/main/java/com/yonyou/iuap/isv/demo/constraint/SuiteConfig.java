package com.yonyou.iuap.isv.demo.constraint;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "suite-config")
public class SuiteConfig {

    private String suiteKey;

    private String suiteSecret;

    private String EncodingAESKey;

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public String getSuiteSecret() {
        return suiteSecret;
    }

    public void setSuiteSecret(String suiteSecret) {
        this.suiteSecret = suiteSecret;
    }

    public String getEncodingAESKey() {
        return EncodingAESKey;
    }

    public void setEncodingAESKey(String encodingAESKey) {
        EncodingAESKey = encodingAESKey;
    }
}
