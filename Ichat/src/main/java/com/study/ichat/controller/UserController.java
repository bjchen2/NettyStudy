package com.study.ichat.controller;

import com.study.ichat.pojo.Users;
import com.study.ichat.pojo.vo.UsersVO;
import com.study.ichat.service.UserService;
import com.study.ichat.utils.IMoocJSONResult;
import com.study.ichat.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Cx
 * @version jdk8 and idea On 2019/6/4 21:20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册/登录
     * @param user 登录/注册用户，只需提供cid、username和password（未加密）
     * @return 响应结果
     */
    @PostMapping("/registerOrLogin")
    public IMoocJSONResult registerOrLogin(@RequestBody Users user) throws Exception {
        // 0. 判断用户名和密码不能为空
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return IMoocJSONResult.errorMsg("用户名或密码不能为空...");
        }

        // 1. 判断用户名是否存在，如果存在就登录，如果不存在则注册
        boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
        Users userResult;
        if (usernameIsExist) {
            // 1.1 登录
            userResult = userService.queryUserForLogin(user.getUsername(), MD5Utils.getMD5Str(user.getPassword()));
            if (userResult == null) {
                return IMoocJSONResult.errorMsg("用户名或密码不正确...");
            }
        } else {
            // 1.2 注册用户
            userResult = userService.saveUser(user);
        }

        UsersVO userVO = new UsersVO();
        BeanUtils.copyProperties(userResult, userVO);

        return IMoocJSONResult.ok(userVO);
    }

//    /**
//     * 上传用户头像
//     */
//    @PostMapping("/uploadFaceBase64")
//    public IMoocJSONResult uploadFaceBase64(@RequestBody UsersBO userBO) throws Exception {
//
//        // 获取前端传过来的base64字符串, 然后转换为文件对象再上传
//        String base64Data = userBO.getFaceData();
//        String userFacePath = "C:\\" + userBO.getUserId() + "userface64.png";
//        FileUtils.base64ToFile(userFacePath, base64Data);
//
//        // 上传文件到fastdfs
//        MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
//        String url = fastDFSClient.uploadBase64(faceFile);
//        System.out.println(url);
//
////		"dhawuidhwaiuh3u89u98432.png"
////		"dhawuidhwaiuh3u89u98432_80x80.png"
//
//        // 获取缩略图的url
//        String thump = "_80x80.";
//        String arr[] = url.split("\\.");
//        String thumpImgUrl = arr[0] + thump + arr[1];
//
//        // 更细用户头像
//        Users user = new Users();
//        user.setId(userBO.getUserId());
//        user.setFaceImage(thumpImgUrl);
//        user.setFaceImageBig(url);
//
//        Users result = userService.updateUserInfo(user);
//
//        return IMoocJSONResult.ok(result);
//    }
//
//    /**
//     * 设置用户昵称
//     */
//    @PostMapping("/setNickname")
//    public IMoocJSONResult setNickname(@RequestBody UsersBO userBO) throws Exception {
//
//        Users user = new Users();
//        user.setId(userBO.getUserId());
//        user.setNickname(userBO.getNickname());
//
//        Users result = userService.updateUserInfo(user);
//
//        return IMoocJSONResult.ok(result);
//    }
//
//    /**
//     * 搜索好友接口, 根据账号做匹配查询而不是模糊查询
//     */
//    @PostMapping("/search")
//    public IMoocJSONResult searchUser(String myUserId, String friendUsername)
//            throws Exception {
//
//        // 0. 判断 myUserId friendUsername 不能为空
//        if (StringUtils.isBlank(myUserId)
//                || StringUtils.isBlank(friendUsername)) {
//            return IMoocJSONResult.errorMsg("");
//        }
//
//        // 前置条件 - 1. 搜索的用户如果不存在，返回[无此用户]
//        // 前置条件 - 2. 搜索账号是你自己，返回[不能添加自己]
//        // 前置条件 - 3. 搜索的朋友已经是你的好友，返回[该用户已经是你的好友]
//        Integer status = userService.preconditionSearchFriends(myUserId, friendUsername);
//        if (status == SearchFriendsStatusEnum.SUCCESS.status) {
//            Users user = userService.queryUserInfoByUsername(friendUsername);
//            UsersVO userVO = new UsersVO();
//            BeanUtils.copyProperties(user, userVO);
//            return IMoocJSONResult.ok(userVO);
//        } else {
//            String errorMsg = SearchFriendsStatusEnum.getMsgByKey(status);
//            return IMoocJSONResult.errorMsg(errorMsg);
//        }
//    }
//
//
//    /**
//     * 发送添加好友的请求
//     */
//    @PostMapping("/addFriendRequest")
//    public IMoocJSONResult addFriendRequest(String myUserId, String friendUsername)
//            throws Exception {
//
//        // 0. 判断 myUserId friendUsername 不能为空
//        if (StringUtils.isBlank(myUserId)
//                || StringUtils.isBlank(friendUsername)) {
//            return IMoocJSONResult.errorMsg("");
//        }
//
//        // 前置条件 - 1. 搜索的用户如果不存在，返回[无此用户]
//        // 前置条件 - 2. 搜索账号是你自己，返回[不能添加自己]
//        // 前置条件 - 3. 搜索的朋友已经是你的好友，返回[该用户已经是你的好友]
//        Integer status = userService.preconditionSearchFriends(myUserId, friendUsername);
//        if (status == SearchFriendsStatusEnum.SUCCESS.status) {
//            userService.sendFriendRequest(myUserId, friendUsername);
//        } else {
//            String errorMsg = SearchFriendsStatusEnum.getMsgByKey(status);
//            return IMoocJSONResult.errorMsg(errorMsg);
//        }
//
//        return IMoocJSONResult.ok();
//    }
//
//    /**
//     * 发送添加好友的请求
//     */
//    @PostMapping("/queryFriendRequests")
//    public IMoocJSONResult queryFriendRequests(String userId) {
//
//        // 0. 判断不能为空
//        if (StringUtils.isBlank(userId)) {
//            return IMoocJSONResult.errorMsg("");
//        }
//
//        // 1. 查询用户接受到的朋友申请
//        return IMoocJSONResult.ok(userService.queryFriendRequestList(userId));
//    }
//
//
//    /**
//     * 接受方 通过或者忽略朋友请求
//     */
//    @PostMapping("/operFriendRequest")
//    public IMoocJSONResult operFriendRequest(String acceptUserId, String sendUserId,
//                                             Integer operType) {
//
//        // 0. acceptUserId sendUserId operType 判断不能为空
//        if (StringUtils.isBlank(acceptUserId)
//                || StringUtils.isBlank(sendUserId)
//                || operType == null) {
//            return IMoocJSONResult.errorMsg("");
//        }
//
//        // 1. 如果operType 没有对应的枚举值，则直接抛出空错误信息
//        if (StringUtils.isBlank(OperatorFriendRequestTypeEnum.getMsgByType(operType))) {
//            return IMoocJSONResult.errorMsg("");
//        }
//
//        if (operType == OperatorFriendRequestTypeEnum.IGNORE.type) {
//            // 2. 判断如果忽略好友请求，则直接删除好友请求的数据库表记录
//            userService.deleteFriendRequest(sendUserId, acceptUserId);
//        } else if (operType == OperatorFriendRequestTypeEnum.PASS.type) {
//            // 3. 判断如果是通过好友请求，则互相增加好友记录到数据库对应的表
//            //	   然后删除好友请求的数据库表记录
//            userService.passFriendRequest(sendUserId, acceptUserId);
//        }
//
//        // 4. 数据库查询好友列表
//        List<MyFriendsVO> myFirends = userService.queryMyFriends(acceptUserId);
//
//        return IMoocJSONResult.ok(myFirends);
//    }
//
//    /**
//     * 查询我的好友列表
//     */
//    @PostMapping("/myFriends")
//    public IMoocJSONResult myFriends(String userId) {
//        // 0. userId 判断不能为空
//        if (StringUtils.isBlank(userId)) {
//            return IMoocJSONResult.errorMsg("");
//        }
//
//        // 1. 数据库查询好友列表
//        List<MyFriendsVO> myFirends = userService.queryMyFriends(userId);
//
//        return IMoocJSONResult.ok(myFirends);
//    }
//
//    /**
//     *
//     * 用户手机端获取未签收的消息列表
//     */
//    @PostMapping("/getUnReadMsgList")
//    public IMoocJSONResult getUnReadMsgList(String acceptUserId) {
//        // 0. userId 判断不能为空
//        if (StringUtils.isBlank(acceptUserId)) {
//            return IMoocJSONResult.errorMsg("");
//        }
//
//        // 查询列表
//        List<ChatMsg> unreadMsgList = userService.getUnReadMsgList(acceptUserId);
//
//        return IMoocJSONResult.ok(unreadMsgList);
//    }
}
