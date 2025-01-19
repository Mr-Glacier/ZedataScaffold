package com.zedata.project.service;

import com.zedata.project.entity.po.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Mr-Glacier
 * @since 2025-01-20
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据账号查询用户信息
     *
     * @param account 账号
     * @return 用户信息
     */
    SysUser getUserByAccount(String account);

    /**
     * 注册新用户(仅允许注册普通用户)
     *
     * @param sysUser 用户信息实体
     */
    boolean registerUser(SysUser sysUser);

}
