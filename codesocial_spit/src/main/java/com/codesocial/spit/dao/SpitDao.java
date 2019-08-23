package com.codesocial.spit.dao;

import com.codesocial.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SpitDao extends MongoRepository<Spit,String> {
    Page<Spit> findByParentid(String parentId, Pageable pageable);
}
