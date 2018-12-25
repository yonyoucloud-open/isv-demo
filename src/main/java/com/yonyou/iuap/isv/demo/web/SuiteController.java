package com.yonyou.iuap.isv.demo.web;

import com.yonyou.iuap.isv.demo.model.AccessTokenResponse;
import com.yonyou.iuap.isv.demo.model.Suite;
import com.yonyou.iuap.isv.demo.model.SuiteAuth;
import com.yonyou.iuap.isv.demo.service.OpenApiRequestService;
import com.yonyou.iuap.isv.demo.service.SuiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 套件相关的业务操作
 */
@RestController
public class SuiteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuiteController.class);

    @Autowired
    private OpenApiRequestService requestService;

    @Autowired
    private SuiteService suiteService;

    @GetMapping("/suite/getAccessToken")
    public AccessTokenResponse getAccessToken(@RequestParam String suiteKey, @RequestParam String tenantId) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        Suite suite = suiteService.getSuite(suiteKey);
        if (suite == null) {
            throw new RuntimeException("无效的套件或尚未初始化 suiteKey: " + suiteKey);
        }

        SuiteAuth suiteAuth = suiteService.getSuiteAuth(suite.getSuiteKey(), tenantId);
        if (suiteAuth == null) {
            suiteAuth = new SuiteAuth();
            suiteAuth.setSuiteKey(suiteKey);
            suiteAuth.setTenantId(tenantId);
        }

        AccessTokenResponse response = requestService.requestForAccessToken(suite.getSuiteKey(), tenantId, suite.getSuiteTicket(), suite.getSuiteSecret());
        LOGGER.info("成功获得访问令牌: suiteKey: {}, tenantId: {}, access_token: {}, expire in: {} s", suiteKey, tenantId, response.getAccessToken(), response.getExpire());
        suiteAuth.updateAccessToken(response);
        suiteService.saveSuiteAuth(suiteAuth);
        return response;
    }
}
