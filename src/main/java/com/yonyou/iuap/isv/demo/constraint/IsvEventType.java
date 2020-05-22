package com.yonyou.iuap.isv.demo.constraint;

/**
 * 推送事件定义
 */
public enum IsvEventType {

    /**
     * 检查回调地址有效性
     */
    CHECK_URL,

    /**
     * 套件 ticket 推送事件
     */
    SUITE_TICKET,

    /**
     * 套件授权事件
     */
    SUITE_AUTH,

    /**
     * 员工增加
     */
    STAFF_ADD,

    /**
     * 员工更改
     */
    STAFF_UPDATE,

    /**
     * 员工启用
     */
    STAFF_ENABLE,

    /**
     * 员工停用
     */
    STAFF_DISABLE,

    /**
     * 员工删除
     */
    STAFF_DELETE,


    /**
     * 部门创建
     */
    DEPT_ADD,

    /**
     * 部门修改
     */
    DEPT_UPDATE,

    /**
     * 部门启用
     */
    DEPT_ENABLE,

    /**
     * 部门停用
     */
    DEPT_DISABLE,

    /**
     * 部门删除
     */
    DEPT_DELETE,


    /**
     * 用户增加
     */
    USER_ADD,

    /**
     * 用户删除
     */
    USER_DELETE,
    /**
     * 用户注销事件
     */
    FRONT_LOGOUT,

    /** UNKNOWN **/
    UNKNOWN,

    /**
     * 元数据修改
     */
    META_ALTER,

    APPROVAL_MEG


}
