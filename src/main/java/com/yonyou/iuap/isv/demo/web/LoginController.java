package com.yonyou.iuap.isv.demo.web;

import com.yonyou.iuap.isv.demo.constraint.SuiteConfig;
import com.yonyou.iuap.isv.demo.model.LoginFreeResponse;
import com.yonyou.iuap.isv.demo.model.Suite;
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
 * 免登示例
 */
@RestController
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SuiteConfig suiteConfig;

    @Autowired
    private OpenApiRequestService requestService;

    @Autowired
    private SuiteService suiteService;

    @GetMapping("/login")
    public LoginFreeResponse loginFree(@RequestParam String code) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        LOGGER.info("新的免登请求, code: {}", code);
        Suite suite = suiteService.getSuite(suiteConfig.getSuiteKey());
        if (suite == null || suite.getSuiteTicket() == null) {
            throw new RuntimeException("套件尚未初始化，或尚未推送 ticket！");
        }

        LoginFreeResponse response = requestService.requestForLogin(suite.getSuiteKey(), code, suite.getSuiteTicket(), suite.getSuiteSecret());
        LOGGER.info("成功获得用户信息：{}", response);
        return response;
    }
}
