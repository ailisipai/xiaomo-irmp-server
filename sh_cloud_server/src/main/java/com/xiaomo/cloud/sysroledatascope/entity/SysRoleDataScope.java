package com.xiaomo.cloud.sysroledatascope.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统角色数据范围表
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleDataScope extends Model {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 机构id
     */
    private Long orgId;


}
