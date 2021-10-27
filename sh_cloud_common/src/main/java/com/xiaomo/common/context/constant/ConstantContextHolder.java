package com.xiaomo.common.context.constant;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import com.xiaomo.common.consts.SymbolConstant;

import java.util.List;


/**
 * 系统参数配置获取
 *
 * @author xiaomo
 * @date 2021/4/14 15:34
 */
public class ConstantContextHolder {

    private static final Log log = Log.get();

    /**
     * 获取租户功能是否开启
     *
     * @author xiaomo
     * @date 2021/9/3
     */
    public static Boolean getTenantOpenFlag() {
        return getSysConfigWithDefault("SNOWY_TENANT_OPEN", Boolean.class, false);
    }

    /**
     * 获取放开xss过滤的接口
     *
     * @author yubaoshan
     * @date 2021/6/20 22:13
     */
    public static List<String> getUnXssFilterUrl() {
        String snowyUnXssFilterUrl = getSysConfigWithDefault("SNOWY_UN_XSS_FILTER_URL", String.class, null);
        if (ObjectUtil.isEmpty(snowyUnXssFilterUrl)) {
            return CollectionUtil.newArrayList();
        } else {
            return CollectionUtil.toList(snowyUnXssFilterUrl.split(SymbolConstant.COMMA));
        }
    }

    /**
     * 获取演示环境开关是否开启，默认为false
     *
     * @author yubaoshan
     * @date 2021/6/20 22:13
     */
    public static Boolean getDemoEnvFlag() {
        return getSysConfigWithDefault("SNOWY_DEMO_ENV_FLAG", Boolean.class, false);
    }

    /**
     * 邮件的配置
     *
     * @author yubaoshan
     * @date 2021/6/19 18:08
     */
    /*public static EmailConfigs getEmailConfigs() {
        String host = getSysConfig("SNOWY_EMAIL_HOST", String.class, true);
        String username = getSysConfig("SNOWY_EMAIL_USERNAME", String.class, true);
        String password = getSysConfig("SNOWY_EMAIL_PASSWORD", String.class, true);
        Integer port = getSysConfig("SNOWY_EMAIL_PORT", Integer.class, true);
        String from = getSysConfig("SNOWY_EMAIL_FROM", String.class, true);
        Boolean ssl = getSysConfig("SNOWY_EMAIL_SSL", Boolean.class, true);

        EmailConfigs emailConfigs = new EmailConfigs();
        emailConfigs.setHost(host);
        emailConfigs.setUser(username);
        emailConfigs.setPass(password);
        emailConfigs.setPort(port);
        emailConfigs.setFrom(from);
        emailConfigs.setSslEnable(ssl);
        return emailConfigs;
    }*/

    /**
     * 获取jwt密钥
     * @author yubaoshan
     * @date 2021/6/19 18:08
     */
    /*public static String getJwtSecret() {
        return getSysConfigWithDefault("SNOWY_JWT_SECRET", String.class, CommonConstant.DEFAULT_JWT_PASSWORD);
    }*/

    /**
     * 获取默认密码
     *
     * @author yubaoshan
     * @date 2021/6/19 18:08
     */
    /*public static String getDefaultPassWord() {
        return getSysConfigWithDefault("SNOWY_DEFAULT_PASSWORD", String.class, CommonConstant.DEFAULT_PASSWORD);
    }*/

    /**
     * 获取会话过期时间，默认2小时
     *
     * @author yubaoshan
     * @date 2021/7/9 16:18
     */
    public static Long getSessionTokenExpireSec() {
        return getSysConfigWithDefault("SNOWY_SESSION_EXPIRE", Long.class, 2 * 60 * 60L);
    }

    /**
     * 获取token过期时间（单位：秒）
     * <p>
     * 默认时间1天
     *
     * @author xiaomo
     * @date 2021/6/19 18:08
     */
    public static Long getTokenExpireSec() {
        return getSysConfigWithDefault("SNOWY_TOKEN_EXPIRE", Long.class, 86400L);
    }

    /**
     * 获取自定义的windows环境本地文件上传路径
     *
     * @author xiaomo
     * @date 2021/6/19 18:09
     */
    public static String getDefaultFileUploadPathForWindows() {
        return getSysConfigWithDefault("SNOWY_FILE_UPLOAD_PATH_FOR_WINDOWS", String.class, "");
    }

    /**
     * 获取自定义的linux环境本地文件上传路径
     *
     * @author xiaomo
     * @date 2021/6/19 18:09
     */
    public static String getDefaultFileUploadPathForLinux() {
        return getSysConfigWithDefault("SNOWY_FILE_UPLOAD_PATH_FOR_LINUX", String.class, "");
    }

    /**
     * 获取是否允许单用户登陆的开关， 默认为false
     *
     * @author xiaomo
     * @date 2021/6/19 18:09
     */
    public static Boolean getEnableSingleLogin() {
        return getSysConfigWithDefault("SNOWY_ENABLE_SINGLE_LOGIN", Boolean.class, false);
    }


    /**
     * 获取是否允许Oauth用户登陆的开关， 默认为false
     *
     * @author xiaomo
     * @date 2021/7/28 16:37
     **/
    public static Boolean getEnableOauthLogin() {
        return getSysConfigWithDefault("SNOWY_ENABLE_OAUTH_LOGIN", Boolean.class, false);
    }

    /**
     * 获取前端项目的地址
     *
     * @author xiaomo
     * @date 2021/7/29 14:08
     **/
    /*public static String getWebUrl() {
        return getSysConfig("SNOWY_WEB_URL", String.class, true);
    }*/

    /**
     * 获取支付宝支付成功转发地址
     *
     * @author xiaomo
     * @date 2021/7/29 14:08
     **/
    /*public static String getAlipayReturnUrl() {
        return getSysConfig("SNOWY_ALIPAY_RETURN_URL", String.class, true);
    }*/

    /**
     * 获取OnlyOffice地址
     *
     * @author xiaomo
     * @date 2021/7/29 14:08
     **/
    /*public static String getOnlyOfficeUrl() {
        return getSysConfig("SNOWY_ONLY_OFFICE_SERVICE_URL", String.class, true);
    }*/

    /**
     * 获取config表中的配置，如果为空返回默认值
     *
     * @param configCode   变量名称，对应sys_config表中的code
     * @param clazz        返回变量值的类型
     * @param defaultValue 如果结果为空返回此默认值
     * @author yubaoshan
     * @date 2021/6/20 22:03
     */
    public static <T> T getSysConfigWithDefault(String configCode, Class<T> clazz, T defaultValue) {
        String configValue = ConstantContext.me().getStr(configCode);
        if (ObjectUtil.isEmpty(configValue)) {
            // 将默认值加入到缓存常量
            log.warn(">>> 系统配置sys_config表中存在空项，configCode为：{}，系统采用默认值：{}", configCode, defaultValue);
            ConstantContext.me().put(configCode, defaultValue);
            return defaultValue;
        } else {
            try {
                return Convert.convert(clazz, configValue);
            } catch (Exception e) {
                return defaultValue;
            }
        }
    }

    /**
     * 获取config表中的配置，如果为空，是否抛出异常
     *
     * @param configCode   变量名称，对应sys_config表中的code
     * @param clazz        返回变量值的类型
     * @param nullThrowExp 若为空是否抛出异常
     * @author yubaoshan
     * @date 2021/6/20 22:03
     */
    /*public static <T> T getSysConfig(String configCode, Class<T> clazz, Boolean nullThrowExp) {
        String configValue = ConstantContext.me().getStr(configCode);
        if (ObjectUtil.isEmpty(configValue)) {
            if (nullThrowExp) {
                String format = StrUtil.format(">>> 系统配置sys_config表中存在空项，configCode为：{}", configCode);
                log.error(format);
                throw new ServiceException(CONSTANT_EMPTY.getCode(), format);
            } else {
                return null;
            }
        } else {
            try {
                return Convert.convert(clazz, configValue);
            } catch (Exception e) {
                if (nullThrowExp) {
                    String format = StrUtil.format(">>> 系统配置sys_config表中存在格式错误的值，configCode={}，configValue={}", configCode, configValue);
                    log.error(format);
                    throw new ServiceException(CONSTANT_EMPTY.getCode(), format);
                } else {
                    return null;
                }
            }
        }
    }*/

    /**
     * 获取验证码 开关标识
     *
     * @author Jax
     * @Date 2021/1/21 15:22
     */
    public static Boolean getCaptchaOpenFlag() {
        return getSysConfigWithDefault("SNOWY_CAPTCHA_OPEN", Boolean.class, true);
    }

}
