package com.yuanxiatech.xgj.funeral.drivetask.controller;

import com.yuanxiatech.xgj.core.pojo.DataSet;
import com.yuanxiatech.xgj.core.pojo.Result;
import com.yuanxiatech.xgj.core.utils.StringUtil;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralCar;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralCarQueryParam;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralDriveTaskQueryParam;
import com.yuanxiatech.xgj.funeral.drivetask.service.FuneralCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class FuneralCarController extends DriveTaskBaseController {

    public static final String MODULE_NAME = "/car";

    private FuneralCarService carService;

    @Autowired(required = false)
    public void setDriveTaskService(FuneralCarService carService) {
        this.carService = carService;
    }

    /**
     * 新增
     *
     * @return
     */
    @PostMapping(value = MODULE_NAME+"/add")
    public Result add(FuneralCar car) {
        car.setCreateTime(new Date());
        car.setCreateUserId(getLogonUserId());
        carService.save(car);
        return jsonWithRecord(car.getId());
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @PostMapping(value = MODULE_NAME+"/{ids}")
    public Result delete(String ids) {
        List<String> idList = StringUtil.convertList(ids);
        carService.deleteDatas(idList,getLogonUser());
        return jsonWithStandardStatus();
    }

    /**
     * 编辑
     *
     * @param car
     * @return
     */
    @PostMapping(value = MODULE_NAME+ "/old")
    public Result editMenu(FuneralCar car) {
        car.setLastUpdateTime(new Date());
        car.setLastUpdateUserId(getLogonUserId());
        carService.deleteCarStaff(car.getId());
        carService.update(car);
        return jsonWithStandardStatus();
    }

    /**
     * 列表
     * @param carQueryParam
     * @return
     */
    @GetMapping(value = MODULE_NAME+ "/list")
    public Result queryCar(FuneralCarQueryParam carQueryParam) {
        DataSet<FuneralCar> driveTaskDataSet = carService.queryCarData(carQueryParam);
        return jsonWithRecord(driveTaskDataSet);
    }

    /**
     * 获取车辆 及 排班顺序
     *
     * @param carQueryParam
     * @return
     */
    @GetMapping(value = MODULE_NAME+ "/scheduling")
    public Result carScheduling(FuneralCarQueryParam carQueryParam) {
        List<FuneralCar> carList = carService.getCarScheduling(carQueryParam);
        return jsonWithRecord(carList);
    }

}
