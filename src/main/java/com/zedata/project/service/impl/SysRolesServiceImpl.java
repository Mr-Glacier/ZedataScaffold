package com.zedata.project.service.impl;

import com.zedata.project.entity.po.SysRoles;
import com.zedata.project.mapper.SysRolesMapper;
import com.zedata.project.service.SysRolesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Mr-Glacier
 * @since 2025-01-20
 */
@Service
public class SysRolesServiceImpl extends ServiceImpl<SysRolesMapper, SysRoles> implements SysRolesService {

    public final SysRolesMapper sysRolesMapper;

    public SysRolesServiceImpl(SysRolesMapper sysRolesMapper) {
        this.sysRolesMapper = sysRolesMapper;
    }

    @Override
    public List<SysRoles> getRoles() {
        return sysRolesMapper.selectList(null);
    }
}
