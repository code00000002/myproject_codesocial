package com.codesocial.search.service;

import com.codesocial.search.dao.ArticleSearchDao;
import com.codesocial.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleSearchService {
    @Autowired
    private ArticleSearchDao articleSearchDao;

    public void add(Article article){
        articleSearchDao.save(article);
    }

    public Page<Article> findByTitleOrContentLike(String keywords,int page,int size){
        Pageable pageable = PageRequest.of(page, size);
        return articleSearchDao.findByTitleOrContentLike(keywords, keywords, pageable);
    }
}
