package com.yuanxiatech.xgj.funeral.system.dao;

import com.yuanxiatech.xgj.core.dao.StringPojoAppBaseDaoImpl;
import com.yuanxiatech.xgj.funeral.system.model.Role;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: TODO
 * @date 2020/6/9 19:39
 **/
@Repository
public class RoleDaoImpl extends StringPojoAppBaseDaoImpl<Role> implements RoleDao{

    @Override
    protected Class<Role> getPojoClass() {
        return Role.class;
    }

    /**
     * 机构的角色
     * @param partnerId
     * @return
     */
    @Override
    public List<Role> getByPartnerId(String partnerId) {
        return selectList(getPrefix() + "getByPartnerId",partnerId);
    }

    /**
     * 角色id和机构查询
     * @param roleIds
     * @param partnerId
     * @return
     */
    @Override
    public List<Role> getByRoleIdsAndPartnerId(List<String> roleIds, String partnerId) {

        Map<String, Object> map = new HashMap<>();
        map.put("roleIds",roleIds);
        map.put("partnerId",partnerId);
        return selectList(getPrefix() + "getByRoleIdsAndPartnerId",map);
    }
}
