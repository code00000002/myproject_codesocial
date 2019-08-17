package com.codesocial.base.controller;

import com.codesocial.base.pojo.Label;
import com.codesocial.base.service.LabelService;
import com.codesocial.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("label")
@CrossOrigin //允许跨域请求
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
}
