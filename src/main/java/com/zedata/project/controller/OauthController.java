package com.zedata.project.controller;

import com.zedata.project.common.result.Result;
import com.zedata.project.entity.po.SysUser;
import com.zedata.project.service.SysUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 认证授权控制类
 * @since 2025/1/20 1:29
 */
@RestController
@RequestMapping("/oauth")
public class OauthController {

    private final SysUserService sysUserService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public OauthController(SysUserService sysUserService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder1, AuthenticationManager authenticationManager1) {
        this.sysUserService = sysUserService;
        this.passwordEncoder = passwordEncoder1;
        this.authenticationManager = authenticationManager1;
    }

    /**
     * @apiNote 登录接口, 接收用户名密码, 返回token
     */
    @PostMapping("/login")
    public Result login(@RequestParam(name = "account") String account,
                        @RequestParam(name = "userPassword") String password) {
        try {
            if (account == null || password == null) {
                return Result.failed("账户密码不可为空");
            }

            // 创建认证令牌
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account, password);

            // 执行认证
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 设置认证到安全上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return Result.success(authentication);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed("登录失败");
        }
    }


    /**
     * 注册用户,仅允许注册普通用户账号
     *
     * @param account 用户账号 userName 用户名 userPassword 用户密码 registrationKey 注册码
     */
    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestParam("account") String account, @RequestParam("userName") String userName,
                           @RequestParam("userPassword") String userPassword) {
        try {
            // 对三个参数进行校验
            if (account == null || userName == null || userPassword == null) {
                return Result.failed("注册相关信息错误");
            }
            // 判断用户是否存在
            SysUser sysUser = sysUserService.getUserByAccount(account);
            if (sysUser != null) {
                return Result.failed("该账户已存在");
            }
            // 用户不存在进行注册
            SysUser registerUser = new SysUser();
            registerUser.setAccount(account);
            registerUser.setUserName(userName);
            registerUser.setPassWord(passwordEncoder.encode(userPassword));
            registerUser.setRoleList("2");
            if (sysUserService.registerUser(registerUser)) {
                return Result.success("注册成功");
            } else {
                return Result.failed("注册失败");
            }
        } catch (Exception e) {
            return Result.failed("注册失败" + e.getMessage());
        }
    }

}
