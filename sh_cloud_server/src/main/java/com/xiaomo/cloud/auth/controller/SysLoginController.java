package com.xiaomo.cloud.auth.controller;

import cn.hutool.core.lang.Dict;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.xiaomo.common.auth.api.IAuthService;
import com.xiaomo.common.context.constant.ConstantContextHolder;
import com.xiaomo.common.exception.AuthException;
import com.xiaomo.common.exception.enums.AuthExceptionEnum;
import com.xiaomo.common.pojo.response.ResponseData;
import com.xiaomo.common.pojo.response.SuccessResponseData;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录器控制
 */
@RestController
@RequestMapping(value = "/auth")
public class SysLoginController {

    @Resource
    private IAuthService authService;

    @Lazy
    @Resource
    private CaptchaService captchaService;

    @RequestMapping(value = "/login")
    public ResponseData login(@RequestBody Dict dict){
        String account = dict.getStr("username");
        String password = dict.getStr("password");
        String tenantCode = dict.getStr("tenantCode");
        //检测是否开启验证码
        if (ConstantContextHolder.getCaptchaOpenFlag()) {
            verificationCode(dict.getStr("code"));
        }
        //如果系统开启了多租户开关，则添加租户的临时缓存
        if (ConstantContextHolder.getTenantOpenFlag()) {
            authService.cacheTenantInfo(tenantCode);
        }
        String token = authService.login(account, password);
        return new SuccessResponseData(token);
    }

    /*-----------------------------工具方法--------------------------------*/
    /**
     * 校验验证码
     *
     * @author Jax
     * @date 2021/1/21 15:27
     */
    private boolean verificationCode(String code) {
        CaptchaVO vo = new CaptchaVO();
        vo.setCaptchaVerification(code);
        if (!captchaService.verification(vo).isSuccess()) {
            throw new AuthException(AuthExceptionEnum.CONSTANT_EMPTY_ERROR);
        }
        return true;
    }
}
