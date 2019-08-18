package com.codesocial.qa.dao;

import com.codesocial.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    @Query("select p from Problem p where id in (select problemid from Pl where labelid = ?1) order by replytime desc")
    Page<Problem> findNewest(String label, Pageable pageable);

    @Query("select p from Problem p where id in (select problemid from Pl where labelid = ?1) order by reply desc")
    Page<Problem> findHotest(String label, Pageable pageable);

    @Query("select p from Problem p where id in (select problemid from Pl where labelid = ?1) and reply = 0 order by createtime desc")
    Page<Problem> findWaitest(String label, Pageable pageable);
}
