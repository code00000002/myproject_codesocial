package com.codesocial.recruit.dao;

import com.codesocial.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

    List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String s);

    List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String s);
}
