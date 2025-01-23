package com.zedata.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.injector.methods.Update;
import com.zedata.project.entity.po.SysUser;
import com.zedata.project.mapper.SysUserMapper;
import com.zedata.project.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Mr-Glacier
 * @since 2025-01-20
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper sysUserMapper;

    public SysUserServiceImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    /**
     * 根据账号获取用户信息
     *
     * @param account 账号
     * @return 用户信息
     */
    @Override
    public SysUser getUserByAccount(String account) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        // 根据用户roleList查询角色列表
        return sysUserMapper.selectOne(queryWrapper);
    }

    /**
     * 注册普通用户
     *
     * @param sysUser 用户信息实体
     * @return boolean
     */
    @Override
    public boolean registerUser(SysUser sysUser) {
        try {
            sysUserMapper.insert(sysUser);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("注册失败" + e.getMessage());
        }
    }

    /**
     * 删除用户
     *
     * @param account
     * @return
     */
    @Override
    public boolean deleteUser(String account) {
        try {
            UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("account", account);
            updateWrapper.set("status", 2);
            int rowsAffected = sysUserMapper.update(null, updateWrapper);
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("删除失败" + e.getMessage());
        }
    }
}
