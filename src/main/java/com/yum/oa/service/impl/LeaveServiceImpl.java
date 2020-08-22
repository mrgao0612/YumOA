package com.yum.oa.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yum.oa.common.base.BaseService;
import com.yum.oa.common.result.ResultBean;
import com.yum.oa.common.result.ResultGenerator;
import com.yum.oa.mapper.LeaveEntityMapper;
import com.yum.oa.model.entity.LeaveEntity;
import com.yum.oa.model.vo.LeaveOutVo;
import com.yum.oa.service.LeaveService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.avalon.framework.service.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author gaoyu
 * @version v
 * @description
 * @date 2020/8/21
 **/
@Service
public class LeaveServiceImpl extends BaseService<LeaveEntityMapper, LeaveEntity> implements LeaveService {

    @Resource
    private LeaveEntityMapper leaveEntityMapper;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private ProcessEngine processEngine;

    protected LeaveServiceImpl(LeaveEntityMapper leaveEntityMapper) {
        super(leaveEntityMapper);
    }

    @Override
    public ResultBean<String> saveOne(LeaveEntity entity) {
        if (super.save(entity) > 0) {
            return ResultGenerator.success("提交成功");
        }
        return ResultGenerator.failed("提交失败");
    }

    @Override
    public ResultBean<PageInfo<LeaveOutVo>> findPageInfo(Long ownerId, Integer pageSize, Integer pageNum) {
        LeaveEntity param = new LeaveEntity();
        param.setOwnerId(ownerId);
        param.setPageNum(pageNum);
        param.setPageSize(pageSize);
        PageInfo<LeaveEntity> leaves = super.findPageList(param);
        List<LeaveOutVo> leaveOutList = leaves.getList().stream().map(l -> {
            LeaveOutVo leaveOutVo = new LeaveOutVo();
            BeanUtils.copyProperties(l, leaveOutVo);
            return leaveOutVo;
        }).collect(Collectors.toList());

        return ResultGenerator.success(PageInfo.of(leaveOutList));
    }

    @Override
    public ResultBean<PageInfo<LeaveOutVo>> findPendingApprovePage(Long approverId, Set<String> processIds, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<LeaveEntity> leaveList = leaveEntityMapper.findPendingApproveList(approverId, processIds);
        List<LeaveOutVo> resultList = leaveList.stream().map(l -> {
            LeaveOutVo result = new LeaveOutVo();
            BeanUtils.copyProperties(l, result);
            return result;
        }).collect(Collectors.toList());
        return ResultGenerator.success(PageInfo.of(resultList));
    }

    @Override
    public void completeTask(String processId, String assignedUserId, Map<String, Object> variables) {
        Task task = taskService.createTaskQuery()
                .processInstanceId(processId)
                .taskCandidateOrAssigned(assignedUserId)
                .active()
                .singleResult();
        taskService.complete(task.getId(), variables);
    }

    @Override
    public void updateApproveStatus(Long id, Integer approveStatus) {
        leaveEntityMapper.updateApproveStatus(id, approveStatus);
    }

    @Override
    public void getProcessImage(String processId, HttpServletResponse response) {
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        try (OutputStream os = response.getOutputStream()){
            //  获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(processId).singleResult();

            if (historicProcessInstance == null) {
                throw new ServiceException(processId, "获取流程实例ID[" + processId + "]对应的历史流程实例失败！");
            } else {
                // 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
                List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(processId).orderByHistoricActivityInstanceId().asc().list();

                // 已执行的节点ID集合
                List<String> executedActivityIdList = new ArrayList<>();
                for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
                    executedActivityIdList.add(activityInstance.getActivityId());
                }
                // 获取流程定义
                BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());

                // 已执行的线集合
                List<String> flowIds = getHighLightedFlows(bpmnModel, historicActivityInstanceList);

                // 流程图生成器
                ProcessDiagramGenerator pec = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
                // 获取流程图图像字符流（png/jpg）
                try (InputStream imageStream = pec.generateDiagram(bpmnModel, "jpg",
                        executedActivityIdList, flowIds, "宋体",
                        "微软雅黑", "黑体", null, 2.0)) {
                    int bytesRead;
                    byte[] buffer = new byte[8192];
                    while ((bytesRead = imageStream.read(buffer, 0, 8192)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                }
            }
        } catch (Exception e) {
            throw new ActivitiException("获取流程图失败", e);
        }

    }

    private List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
        // 24小时制
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 用以保存高亮的线flowId
        List<String> highFlows = new ArrayList<>();

        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {
            // 对历史流程节点进行遍历
            // 得到节点定义的详细信息
            FlowNode activityImpl = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstances.get(i).getActivityId());

            // 用以保存后续开始时间相同的节点
            List<FlowNode> sameStartTimeNodes = new ArrayList<>();
            FlowNode sameActivityImpl1 = null;
            // 第一个节点
            HistoricActivityInstance activityImpl_ = historicActivityInstances.get(i);
            HistoricActivityInstance activityImp2_;

            for (int k = i + 1; k <= historicActivityInstances.size() - 1; k++) {
                // 后续第1个节点
                activityImp2_ = historicActivityInstances.get(k);

                if (activityImpl_.getActivityType().equals("userTask") && activityImp2_.getActivityType().equals("userTask") &&
                        df.format(activityImpl_.getStartTime()).equals(df.format(activityImp2_.getStartTime()))) {
                    // 都是usertask，且主节点与后续节点的开始时间相同，说明不是真实的后继节点
                } else {
                    //找到紧跟在后面的一个节点
                    sameActivityImpl1 = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstances.get(k).getActivityId());
                    break;
                }

            }
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                // 后续第一个节点
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);
                // 后续第二个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);

                if (df.format(activityImpl1.getStartTime()).equals(df.format(activityImpl2.getStartTime()))) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    FlowNode sameActivityImpl2 = (FlowNode) bpmnModel.getMainProcess().getFlowElement(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {// 有不相同跳出循环
                    break;
                }
            }
            // 取出节点的所有出去的线
            List<SequenceFlow> pvmTransitions = activityImpl.getOutgoingFlows();
            // 对所有的线进行遍历
            for (SequenceFlow pvmTransition : pvmTransitions) {
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                FlowNode pvmActivityImpl = (FlowNode) bpmnModel.getMainProcess().getFlowElement(pvmTransition.getTargetRef());
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }

        }
        return highFlows;

    }
}
