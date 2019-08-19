package com.codesocial.article.dao;

import com.codesocial.article.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    @Query("update Article set state = '1' where id = ?1")
    @Modifying
    void updateExamine(String articleId);

    @Query("update Article set thumbup = thumbup + 1 where id = ?1")
    @Modifying
    void updateThumbup(String articleId);
}
