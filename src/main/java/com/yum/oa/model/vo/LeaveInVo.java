package com.yum.oa.model.vo;

import java.util.Date;

/**
 * @author gaoyu
 * @version v
 * @description
 * @date 2020/8/20
 **/
public class LeaveInVo {
    private Long id;
    /** 请假编码 */
    private Integer leaveCode;
    /** 请假类型0:事假1:病假 */
    private Integer leaveType;
    /** 原因 */
    private String reason;
    /** 开始时间 */
    private Date startTime;
    /** 结束时间 */
    private Date endTime;
    /** 申请时长 */
    private Integer totalHours;
    /** 拥有人Id */
    private Long ownerId;
    /** 流程Id */
    private String processId;
    /** 审批状态0:待审批1:同意2驳回 */
    private Integer approveStatus;
    /** 驳回原因 */
    private String rejectReason;
    /** 审批人 */
    private Long approverId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLeaveCode() {
        return leaveCode;
    }

    public void setLeaveCode(Integer leaveCode) {
        this.leaveCode = leaveCode;
    }

    public Integer getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(Integer leaveType) {
        this.leaveType = leaveType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Integer totalHours) {
        this.totalHours = totalHours;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }
}
