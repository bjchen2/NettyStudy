package com.study.ichat.service;

import com.study.ichat.pojo.ChatMsg;
import com.study.ichat.pojo.Users;
import com.study.ichat.pojo.vo.FriendRequestVO;
import com.study.ichat.pojo.vo.MyFriendsVO;

import java.util.List;

/**
 * @author Cx
 * @version jdk8 and idea On 2019/6/6 9:21
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     * @param username 需要查询的用户名
     * @return 是否存在
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 查询用户是否存在
     * @param username 用户名
     * @param pwd 密码（MD5加密过的）
     * @return 对应用户
     */
    Users queryUserForLogin(String username, String pwd);

    /**
     * 用户注册（只需提供username和password），昵称默认为用户名
     * @param user 需要注册的用户
     * @return 注册成功的用户
     * @throws Exception
     */
    Users saveUser(Users user) throws Exception;

    /**
     * 通过用户ID，修改用户记录
     * @param user 修改后的用户信息
     * @return 修改后的用户
     */
    Users updateUserInfo(Users user);

    /**
     * 搜索朋友的前置条件
     * @param myUserId       搜索者ID
     * @param friendUsername 需要搜索的好友名称
     * @return 搜索结果
     */
    Integer preconditionSearchFriends(String myUserId, String friendUsername);

    /**
     * 根据用户名查询用户对象
     * @param username
     * @return
     */
    Users queryUserInfoByUsername(String username);

    /**
     * 添加好友请求记录，保存到数据库
     */
    void sendFriendRequest(String myUserId, String friendUsername);

    /**
     * 查询好友请求
     */
    List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

    /**
     * 删除好友请求记录
     */
    void deleteFriendRequest(String sendUserId, String acceptUserId);

    /**
     * 通过好友请求：1. 保存好友 2. 逆向保存好友 3. 删除好友请求记录
     */
    void passFriendRequest(String sendUserId, String acceptUserId);

    /**
     * 查询好友列表
     */
    List<MyFriendsVO> queryMyFriends(String userId);

//    /**
//     * 保存聊天消息到数据库
//     */
//    String saveMsg(ChatMsg chatMsg);

    /**
     * 批量签收消息
     */
    void updateMsgSigned(List<String> msgIdList);

    /**
     * 获取未签收消息列表
     */
    List<ChatMsg> getUnReadMsgList(String acceptUserId);
}
