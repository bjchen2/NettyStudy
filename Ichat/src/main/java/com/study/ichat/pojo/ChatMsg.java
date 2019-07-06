package com.study.ichat.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author cxd27419
 */
@Table(name = "chat_msg")
public class ChatMsg {
    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 发送用户ID
     */
    @Column(name = "send_user_id")
    private String sendUserId;

    /**
     * 接收用户ID
     */
    @Column(name = "accept_user_id")
    private String acceptUserId;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 消息是否已查看
1：签收
0：未签收

     */
    @Column(name = "sign_flag")
    private Integer signFlag;

    /**
     * 消息创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取发送用户ID
     *
     * @return send_user_id - 发送用户ID
     */
    public String getSendUserId() {
        return sendUserId;
    }

    /**
     * 设置发送用户ID
     *
     * @param sendUserId 发送用户ID
     */
    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    /**
     * 获取接收用户ID
     *
     * @return accept_user_id - 接收用户ID
     */
    public String getAcceptUserId() {
        return acceptUserId;
    }

    /**
     * 设置接收用户ID
     *
     * @param acceptUserId 接收用户ID
     */
    public void setAcceptUserId(String acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    /**
     * 获取消息内容
     *
     * @return msg - 消息内容
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置消息内容
     *
     * @param msg 消息内容
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取消息是否已查看
1：签收
0：未签收

     *
     * @return sign_flag - 消息是否已查看
1：签收
0：未签收

     */
    public Integer getSignFlag() {
        return signFlag;
    }

    /**
     * 设置消息是否已查看
1：签收
0：未签收

     *
     * @param signFlag 消息是否已查看
1：签收
0：未签收

     */
    public void setSignFlag(Integer signFlag) {
        this.signFlag = signFlag;
    }

    /**
     * 获取消息创建时间
     *
     * @return create_time - 消息创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置消息创建时间
     *
     * @param createTime 消息创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}