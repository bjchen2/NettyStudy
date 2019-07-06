package com.study.ichat.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author cxd27419
 */
@Table(name = "friends_request")
public class FriendsRequest {
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
     * 发送请求时间
     */
    @Column(name = "request_date_time")
    private Date requestDateTime;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
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
     * 获取发送请求时间
     *
     * @return request_date_time - 发送请求时间
     */
    public Date getRequestDateTime() {
        return requestDateTime;
    }

    /**
     * 设置发送请求时间
     *
     * @param requestDateTime 发送请求时间
     */
    public void setRequestDateTime(Date requestDateTime) {
        this.requestDateTime = requestDateTime;
    }
}