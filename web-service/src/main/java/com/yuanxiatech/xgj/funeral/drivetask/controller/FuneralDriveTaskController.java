package com.yuanxiatech.xgj.funeral.drivetask.controller;

import com.yuanxiatech.xgj.core.exception.BusinessException;
import com.yuanxiatech.xgj.core.pojo.DataSet;
import com.yuanxiatech.xgj.core.pojo.Result;
import com.yuanxiatech.xgj.core.utils.StringUtil;
import com.yuanxiatech.xgj.funeral.drivetask.controller.pdf.BusinessPdf;
import com.yuanxiatech.xgj.funeral.drivetask.controller.pdf.DeadPdf;
import com.yuanxiatech.xgj.funeral.drivetask.controller.pdf.TaskPdf;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralDriveTask;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralDriveTaskQueryParam;
import com.yuanxiatech.xgj.funeral.drivetask.service.FuneralDriveTaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class FuneralDriveTaskController extends DriveTaskBaseController {

    public static final String MODULE_NAME = "/task";

    private FuneralDriveTaskService driveTaskService;

    @Autowired(required = false)
    public void setDriveTaskService(FuneralDriveTaskService driveTaskService) {
        this.driveTaskService = driveTaskService;
    }

    /**
     * 新增遗体接运
     *
     * @return
     */
    @PostMapping(value = MODULE_NAME+"/add")
    public Result add(FuneralDriveTask driveTask) {
        driveTask.setCreateTime(new Date());
        driveTask.setCreateUserId(getLogonUserId());
        driveTaskService.saveDriveTask(driveTask);
        return jsonWithRecord(driveTask.getId());
    }

    /**
     * 删除遗体接运
     *
     * @param ids
     * @return
     */
    @PostMapping(value = MODULE_NAME+"/delete")
    public Result delete(String ids) {
        List<String> idList = StringUtil.convertList(ids);
        driveTaskService.deleteTasks(idList,getLogonUser());
        return jsonWithStandardStatus();
    }

    /**
     * 回车 完成遗体接运
     *
     * @param ids
     * @return
     */
    @PostMapping(value = MODULE_NAME+"/finish")
    public Result taskFinish(String ids) {
        List<String> idList = StringUtil.convertList(ids);
        driveTaskService.taskFinishs(idList,getLogonUser());
        return jsonWithStandardStatus();
    }

    /**
     * 编辑
     *
     * @param driveTask
     * @return
     */
    @PostMapping(value = MODULE_NAME+ "/old")
    public Result editMenu(FuneralDriveTask driveTask) {
        driveTask.setLastUpdateTime(new Date());
        driveTask.setLastUpdateUserId(getLogonUserId());
        driveTaskService.updateDriveTask(driveTask);
        return jsonWithStandardStatus();
    }

    /**
     * 列表
     *
     * @param driveTaskQueryParam
     * @return
     */
    @GetMapping(value = MODULE_NAME+ "/list")
    public Result queryMenu(FuneralDriveTaskQueryParam driveTaskQueryParam) {
        DataSet<FuneralDriveTask> driveTaskDataSet = driveTaskService.queryDriveTaskData(driveTaskQueryParam);
        return jsonWithRecord(driveTaskDataSet);
    }

    /**
     * 任务查询统计
     * @param driveTaskQueryParam
     * @return
     */
    @GetMapping(value = MODULE_NAME+ "/statistics-query")
    public Result taskStatisticsQuery(FuneralDriveTaskQueryParam driveTaskQueryParam) {
        List<Map> mapList = driveTaskService.taskStatisticsQuery(driveTaskQueryParam);
        return jsonWithRecord(mapList);
    }

    /**
     * 接运任务状态查询
     * @param driveTaskQueryParam
     * @return
     */
    @GetMapping(value = MODULE_NAME+ "/status-query")
    public Result driveTaskStatusQuery(FuneralDriveTaskQueryParam driveTaskQueryParam) {
        if(StringUtils.isBlank(driveTaskQueryParam.getStartTime())||StringUtils.isBlank(driveTaskQueryParam.getEndTime())){
            throw new BusinessException("时间不能为空!");
        }
        Map map = driveTaskService.driveTaskStatusQuery(driveTaskQueryParam);
        return jsonWithRecord(map);
    }

    /**
     * 派车业务单 pdf
     * @param driveTaskQueryParam
     * @return
     */
    @GetMapping(value = MODULE_NAME+"/business-pdf")
    public ModelAndView businessPdf(FuneralDriveTaskQueryParam driveTaskQueryParam) {
        FuneralDriveTask driveTask = driveTaskService.getDriveTaskDetail(driveTaskQueryParam.getId());
        ModelAndView mv = new ModelAndView("南安市殡仪馆业务单");
        mv.setView(new BusinessPdf());
        mv.addObject("title", "中文");
        mv.addObject("driveTask", driveTask);
        return mv;
    }
    /**
     * 死亡人员情况登记表 pdf
     * @param driveTaskQueryParam
     * @return
     */
    @GetMapping(value = MODULE_NAME+"/dead-pdf")
    public ModelAndView deadPdf(FuneralDriveTaskQueryParam driveTaskQueryParam) {
        FuneralDriveTask driveTask = driveTaskService.getDriveTaskDetail(driveTaskQueryParam.getId());
        ModelAndView mv = new ModelAndView("乡镇（街道）死亡人员情况登记表");
        mv.setView(new DeadPdf());
        mv.addObject("title", "中文");
        mv.addObject("driveTask", driveTask);
        return mv;
    }
    /**
     * 接尸登记表 pdf
     * @param driveTaskQueryParam
     * @return
     */
    @GetMapping(value = MODULE_NAME+"/task-pdf")
    public ModelAndView taskPdf(FuneralDriveTaskQueryParam driveTaskQueryParam) {
        List<FuneralDriveTask> driveTaskList = driveTaskService.queryDriveTaskData(driveTaskQueryParam).getData();
        ModelAndView mv = new ModelAndView("南安市殡仪馆接尸登记表");
        mv.setView(new TaskPdf());
        mv.addObject("title", "中文");
        mv.addObject("driveTasks", driveTaskList);
        return mv;
    }


}
