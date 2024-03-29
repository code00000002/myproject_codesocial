package com.codesocial.user.dao;

import com.codesocial.user.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    public User findByMobile(String mobile);

    /**
     * 增加粉丝数
     *
     * @param userid
     * @param x
     */
    @Modifying
    @Query("update User u set u.fanscount=u.fanscount + ?2 where u.id = ?1")
    public void incFanscount(String userid, int x);

    @Modifying
    @Query("update User u set u.followcount=u.followcount+?2 where u.id=?1")
    public void incFollowcount(String userid, int x);
}
