package com.codesocial.spit.controller;

import com.codesocial.entity.Result;
import com.codesocial.entity.StatusCode;
import com.codesocial.spit.pojo.Spit;
import com.codesocial.spit.service.SpitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/spit")
@CrossOrigin
public class SpitController {
    @Autowired
    private SpitService spitService;

    @PostMapping
    public Result add(@RequestBody Spit spit) {
        spitService.add(spit);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询全部成功", spitService.findAll());
    }

    @GetMapping("/{spitId}")
    public Result findById(@PathVariable("spitId") String id) {
        return new Result(true, StatusCode.OK, "根据id查询成功", spitService.findById(id));
    }

    @PutMapping("{spitId}")
    public Result update(@RequestBody Spit spit, @PathVariable("spitId") String id) {
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");

    }

    @DeleteMapping("{spitId}")
    public Result delete(@PathVariable("spitId") String id) {
        spitService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据上级id查询吐槽分页数据
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/comment/{parentid}/{page}/{size}")
    public Result findByParentId(@PathVariable("parentid") String parentid,
                                 @PathVariable("page") int page, @PathVariable("size") int size) {
        Page<Spit> spitPage = spitService.findByParentId(parentid, page, size);
        return new Result(true, StatusCode.OK, "根据上级id查询吐槽分页数据成功", spitPage);
    }

    @PutMapping("/thumbup/{spitId}")
    public Result thumbup(@PathVariable("spitId")String spitId){
        spitService.thumbup(spitId);
        return new Result(true,StatusCode.OK,"点赞成功");
    }
}

