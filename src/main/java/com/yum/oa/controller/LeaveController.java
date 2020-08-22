package com.yum.oa.controller;

import com.github.pagehelper.PageInfo;
import com.yum.oa.common.result.ResultBean;
import com.yum.oa.common.result.ResultGenerator;
import com.yum.oa.common.security.JwtUser;
import com.yum.oa.common.utils.ObjectUtil;
import com.yum.oa.common.utils.UserUtil;
import com.yum.oa.model.entity.LeaveEntity;
import com.yum.oa.model.vo.LeaveInVo;
import com.yum.oa.model.vo.LeaveOutVo;
import com.yum.oa.service.LeaveService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gaoyu
 * @version v
 * @description
 * @date 2020/8/20
 **/
@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    @Resource
    private UserUtil userUtil;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private LeaveService leaveService;

    @PostMapping("apply")
    public ResultBean<String> apply(@RequestBody LeaveInVo param) {
        JwtUser currentUser = userUtil.getCurrentUser();
        param.setOwnerId(currentUser.getUserId());
        Map<String, Object> vars = new HashMap<>(4);
        vars.put("applyUserId", currentUser.getUserId());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave" , vars);
        leaveService.completeTask(processInstance.getProcessInstanceId(), String.valueOf(currentUser.getUserId()), ObjectUtil.objectTurn2Map(param));
        LeaveEntity leaveEntity = new LeaveEntity();
        BeanUtils.copyProperties(param, leaveEntity);
        leaveEntity.setProcessId(processInstance.getProcessInstanceId());
        return leaveService.saveOne(leaveEntity);
    }

    @GetMapping("applyList")
    public ResultBean<PageInfo<LeaveOutVo>> applyList(@RequestParam Integer pageSize, @RequestParam Integer pageNum) {
        JwtUser currentUser = userUtil.getCurrentUser();
        return leaveService.findPageInfo(currentUser.getUserId(), pageSize, pageNum);
    }

    @DeleteMapping("endApply/{processInstanceId}")
    public ResultBean<String> endApply(@PathVariable("processInstanceId") String processInstanceId){
        // 结束流程
        runtimeService.deleteProcessInstance(processInstanceId, "");
        return ResultGenerator.success("取消申请成功");
    }

    @GetMapping("pendingApproveList")
    public ResultBean<PageInfo<LeaveOutVo>> findPendingApproveList(@RequestParam Integer pageSize, @RequestParam Integer pageNum) {
        JwtUser currentUser = userUtil.getCurrentUser();
        List<Task> taskList = taskService.createTaskQuery()
                .taskCandidateOrAssigned(String.valueOf(currentUser.getUserId()))
                .active().list();
        Set<String> processIds = taskList.stream().map(Task::getProcessInstanceId).collect(Collectors.toSet());
        return leaveService.findPendingApprovePage(currentUser.getUserId(), processIds, pageSize, pageNum);
    }

    @PostMapping("pass/{id}/{processId}")
    public ResultBean<String> pass(@PathVariable("id") Long id, @PathVariable("processId") String processId) {
        JwtUser currentUser = userUtil.getCurrentUser();
        Map<String, Object> vars = new HashMap<>();
        vars.put("auditPass", true);
        leaveService.completeTask(processId, String.valueOf(currentUser.getUserId()), vars);
        leaveService.updateApproveStatus(id, 1);
        return ResultGenerator.success("操作成功");
    }

    @GetMapping("getProcessImage/{processId}")
    public void getProcessImage(@PathVariable("processId") String processId, HttpServletResponse response) {
        leaveService.getProcessImage(processId, response);
    }

}
