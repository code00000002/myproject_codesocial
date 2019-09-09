package com.codesoial.friend.controller;

import com.codesocial.entity.Result;
import com.codesocial.entity.StatusCode;
import com.codesoial.friend.service.FriendService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
@CrossOrigin
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private HttpServletRequest request;

    @PutMapping("/like/{friendid}/{type}")
    public Result addFriend(@PathVariable String friendid,@PathVariable int type){
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (null == claims){
            return new Result(false, StatusCode.ACCESSERROR, "无权访问");
        }
        if (type == 1){
            //添加好友
            friendService.addFriend(claims.getId(), friendid);
            return Result.success("添加好友成功");
        }else {
            friendService.addNoFriend(claims.getId(),friendid);
            return Result.success("添加非好友成功");
        }
    }

    @DeleteMapping("/{friendid}")
    public Result remove(@PathVariable String friendid){
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (null == claims){
            return new Result(false, StatusCode.ACCESSERROR, "无权访问");
        }
        friendService.deleteFriend(claims.getId(),friendid);
        return new Result(true, StatusCode.OK, "删除好友成功");
    }
}
