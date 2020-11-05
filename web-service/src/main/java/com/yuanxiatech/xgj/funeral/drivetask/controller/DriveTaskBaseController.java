package com.yuanxiatech.xgj.funeral.drivetask.controller;

import com.yuanxiatech.xgj.funeral.base.controller.ManageBaseController;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(ManageBaseController.BASE_SYSTEM_NAME + DriveTaskBaseController.SYSTEM_MODULE_NAME)
public class DriveTaskBaseController extends ManageBaseController {

    public static final String SYSTEM_MODULE_NAME = "/drive-task";
}
