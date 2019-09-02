package com.codesocial.qa.controller;

import com.codesocial.entity.PageResult;
import com.codesocial.entity.Result;
import com.codesocial.entity.StatusCode;
import com.codesocial.qa.client.LabelClient;
import com.codesocial.qa.pojo.Problem;
import com.codesocial.qa.service.ProblemService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;


    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findAll());
    }


    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findSearch(searchMap));
    }

    @Autowired
    private HttpServletRequest request;
    /**
     * 增加
     *
     * @param problem
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Problem problem) {
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (null == claims){
            return new Result(false,StatusCode.ACCESSERROR,"无权访问");
        }
        problem.setId(claims.getId());
        problemService.add(problem);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param problem
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Problem problem, @PathVariable String id) {
        problem.setId(id);
        problemService.update(problem);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        problemService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 查询最新问答列表
     * /newlist/{label}/{page}/{size}
     *
     * @return
     */
    @GetMapping("/newlist/{label}/{page}/{size}")
    public Result findNewest(@PathVariable("label") String label, @PathVariable("page") int page, @PathVariable("size") int size) {
        Page<Problem> problemPage = problemService.findNewest(label,page,size);
        return Result.success("查询最新问答列表成功", new PageResult<>(problemPage.getTotalElements(), problemPage.getContent()));
    }

    /**
     * 热门问答列表
     * /hotlist/{label}/{page}/{size}
     *
     * @return
     */
    @GetMapping("/hotlist/{label}/{page}/{size}")
    public Result findHotest(@PathVariable("label") String label, @PathVariable("page") int page, @PathVariable("size") int size) {
        Page<Problem> problemPage = problemService.findHotest(label,page,size);
        return Result.success("查询最新问答列表成功", new PageResult<>(problemPage.getTotalElements(), problemPage.getContent()));
    }

    /**
     * 等待问答列表
     * /waitlist/{label}/{page}/{size}
     *
     * @return
     */
    @GetMapping("/waitlist/{label}/{page}/{size}")
    public Result findWaitest(@PathVariable("label") String label, @PathVariable("page") int page, @PathVariable("size") int size) {
        Page<Problem> problemPage = problemService.findWaitest(label,page,size);
        return Result.success("查询最新问答列表成功", new PageResult<>(problemPage.getTotalElements(), problemPage.getContent()));
    }

    @Autowired
    private LabelClient labelClient;

    @RequestMapping(value = "/mylabel/{labelid}")
    public Result findLabelById(@PathVariable  String labelid){
        Result result = labelClient.findById(labelid);
        return result;
    }
}
