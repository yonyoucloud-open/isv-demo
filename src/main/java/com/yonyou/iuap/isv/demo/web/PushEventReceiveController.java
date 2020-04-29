package com.yonyou.iuap.isv.demo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.iuap.isv.demo.constraint.IsvEventType;
import com.yonyou.iuap.isv.demo.constraint.SuiteConfig;
import com.yonyou.iuap.isv.demo.crypto.EncryptionHolder;
import com.yonyou.iuap.isv.demo.crypto.IsvEventCrypto;
import com.yonyou.iuap.isv.demo.model.EventContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

/**
 * 处理租户数据变更的回调事件。
 * 该回调地址填写在套件的 “订阅” 弹出框中。
 */
@RestController
public class PushEventReceiveController {

    private static Logger LOGGER = LoggerFactory.getLogger(PushEventReceiveController.class);

    @Autowired
    private SuiteConfig suiteConfig;

    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/open/event_push")
    public String eventCallBackReceiver(@RequestBody EncryptionHolder holder) throws IOException {

        // 构建解密验签处理对象
        IsvEventCrypto crypto = new IsvEventCrypto(suiteConfig.getSuiteSecret(), suiteConfig.getEncodingAESKey(), suiteConfig.getSuiteKey());
        // 验签解密后的消息体
        String decryptMessage = crypto.decryptMsg(holder);
        // 反序列化后的消息内容对象
        EventContent content = mapper.readValue(decryptMessage, EventContent.class);

        LOGGER.info("新的事件推送，content: {}", content.toString());

        // 根据事件类型进行相关处理操作，耗时操作推荐异步处理，平台默认推送超时时间为 5 秒，超时视为推送失败
        switch (content.getType()) {
            case CHECK_URL:
                LOGGER.info("事件类型: {}, 说明: 检查事件推送回调地址", IsvEventType.CHECK_URL);
                break;

            case STAFF_ADD:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下员工增加, 员工变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getStaffId()));
                break;
            case STAFF_UPDATE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下员工更改, 员工变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getStaffId()));
                break;
            case STAFF_ENABLE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下员工启用, 员工变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getStaffId()));
                break;
            case STAFF_DISABLE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下员工停用, 员工变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getStaffId()));
                break;
            case STAFF_DELETE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下员工删除, 员工变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getStaffId()));
                break;

            case DEPT_ADD:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下部门创建, 部门变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getDeptId()));
                break;
            case DEPT_UPDATE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下部门修改, 部门变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getDeptId()));
                break;
            case DEPT_ENABLE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下部门修改, 部门变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getDeptId()));
                break;
            case DEPT_DISABLE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下部门修改, 部门变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getDeptId()));
                break;
            case DEPT_DELETE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下部门删除, 部门变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getDeptId()));
                break;

            case USER_ADD:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下用户增加，用户 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getUserId()));
                break;
            case USER_DELETE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下用户移除，用户 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getUserId()));
                break;
            case APPROVAL_MEG:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下用户移除", content.getType(), content.getTenantId());
                break;
            case DELETE_DIWORKSESSION:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 用户注销，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;

            case UNKNOWN:
                LOGGER.info("未知事件");
                break;
        }

        // 处理成功，回复 "success" 告知开放平台，否则开放平台会重试推送，直到 24 小时
        return "success";
    }
}
