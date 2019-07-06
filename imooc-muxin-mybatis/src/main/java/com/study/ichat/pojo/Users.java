package com.study.ichat.pojo;

import javax.persistence.*;

public class Users {
    @Id
    private String id;

    /**
     * 用户名，账号
     */
    private String username;

    /**
     * 密码(已加密)
     */
    private String password;

    /**
     * 用户头像预览图（经过压缩，较小），如果没有默认给一张
     */
    @Column(name = "face_image")
    private String faceImage;

    /**
     * 用户头像原图，如果没有默认给一张
     */
    @Column(name = "face_image_big")
    private String faceImageBig;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户二维码图（可用于扫码加好友），新用户注册后默认后台生成，并上传到fastdfs
     */
    private String qrcode;

    /**
     * clientId，设备唯一ID
     */
    private String cid;

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
     * 获取用户名，账号
     *
     * @return username - 用户名，账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名，账号
     *
     * @param username 用户名，账号
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码(已加密)
     *
     * @return password - 密码(已加密)
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码(已加密)
     *
     * @param password 密码(已加密)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取用户头像预览图（经过压缩，较小），如果没有默认给一张
     *
     * @return face_image - 用户头像预览图（经过压缩，较小），如果没有默认给一张
     */
    public String getFaceImage() {
        return faceImage;
    }

    /**
     * 设置用户头像预览图（经过压缩，较小），如果没有默认给一张
     *
     * @param faceImage 用户头像预览图（经过压缩，较小），如果没有默认给一张
     */
    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    /**
     * 获取用户头像原图，如果没有默认给一张
     *
     * @return face_image_big - 用户头像原图，如果没有默认给一张
     */
    public String getFaceImageBig() {
        return faceImageBig;
    }

    /**
     * 设置用户头像原图，如果没有默认给一张
     *
     * @param faceImageBig 用户头像原图，如果没有默认给一张
     */
    public void setFaceImageBig(String faceImageBig) {
        this.faceImageBig = faceImageBig;
    }

    /**
     * 获取用户昵称
     *
     * @return nickname - 用户昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置用户昵称
     *
     * @param nickname 用户昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取用户二维码图（可用于扫码加好友），新用户注册后默认后台生成，并上传到fastdfs
     *
     * @return qrcode - 用户二维码图（可用于扫码加好友），新用户注册后默认后台生成，并上传到fastdfs
     */
    public String getQrcode() {
        return qrcode;
    }

    /**
     * 设置用户二维码图（可用于扫码加好友），新用户注册后默认后台生成，并上传到fastdfs
     *
     * @param qrcode 用户二维码图（可用于扫码加好友），新用户注册后默认后台生成，并上传到fastdfs
     */
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    /**
     * 获取clientId，设备唯一ID
     *
     * @return cid - clientId，设备唯一ID
     */
    public String getCid() {
        return cid;
    }

    /**
     * 设置clientId，设备唯一ID
     *
     * @param cid clientId，设备唯一ID
     */
    public void setCid(String cid) {
        this.cid = cid;
    }
}