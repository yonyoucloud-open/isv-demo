package com.yonyou.iuap.isv.demo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.iuap.isv.demo.crypto.SignHelper;
import com.yonyou.iuap.isv.demo.model.AccessTokenResponse;
import com.yonyou.iuap.isv.demo.model.GenericResponse;
import com.yonyou.iuap.isv.demo.model.LoginFreeResponse;
import com.yonyou.iuap.isv.demo.utils.RequestTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class OpenApiRequestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenApiRequestService.class);

    @Value("${open-api.url}")
    private String openApiUrl;

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * 获取 access_token
     * @param suiteKey      套件的 suite key
     * @param tenantId      授权的租户 id
     * @param suiteTicket   套件的 suite ticket
     * @param suiteSecret   套件的 suite secret
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IOException
     */
    public AccessTokenResponse requestForAccessToken(String suiteKey, String tenantId, String suiteTicket, String suiteSecret) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        Map<String, String> params = new HashMap<>();
        // 除签名外的其他参数
        params.put("suiteKey", suiteKey);
        params.put("tenantId", tenantId);
        params.put("suiteTicket", suiteTicket);
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        // 计算签名
        String signature = SignHelper.sign(params, suiteSecret);
        params.put("signature", signature);

        // 请求
        String requestUrl = openApiUrl + "/open-auth/suiteApp/getAccessToken";
        GenericResponse<AccessTokenResponse> response = RequestTool.doGet(requestUrl, params, new TypeReference<GenericResponse<AccessTokenResponse>>() {});

        if (response.isSuccess()) {
            return response.getData();
        }

        LOGGER.error("请求开放平台接口失败，code: {}, message: {}", response.getCode(), response.getMessage());
        throw new RuntimeException("请求开放平台接口失败, code: " + response.getCode() + ", message: " + response.getMessage());
    }

    /**
     * 获取免登信息
     * @param suiteKey      套件的 suite key
     * @param code          免登授权码 code
     * @param suiteTicket   套件的 suite ticket
     * @param suiteSecret   套件的 suite secret
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IOException
     */
    public LoginFreeResponse requestForLogin(String suiteKey, String code, String suiteTicket, String suiteSecret) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        Map<String, String> params = new HashMap<>();
        // 除签名外的其他参数
        params.put("suiteKey", suiteKey);
        params.put("code", code);
        params.put("suiteTicket", suiteTicket);
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));

        // 计算签名
        String signature = SignHelper.sign(params, suiteSecret);
        params.put("signature", signature);

        // 请求
        String requestUrl = openApiUrl + "/open-auth/suiteApp/getBaseInfoByCode";
        GenericResponse<LoginFreeResponse> response = RequestTool.doGet(requestUrl, params, new TypeReference<GenericResponse<LoginFreeResponse>>() {});

        if (response.isSuccess()) {
            return response.getData();
        }

        LOGGER.error("请求开放平台接口失败，code: {}, message: {}", response.getCode(), response.getMessage());
        throw new RuntimeException("请求开放平台接口失败, code: " + response.getCode() + ", message: " + response.getMessage());
    }
}
