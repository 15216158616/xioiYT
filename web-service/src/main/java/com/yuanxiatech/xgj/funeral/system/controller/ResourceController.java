package com.yuanxiatech.xgj.funeral.system.controller;

import com.yuanxiatech.xgj.core.pojo.DataSet;
import com.yuanxiatech.xgj.core.pojo.Result;
import com.yuanxiatech.xgj.core.utils.StringUtil;
import com.yuanxiatech.xgj.funeral.system.model.Resource;
import com.yuanxiatech.xgj.funeral.system.model.ResourceQueryParam;
import com.yuanxiatech.xgj.funeral.system.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ResourceController extends SystemBaseController {

    private ResourceService resourceService;

    @Autowired(required = false)
    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * 新增资源
     *
     * @param resource
     * @return
     */
    @PostMapping(value = "/newResource")
    public Result addResource(Resource resource) {
        resource.setCreateTime(new Date());
        resource.setCreateUserId(getLogonUserId());
        resourceService.save(resource);
        return jsonWithStandardStatus();
    }

    /**
     * 删除资源
     *
     * @param ids
     * @return
     */
    @PostMapping(value = "/cancelResource")
    public Result deleteResource(String ids) {
        List<String> idList = StringUtil.convertList(ids);
        List<Resource> resourceList = new ArrayList<>();
        idList.stream().forEach(id -> {
            Resource resource = new Resource();
            resource.setId(id);
            resource.setDataStatus(Resource.DATA_STATUS_DELETE);
            resource.setDeleteTime(new Date());
            resource.setDeleteUserId(getLogonUserId());
            resourceList.add(resource);
        });
        resourceService.logicDelete(resourceList);
        return jsonWithStandardStatus();
    }

    /**
     * 编辑资源
     *
     * @param resource
     * @return
     */
    @PostMapping(value = "/oldResource")
    public Result editResource(Resource resource) {
        resource.setLastUpdateTime(new Date());
        resource.setLastUpdateUserId(getLogonUserId());
        resourceService.update(resource);
        return jsonWithStandardStatus();
    }

    /**
     * 查询资源列表
     *
     * @param resourceQueryParam
     * @return
     */
    @GetMapping(value = "/resourceList")
    public Result queryResource(ResourceQueryParam resourceQueryParam) {
        DataSet<Resource> resourceDataSet = resourceService.queryByParam(resourceQueryParam);
        return jsonWithRecord(resourceDataSet);
    }

    /**
     * 获取资源集合
     *
     * @return
     */
    @GetMapping(value = "/getResourceList")
    public Result getResources() {
        List<Resource> resourceList = resourceService.getResources();
        return jsonWithRecord(resourceList);
    }
}
