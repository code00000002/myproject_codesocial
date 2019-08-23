package com.codesocial.spit.service;

import com.codesocial.exception.MyException;
import com.codesocial.spit.dao.SpitDao;
import com.codesocial.spit.pojo.Spit;
import com.codesocial.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SpitService {
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部记录
     *
     * @return
     */
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param spit
     */
    public void add(Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        if (!StringUtils.isEmpty(spit.getParentid())) {
            //如果这是一条评论
            //就增加吐槽下面的评论数
            Query query=new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }
        spitDao.save(spit);
    }

    /**
     * 修改
     *
     * @param spit
     */
    public void update(Spit spit) {
        spitDao.save(spit);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    public Page<Spit> findByParentId(String parentId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentId, pageable);
    }

    public void thumbup(String spitId) {
        String key = "userId_" + spitId;
        if (null != redisTemplate.opsForValue().get(key)) {
            throw new MyException("您已经点过赞了，不能重复点赞");
        }
        /**
         * Query query,条件
         * Update update,更新的值
         * String collectionName，集合，表
         */
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");
        //存入redis一条记录
        redisTemplate.opsForValue().set(key, 1);
    }
}
