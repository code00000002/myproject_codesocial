package com.codesocial.search.controller;

import com.codesocial.entity.PageResult;
import com.codesocial.entity.Result;
import com.codesocial.entity.StatusCode;
import com.codesocial.search.pojo.Article;
import com.codesocial.search.service.ArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleSearchController {
    @Autowired
    private ArticleSearchService articleSearchService;

    @PostMapping
    public Result add(@RequestBody Article article){
        articleSearchService.add(article);
        return new Result(true, StatusCode.OK, "新增文章搜索成功");
    }

    @GetMapping("/search/{keywords}/{page}/{size}")
    public Result findByTitleOrContentLike(@PathVariable String keywords,
                                           @PathVariable int page,@PathVariable int size){
        Page<Article> articlePage = articleSearchService.findByTitleOrContentLike(keywords, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(articlePage.getTotalElements(), articlePage.getContent()));
    }
}
