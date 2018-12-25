package com.yonyou.iuap.isv.demo.web;

import com.yonyou.iuap.isv.demo.crypto.CryptoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@ResponseBody
public class ExceptionHandleController {

    private static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandleController.class);

    @ExceptionHandler(CryptoException.class)
    public String handleCryptoException(CryptoException e) {
        LOGGER.error("解密验签处理失败, 原因: {}", e.toString());
        LOGGER.error("解密验签处理失败，详情：", e);
        return "fail";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        LOGGER.error("请求处理失败，原因：{}", e.toString());
        LOGGER.error("请求处理失败，详情：{}", e);
        return "请求处理失败, 原因: " + e.toString();
    }
}
