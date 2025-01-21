package com.zedata.project.controller.admin;

import com.zedata.project.common.result.Result;
import com.zedata.project.entity.po.SysUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 管理员用户控制类
 * @since 2025/1/21 19:20
 */
@RestController
@RequestMapping("/api/admin/sysUser")
public class AdminSysUserController {



    /**
     * 增加系统用户,可以注册的时候就进行权限区分
     *
     * @since 2025/1/21 19:20
     */
    @PostMapping("/add")
    public Result<Object> addSysUserController(@RequestParam("account") String account, @RequestParam("userName") String userName,
                                               @RequestParam("userPassword") String userPassword,@RequestParam("roleList") String roleList) {
        try{


        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.success();
    }

}
