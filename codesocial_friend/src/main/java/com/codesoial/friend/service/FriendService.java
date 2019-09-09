package com.codesoial.friend.service;

import com.codesoial.friend.client.UserClient;
import com.codesoial.friend.dao.FriendDao;
import com.codesoial.friend.dao.NoFriendDao;
import com.codesoial.friend.pojo.Friend;
import com.codesoial.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendService {
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private NoFriendDao noFriendDao;
    @Autowired
    private UserClient userClient;

    @Transactional
    public int addFriend(String userid,String friendid){
        //增加自己的关注数
        userClient.incFanscount(userid,1);
        //增加对方的粉丝数
        userClient.incFollowcount(friendid,1);
        //判断如果用户已经添加了好友，则返回0，表示已经添加过了，不再添加好友
        if (friendDao.selectCount(userid,friendid)>0){
            return 0;
        }
        //向喜欢表中添加记录
        Friend friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        //判断对方是否也喜欢你，如果喜欢就更新islike=1
        if (friendDao.selectCount(friendid,userid)>0){
            friendDao.updateLike(userid,friendid,"1");
            friendDao.updateLike(friendid,userid,"1");
        }
        return 1;
    }


    /**
     * 向不喜欢表添加记录
     * @param userid
     * @param friendid
     */
    public void addNoFriend(String userid,String friendid){
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }

    /**
     * 删除好友
     * @param userid
     * @param friendid
     */
    @Transactional
    public void deleteFriend(String userid,String friendid){
        //减少自己的关注数
        userClient.incFollowcount(userid,-1);
        //减少对方的粉丝数
        userClient.incFollowcount(friendid,-1);
        friendDao.deleteFriend(userid,friendid);
        friendDao.updateLike(userid,friendid,"0");
        addNoFriend(userid,friendid);
    }
}
