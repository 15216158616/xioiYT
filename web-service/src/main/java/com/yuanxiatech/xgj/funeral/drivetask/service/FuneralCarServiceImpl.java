package com.yuanxiatech.xgj.funeral.drivetask.service;

import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.core.dao.AppBaseDao;
import com.yuanxiatech.xgj.core.pojo.DataSet;
import com.yuanxiatech.xgj.core.service.StringPojoAppBaseServiceImpl;
import com.yuanxiatech.xgj.funeral.drivetask.dao.FuneralCarDao;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralCar;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralCarQueryParam;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: TODO
 * @date 2020/6/9 19:37
 **/
@Service
public class FuneralCarServiceImpl extends StringPojoAppBaseServiceImpl<FuneralCar> implements FuneralCarService {

    private FuneralCarDao carDao;

    @Autowired(required = false)
    public void setCarDao(FuneralCarDao carDao){
        this.carDao = carDao;
    }

    @Override
    protected AppBaseDao<FuneralCar, String> getDao() { return carDao; }

    @Override
    public DataSet<FuneralCar> queryCarData(FuneralCarQueryParam carQueryParam) {
        DataSet<FuneralCar> carDataSet = super.queryByParam(carQueryParam);
        appendStaff(carDataSet.getData());
        return carDataSet;
    }

    @Override
    public void deleteDatas(List<String> idList, User user) {
        List<FuneralCar> carList = new ArrayList<>();
        for (String id : idList) {
            FuneralCar car = new FuneralCar();
            car.setId(id);
            car.setDeleteTime(new Date());
            car.setDeleteUserId(user.getId());
            car.setDataStatus(0);
            carList.add(car);
        }
        super.logicDelete(carList);
    }

    @Override
    public void deleteCarStaff(String carId) {
        carDao.deleteCarStaff(carId);
    }

    @Override
    public List<FuneralCar> getCarScheduling(FuneralCarQueryParam carQueryParam) {
        List<FuneralCar> carList = carDao.getCarScheduling(carQueryParam);
        appendStaff(carList);
        return carList;
    }

    @Override
    public void releaseCarTask(String driveTaskId) {
        carDao.releaseCarTask(driveTaskId);
    }

    @Override
    public void updateDriveTask(FuneralCar car) {
        carDao.updateDriveTask(car);
    }

    private void appendStaff(List<FuneralCar> carList) {
        if(carList.isEmpty())return;
        List<String> ids = carList.stream().map(FuneralCar::getId).collect(Collectors.toList());
        if(ids.isEmpty())return;
        List<FuneralStaff> staffList = carDao.getByCarIds(ids);
        if(staffList.isEmpty())return;
        carList.forEach(car -> {
            List<FuneralStaff> staffs = new ArrayList<>();
            staffList.forEach(staff -> {
                if(car.getId().equals(staff.getTargetId())){
                    staffs.add(staff);
                }
            });
            car.setStaffList(staffs);
        });
    }
}
