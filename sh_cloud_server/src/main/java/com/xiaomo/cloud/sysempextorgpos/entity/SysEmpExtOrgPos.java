package com.xiaomo.cloud.sysempextorgpos.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 员工附属机构岗位表
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysEmpExtOrgPos extends Model {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 员工id
     */
    private Long empId;

    /**
     * 机构id
     */
    private Long orgId;

    /**
     * 岗位id
     */
    private Long posId;


}
