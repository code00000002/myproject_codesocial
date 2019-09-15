package com.codesocial.base.controller;

import com.codesocial.base.pojo.Label;
import com.codesocial.base.service.LabelService;
import com.codesocial.entity.PageResult;
import com.codesocial.entity.Result;
import com.codesocial.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("label")
@CrossOrigin //允许跨域请求
@RefreshScope //自定义配置项，需要在类上加这个注解
public class LabelController {
    @Autowired
    private LabelService labelService;
    /**
     * 增加
     */
    @PostMapping
    public Result add(@RequestBody Label label){
        labelService.add(label);
        return Result.success("添加成功");
    }

    /**
     * 修改
     */
    @PutMapping("/{labelId}")
    public Result update(@PathVariable("labelId")String labelId, @RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return Result.success("修改成功");
    }

    /**
     * 查询（全部）
     */
    @GetMapping
    public Result findAll(){
        List<Label> list = labelService.findAll();
        return Result.success("查询全部成功",list);
    }

    /**
     * 根据id查询
     */
    @GetMapping("/{labelId}")
    public Result findById(@PathVariable("labelId") String labelId){
        Label label = labelService.findById(labelId);
        return Result.success("根据id查询成功",label);
    }

    /**
     * 删除
     */
    @DeleteMapping("/{labelId}")
    public Result deleteById(@PathVariable("labelId") String labelId){
        labelService.deleteById(labelId);
        return Result.success("根据id删除成功");
    }

    /**
     * 按条件搜索查询
     * @return
     */
    @PostMapping("/search")
    public Result findSearch(@RequestBody Map<String,Object> map){
        return new Result(true, StatusCode.OK, "按条件搜索查询成功", labelService.findSearch(map));
    }

    /**
     * 按条件查询，结果分页显示
     * /search/{page}/{size}
     */
    @PostMapping("/search/{page}/{size}")
    public Result findSearchPage(@RequestBody Map<String,Object> map,@PathVariable("page")int page,@PathVariable("size") int size){
        Page<Label> labelPage = labelService.findPage(map,page,size);
        PageResult<Label> pageResult = new PageResult<Label>(labelPage.getTotalElements(),labelPage.getContent());
        return Result.success("按条件分页查询成功",pageResult);
    }
    @Value("${abc}")
    private String abc;

    @GetMapping("/abc")
    public Result abc(){
        return Result.success("成功",abc);
    }
}
