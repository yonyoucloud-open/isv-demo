package com.yonyou.iuap.isv.demo.service;

import com.yonyou.iuap.isv.demo.model.Suite;
import com.yonyou.iuap.isv.demo.model.SuiteAuth;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模拟套件存储，请根据具体情况进行实现
 */
@Service
public class SuiteService {

    private Map<String, Suite> suiteStore = new ConcurrentHashMap<>();

    private Map<String, SuiteAuth> suiteAuthStore = new ConcurrentHashMap<>();

    public Suite getSuite(String suiteKey) {
        return suiteStore.get(suiteKey);
    }

    public void saveSuite(Suite suite) {
        suiteStore.put(suite.getSuiteKey(), suite);
    }

    public SuiteAuth getSuiteAuth(String suiteKey, String tenantId) {
        return suiteAuthStore.get(generateSuiteAuthKey(suiteKey, tenantId));
    }

    public void saveSuiteAuth(SuiteAuth suiteAuth) {
        suiteAuthStore.put(generateSuiteAuthKey(suiteAuth.getSuiteKey(), suiteAuth.getTenantId()), suiteAuth);
    }

    private String generateSuiteAuthKey(String suiteKey, String tenantId) {
        return suiteKey + tenantId;
    }
}
