package com.xiaomo.wpp.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色信息表
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("SYS_ROLE_INFO")
public class SysRoleInfo extends Model {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    //@TableId(value = "ID", type = IdType.AUTO)
    @TableField("ID")
    private Long id = 12L;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    @TableField("ROLE_NAME")
    private String roleName;

    /**
     * 角色描述
     */
    @ApiModelProperty("角色描述")
    @TableField("ROLE_DESC")
    private String roleDesc;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    /**
     * 排序
     */

    @TableField("ORDER_INDEX")
    private Long orderIndex;

    @TableField(exist=false)
    private String name;

    @TableField(exist = false)
    private String describe;

    @TableField(exist = false)
    private String creatorId="system";

    @TableField(exist = false)
    private String createTime="1497160610259";

    @TableField(exist = false)
    private boolean deleted=false;

    @TableField(exist = false)
    private List<Permission> permissions;


}
