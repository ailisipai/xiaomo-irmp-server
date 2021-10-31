package com.xiaomo.cloud.sysemppos.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 员工职位关联表
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysEmpPos extends Model {

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
     * 职位id
     */
    private Long posId;


}
