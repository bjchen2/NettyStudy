package com.study.ichat.service.impl;

import com.study.ichat.enums.MsgActionEnum;
import com.study.ichat.enums.MsgSignFlagEnum;
import com.study.ichat.enums.SearchFriendsStatusEnum;
import com.study.ichat.mapper.*;
import com.study.ichat.pojo.ChatMsg;
import com.study.ichat.pojo.FriendsRequest;
import com.study.ichat.pojo.MyFriends;
import com.study.ichat.pojo.Users;
import com.study.ichat.pojo.vo.FriendRequestVO;
import com.study.ichat.pojo.vo.MyFriendsVO;
import com.study.ichat.service.UserService;
import com.study.ichat.utils.MD5Utils;
import com.study.ichat.utils.QRCodeUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Date;
import java.util.List;

/**
 * @author Cx
 * @version jdk8 and idea On 2019/6/6 9:26
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper userMapper;

    @Autowired
    private UsersMapperCustom usersMapperCustom;

    @Autowired
    private MyFriendsMapper myFriendsMapper;

    @Autowired
    private FriendsRequestMapper friendsRequestMapper;

    @Autowired
    private ChatMsgMapper chatMsgMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private QRCodeUtils qrCodeUtils;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Users user = new Users();
        user.setUsername(username);

        //TkMybatis提供的方法，可通过对象唯一键获取对象，因为userName在数据库中已设为唯一，所以可使用该方法
        Users result = userMapper.selectOne(user);

        return result != null;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public Users queryUserForLogin(String username, String pwd) {
        //TkMybatis 提供的逆向工程查询，避免写sql语句
        Example userExample = new Example(Users.class);

        //Criteria设置查询条件
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username",username);
        criteria.andEqualTo("password",pwd);

        return userMapper.selectOneByExample(userExample);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Users saveUser(Users user) throws Exception {
        String userId = sid.nextShort();

        // 为每个用户生成一个唯一的二维码
//        String qrCodePath = "./userQrCode" + userId + "qrcode.png";
//        qrCodeUtils.createQRCode(qrCodePath, "ichat_qrcode:" + user.getUsername());
//        MultipartFile qrCodeFile = FileUtils.fileToMultipart(qrCodePath);
//
//        String qrCodeUrl = "";
//        try {
//            qrCodeUrl = fastDFSClient.uploadQRCode(qrCodeFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        user.setNickname(user.getUsername());
        user.setFaceImage("");
        user.setFaceImageBig("");
        user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
        user.setId(userId);
        user.setQrcode("");

        userMapper.insert(user);

        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Users updateUserInfo(Users user) {
        userMapper.updateByPrimaryKeySelective(user);
        return userMapper.selectByPrimaryKey(user.getId());
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public Integer preconditionSearchFriends(String myUserId, String friendUsername) {

        Users user = queryUserInfoByUsername(friendUsername);

        // 1. 搜索的用户如果不存在，返回[无此用户]
        if (user == null) {
            return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
        }

        // 2. 搜索账号是你自己，返回[不能添加自己]
        if (user.getId().equals(myUserId)) {
            return SearchFriendsStatusEnum.NOT_YOURSELF.status;
        }

        // 3. 搜索的朋友已经是你的好友，返回[该用户已经是你的好友]
        Example mfe = new Example(MyFriends.class);
        Example.Criteria mfc = mfe.createCriteria();
        mfc.andEqualTo("myUserId", myUserId);
        mfc.andEqualTo("myFriendUserId", user.getId());
        MyFriends myFriendsRel = myFriendsMapper.selectOneByExample(mfe);
        if (myFriendsRel != null) {
            return SearchFriendsStatusEnum.ALREADY_FRIENDS.status;
        }

        return SearchFriendsStatusEnum.SUCCESS.status;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public Users queryUserInfoByUsername(String username) {
        Example ue = new Example(Users.class);
        Example.Criteria uc = ue.createCriteria();
        uc.andEqualTo("username", username);
        return userMapper.selectOneByExample(ue);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void sendFriendRequest(String myUserId, String friendUsername) {

        // 根据用户名把朋友信息查询出来
        Users friend = queryUserInfoByUsername(friendUsername);

        // 1. 查询发送好友请求记录表
        Example fre = new Example(FriendsRequest.class);
        Example.Criteria frc = fre.createCriteria();
        frc.andEqualTo("sendUserId", myUserId);
        frc.andEqualTo("acceptUserId", friend.getId());
        FriendsRequest friendRequest = friendsRequestMapper.selectOneByExample(fre);
        if (friendRequest == null) {
            // 2. 如果不是你的好友，并且好友记录没有添加，则新增好友请求记录
            String requestId = sid.nextShort();

            FriendsRequest request = new FriendsRequest();
            request.setId(requestId);
            request.setSendUserId(myUserId);
            request.setAcceptUserId(friend.getId());
            request.setRequestDateTime(new Date());
            friendsRequestMapper.insert(request);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId) {
        return usersMapperCustom.queryFriendRequestList(acceptUserId);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteFriendRequest(String sendUserId, String acceptUserId) {
        Example fre = new Example(FriendsRequest.class);
        Example.Criteria frc = fre.createCriteria();
        frc.andEqualTo("sendUserId", sendUserId);
        frc.andEqualTo("acceptUserId", acceptUserId);
        friendsRequestMapper.deleteByExample(fre);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void passFriendRequest(String sendUserId, String acceptUserId) {
        saveFriends(sendUserId, acceptUserId);
        saveFriends(acceptUserId, sendUserId);
        deleteFriendRequest(sendUserId, acceptUserId);

//        Channel sendChannel = UserChannelRel.get(sendUserId);
//        if (sendChannel != null) {
//            // 使用websocket主动推送消息到请求发起者，更新他的通讯录列表为最新
//            DataContent dataContent = new DataContent();
//            dataContent.setAction(MsgActionEnum.PULL_FRIEND.type);
//
//            sendChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(dataContent)));
//        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveFriends(String sendUserId, String acceptUserId) {
        MyFriends myFriends = new MyFriends();
        String recordId = sid.nextShort();
        myFriends.setId(recordId);
        myFriends.setMyFriendUserId(acceptUserId);
        myFriends.setMyUserId(sendUserId);
        myFriendsMapper.insert(myFriends);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<MyFriendsVO> queryMyFriends(String userId) {
        List<MyFriendsVO> myFriends = usersMapperCustom.queryMyFriends(userId);
        return myFriends;
    }

//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    @Override
//    public String saveMsg(WsChatMsg wsChatMsg) {
//
//        ChatMsg msgDB = new ChatMsg();
//        String msgId = sid.nextShort();
//        msgDB.setId(msgId);
//        msgDB.setAcceptUserId(chatMsg.getReceiverId());
//        msgDB.setSendUserId(chatMsg.getSenderId());
//        msgDB.setCreateTime(new Date());
//        msgDB.setSignFlag(MsgSignFlagEnum.unsign.type);
//        msgDB.setMsg(chatMsg.getMsg());
//
//        chatMsgMapper.insert(msgDB);
//
//        return msgId;
//    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void updateMsgSigned(List<String> msgIdList) {
        usersMapperCustom.batchUpdateMsgSigned(msgIdList);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<ChatMsg> getUnReadMsgList(String acceptUserId) {

        Example chatExample = new Example(ChatMsg.class);
        Example.Criteria chatCriteria = chatExample.createCriteria();
        chatCriteria.andEqualTo("signFlag", 0);
        chatCriteria.andEqualTo("acceptUserId", acceptUserId);

        return chatMsgMapper.selectByExample(chatExample);
    }
}
