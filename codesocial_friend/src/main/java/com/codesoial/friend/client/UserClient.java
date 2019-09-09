package com.codesoial.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("codesocial-user")
public interface UserClient {
    /**
     * 增加粉丝数
     * @param userid
     * @param x
     */
    @PostMapping("/user/incfans/{userid}/{x}")
    public void incFanscount(@PathVariable("userid") String userid, @PathVariable("x") int x);

    /**
     * 增加关注数
     * @param userid
     * @param x
     */
    @PostMapping("/incfollow/{userid}/{x}")
    public void incFollowcount(@PathVariable("userid") String userid, @PathVariable("x") int x);
}
