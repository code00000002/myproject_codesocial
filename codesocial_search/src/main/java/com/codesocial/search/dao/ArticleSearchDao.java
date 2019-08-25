package com.codesocial.search.dao;

import com.codesocial.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleSearchDao extends ElasticsearchRepository<Article,String> {
    public Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
