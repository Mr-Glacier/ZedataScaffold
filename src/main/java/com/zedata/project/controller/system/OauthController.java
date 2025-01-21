package com.zedata.project.controller.system;

import com.zedata.project.common.result.Result;
import com.zedata.project.config.security.JwtTokenUtil;
import com.zedata.project.entity.po.SysUser;
import com.zedata.project.service.SysUserService;
import com.zedata.project.service.basicServices.AuthUserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


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
    private final JwtTokenUtil jwtTokenUtil;

    private final AuthUserService authUserService;

    public OauthController(SysUserService sysUserService, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil, AuthUserService authUserService) {
        this.sysUserService = sysUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authUserService = authUserService;
    }

    /**
     * @apiNote 登录接口, 接收用户名密码, 返回token
     */
    @PostMapping("/login")
    public Result login(@RequestParam(name = "account") String account,
                        @RequestParam(name = "userPassword") String password) {
        try {
            SysUser currentUser = sysUserService.getUserByAccount(account);
            if (currentUser == null) {
                return Result.failed("用户不存在");
            }
            // 调用方法加载用户
            UserDetails userDetails = authUserService.loadUserByUsername(account);
            // 密码校验
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                return Result.failed("密码错误");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenUtil.generateToken(userDetails);

            return Result.success(token);
        } catch (org.springframework.security.core.AuthenticationException e) {
            // 认证失败处理
            return Result.failed("账户或密码错误");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed("系统错误，请稍后再试");
        }
    }


    /**
     * 注册用户,仅允许注册普通用户账号
     *
     * @param account 用户账号 userName 用户名 userPassword 用户密码 registrationKey 注册码
     */
    @PostMapping("/register")
    @ResponseBody
    public Result<Object> register(@RequestParam("account") String account, @RequestParam("userName") String userName,
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
