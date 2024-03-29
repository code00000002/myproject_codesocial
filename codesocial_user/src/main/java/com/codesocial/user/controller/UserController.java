package com.codesocial.user.controller;

import com.codesocial.entity.PageResult;
import com.codesocial.entity.Result;
import com.codesocial.entity.StatusCode;
import com.codesocial.user.pojo.User;
import com.codesocial.user.service.UserService;
import com.codesocial.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true, StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@PostMapping("/register/{code}")
	public Result add(@RequestBody User user,@PathVariable String code  ){
		userService.add(user,code);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private JwtUtil jwtUtil;
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id,HttpServletRequest req){
		/*Claims claims = (Claims) req.getAttribute("admin_claims");
		if (null == claims){
			return new Result(false, StatusCode.ACCESSERROR, "权限不足");
		}
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");*/
		if (null == req.getAttribute("admin_claims")) {
			return new Result(false, StatusCode.ACCESSERROR, "权限不足");
		}
		userService.deleteById(id);
		return new Result(true, StatusCode.OK, "删除成功");
	}
	@PostMapping("/sendsms/{mobile}")
	public Result sendSms(@PathVariable String mobile){
		userService.sendSms(mobile);
		return new Result(true, StatusCode.OK, "发送短信成功");
	}

	/**
	 * 用户登录
	 * @param map
	 * @return
	 */
	@PostMapping("/login")
	public Result login(@RequestBody Map<String,String> map){
		User user = userService.findByMobileAndPassword(map.get("mobile"), map.get("password"));
		if (null != user){
			String token = jwtUtil.createJWT(user.getId(), user.getNickname(), "user");
			Map jwtMap = new HashMap();
			jwtMap.put("token", token);
			jwtMap.put("name", user.getNickname());
			jwtMap.put("avatar", user.getAvatar());

			return new Result(true, StatusCode.OK, "登录成功", jwtMap);
		}else {
			return new Result(false, StatusCode.ERROR, "用户名或者密码错误");
		}
	}

	/**
	 * 增加粉丝数
	 * @param userid
	 * @param x
	 * @return
	 */
	@PostMapping("/incfans/{userid}/{x}")
	public Result incFanscount(@PathVariable String userid,@PathVariable int x){
		userService.incFanscount(userid,x);
		return new Result(true, StatusCode.OK, "增加粉丝数成功");
	}

	@PostMapping("/incfollow/{userid}/{x}")
	public Result incFollowcount(@PathVariable String userid, @PathVariable int x){
		userService.incFollowcount(userid, x);
		return new Result(true, StatusCode.OK, "变更关注数成功");
	}
}
