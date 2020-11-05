package com.yuanxiatech.xgj.funeral.system.controller;

import com.yuanxiatech.xgj.funeral.base.controller.ManageBaseController;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(ManageBaseController.BASE_SYSTEM_NAME + SystemBaseController.SYSTEM_MODULE_NAME)
public class SystemBaseController extends ManageBaseController {

    public static final String SYSTEM_MODULE_NAME = "/system";
}
