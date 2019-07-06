package com.study.ichat.mapper;

import com.study.ichat.pojo.Users;
import com.study.ichat.pojo.vo.FriendRequestVO;
import com.study.ichat.pojo.vo.MyFriendsVO;
import com.study.ichat.utils.MyMapper;

import java.util.List;

/**
 * @author cxd27419
 */
public interface UsersMapper extends MyMapper<Users> {
    List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

    List<MyFriendsVO> queryMyFriends(String userId);

    void batchUpdateMsgSigned(List<String> msgIdList);
}