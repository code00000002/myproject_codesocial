package com.codesocial.base.service;

import com.codesocial.base.dao.LabelDao;
import com.codesocial.base.pojo.Label;
import com.codesocial.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
