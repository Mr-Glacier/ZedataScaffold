package com.zedata.project.service;

import com.zedata.project.entity.po.SysRoles;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr-Glacier
 * @since 2025-01-20
 */
public interface SysRolesService extends IService<SysRoles> {

    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    public List<SysRoles> getRoles();



}
