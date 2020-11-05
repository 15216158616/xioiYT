package com.yuanxiatech.xgj.funeral.drivetask.controller;

import com.yuanxiatech.xgj.core.pojo.DataSet;
import com.yuanxiatech.xgj.core.pojo.Result;
import com.yuanxiatech.xgj.core.utils.StringUtil;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralStaff;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralStaffQueryParam;
import com.yuanxiatech.xgj.funeral.drivetask.service.FuneralStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class FuneralStaffController extends DriveTaskBaseController {

    public static final String MODULE_NAME = "/staff";

    private FuneralStaffService staffService;

    @Autowired(required = false)
    public void setStaffService(FuneralStaffService staffService) {
        this.staffService = staffService;
    }

    /**
     * 新增
     *
     * @return
     */
    @PostMapping(value = MODULE_NAME+"/add")
    public Result add(FuneralStaff staff) {
        staff.setCreateTime(new Date());
        staff.setCreateUserId(getLogonUserId());
        staffService.save(staff);
        return jsonWithRecord(staff.getId());
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @PostMapping(value = MODULE_NAME+"/delete")
    public Result delete(String ids) {
        List<String> idList = StringUtil.convertList(ids);
        staffService.deleteDatas(idList,getLogonUser());
        return jsonWithStandardStatus();
    }

    /**
     * 编辑
     *
     * @param staff
     * @return
     */
    @PostMapping(value = MODULE_NAME+ "/old")
    public Result editMenu(FuneralStaff staff) {
        staff.setLastUpdateTime(new Date());
        staff.setLastUpdateUserId(getLogonUserId());
        staffService.update(staff);
        return jsonWithStandardStatus();
    }

    /**
     * 列表
     * @param staffQueryParam
     * @return
     */
    @GetMapping(value = MODULE_NAME+ "/list")
    public Result query(FuneralStaffQueryParam staffQueryParam) {
        DataSet<FuneralStaff> driveTaskDataSet = staffService.queryByParam(staffQueryParam);
        return jsonWithRecord(driveTaskDataSet);
    }
}
