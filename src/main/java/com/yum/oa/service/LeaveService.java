package com.yum.oa.service;

import com.github.pagehelper.PageInfo;
import com.yum.oa.common.result.ResultBean;
import com.yum.oa.model.entity.LeaveEntity;
import com.yum.oa.model.vo.LeaveOutVo;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

/**
 * @author gaoyu
 * @version v
 * @description
 * @date 2020/8/21
 **/
public interface LeaveService {

    ResultBean<String> saveOne(LeaveEntity entity);

    ResultBean<PageInfo<LeaveOutVo>> findPageInfo(Long ownerId, Integer pageSize, Integer pageNum);

    ResultBean<PageInfo<LeaveOutVo>> findPendingApprovePage(Long approverId, Set<String> processIds, Integer pageSize, Integer pageNum);

    void completeTask(String processId, String assignedUserId, Map<String, Object> variables);

    void updateApproveStatus(Long id, Integer approveStatus);

    void getProcessImage(String processId, HttpServletResponse response);

}
