package com.xiaomo.common.context.system;

import cn.hutool.core.lang.Dict;
import com.xiaomo.common.pojo.base.validate.UniqueValidateParam;
import com.xiaomo.common.pojo.login.SysLoginUser;

import java.util.List;

/**
 * 系统相关上下文接口，在system模块实现，用于某些模块不能引用system模块时，通过此方式调用system相关功能
 *
 * @author xiaomo
 * @date 2021/5/6 14:52
 */
public interface SystemContext {

    /**
     * 根据用户id获取姓名
     *
     * @param userId 用户id
     * @return 用户姓名
     * @author xiaomo
     * @date 2021/5/6 14:57
     */
    String getNameByUserId(Long userId);

    /**
     * 根据角色id获取角色名称
     *
     * @param roleId 角色id
     * @return 角色名称
     * @author xiaomo
     * @date 2021/5/22 15:55
     */
    String getNameByRoleId(Long roleId);

    /**
     * 根据token获取登录用户信息
     *
     * @param token token
     * @return 登录用户信息
     * @author xiaomo
     * @date 2021/10/13 11:59
     */
    SysLoginUser getLoginUserByToken(String token);

    /**
     * 根据用户账号模糊搜索系统用户列表
     *
     * @param account 账号
     * @return 增强版hashMap，格式：[{"id:":123, "firstName":"张三"}]
     * @author xiaomo
     * @date 2021/6/1 15:12
     */
    List<Dict> listUser(String account);

    /**
     * 根据角色名模糊搜索系统角色列表
     *
     * @param name 角色名
     * @return 增强版hashMap，格式：[{"id:":123, "name":"总经理"}]
     * @author xiaomo
     * @date 2021/6/1 15:13
     */
    List<Dict> listRole(String name);

    /**
     * 根据id判断是否是用户
     *
     * @param userOrRoleId 用户或角色id
     * @return true是 false否
     * @author xiaomo
     * @date 2021/8/4 20:56
     */
    boolean isUser(Long userOrRoleId);

    /**
     * 根据id判断是否是角色
     *
     * @param userOrRoleId 用户或角色id
     * @return true是 false否
     * @author xiaomo
     * @date 2021/8/4 20:56
     */
    boolean isRole(Long userOrRoleId);

    /**
     * 根据字典类型获取字典的code值
     *
     * @param dictTypeCodes 字典类型编码值
     * @return 字典的code值
     * @author xiaomo
     * @date 2021/8/9 14:18
     */
    List<String> getDictCodesByDictTypeCode(String... dictTypeCodes);

    /**
     * 校验某个表中，某一列是否存在重复的值
     * <p>
     * 一般用于唯一code校验
     *
     * @param uniqueValidateParam 被校验的参数
     * @return true-是唯一的值，false-不是唯一的
     * @author xiaomo
     * @date 2021/8/9 21:41
     */
    boolean tableUniValueFlag(UniqueValidateParam uniqueValidateParam);

    /**
     * 获取系统用户id集合
     *
     * @return 用户id集合
     * @author xiaomo
     * @date 2021/9/11 17:53
     **/
    List<Long> getAllUserIdList();

}
