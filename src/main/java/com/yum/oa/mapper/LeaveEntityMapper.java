package com.yum.oa.mapper;

import com.yum.oa.common.base.BaseEntityMapper;
import com.yum.oa.model.entity.LeaveEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LeaveEntityMapper extends BaseEntityMapper<LeaveEntity> {

    List<LeaveEntity> findPendingApproveList(@Param("approverId") Long approverId, @Param("processIds") Set<String> processIds);

    void updateApproveStatus(Long id, Integer approveStatus);

}