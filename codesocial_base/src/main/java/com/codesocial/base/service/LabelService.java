package com.codesocial.base.service;

import com.codesocial.base.dao.LabelDao;
import com.codesocial.base.pojo.Label;
import com.codesocial.entity.PageResult;
import com.codesocial.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 新增
     * @param label
     */
    public void add(Label label) {
        label.setId(idWorker.nextId() +"");
        labelDao.save(label);
    }

    /**
     * 修改
     * @param label
     */
    public void update(Label label) {
        //springdatajpa，save方法，先查询，没有就插入记录，有就修改记录
        labelDao.save(label);
    }

    /**
     * 查询全部
     * @return
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据id查询
     * @param labelId
     * @return
     */
    public Label findById(String labelId) {
        return labelDao.findById(labelId).get();
    }

    /**
     * 根据id删除
     * @param labelId
     */
    public void deleteById(String labelId) {
        labelDao.deleteById(labelId);
    }

    /**
     * 按条件搜索查询
     * @param map
     * @return
     */
    public List<Label> findSearch(Map<String, Object> map) {
        Specification specification = createSpecification(map);
        return labelDao.findAll(specification);
    }

    private Specification createSpecification(Map<String, Object> map) {
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                //有条件的情况下才进行拼接
                if (!StringUtils.isEmpty(map.get("labelname"))){
                    predicates.add(cb.like(root.get("labelname").as(String.class), "%" + map.get("labelname") + "%"));
                }
                if (!StringUtils.isEmpty(map.get("state"))){
                    predicates.add(cb.like(root.get("state").as(String.class), "%" + map.get("state") + "%"));
                }
                if (!StringUtils.isEmpty(map.get("recommend"))){
                    predicates.add(cb.like(root.get("recommend").as(String.class), "%" + map.get("recommend") + "%"));
                }
                //把所有条件 用and连接
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    /**
     * 按条件查询，分页显示
     * @param map
     * @param page
     * @param size
     * @return
     */
    public Page<Label> findPage(Map<String, Object> map, int page, int size) {
        Specification specification = createSpecification(map);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return labelDao.findAll(specification,pageRequest);
    }
}
