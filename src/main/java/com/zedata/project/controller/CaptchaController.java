package com.zedata.project.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.UUID;
import com.zedata.project.common.result.Result;
import com.zedata.project.service.basicServices.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 验证码控制类
 * @since 2025/1/20 1:33
 */
@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {

    private final RedisService redisService;

    @Autowired
    public CaptchaController(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * @apiNote 获取验证码
     */
    @GetMapping("/getCaptcha")
    public Result getCaptcha(HttpServletResponse response) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // 禁止缓存
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0");

            // 生成图片验证码
            LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 60, 4, 6);

            // 转换成为 base64
            ImageIO.write(lineCaptcha.getImage(), "png", baos);
            String base64Img = Base64.getEncoder().encodeToString(baos.toByteArray());

            // 生成唯一ID
            String captchaId = UUID.randomUUID().toString();

            // 将图片验证码的文本保存到 Redis 中，并设置过期时间为5分钟（300,000毫秒）
            redisService.set(captchaId, lineCaptcha.getCode(), 300000);

            Map<String, String> map = new HashMap<>(2);
            map.put("captchaId", captchaId);
            map.put("image", base64Img);

            return Result.success(map);
        } catch (Exception e) {
            return Result.failed("验证码获取失败"+e.getMessage());
        }
    }


}
