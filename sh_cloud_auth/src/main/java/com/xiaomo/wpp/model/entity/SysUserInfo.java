package com.xiaomo.wpp.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("SYS_USER_INFO")
@ApiModel(value = "系统用户",reference = "xiaomo")
public class SysUserInfo extends Model {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户id")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    @TableField("USER_NAME")
    private String userName;

    /**
     * 用户密码
     */
    @ApiModelProperty("用户密码")
    @TableField("PASSWORD")
    private String password;

    /**
     * 真实姓名
     */
    @ApiModelProperty("真实姓名")
    @TableField("REAL_NAME")
    private String realName;

    /**
     * 地址
     */
    @ApiModelProperty("地址")
    @TableField("ADDRESS")
    private String address;

    /**
     * 用户状态：封禁、未封禁
     */
    @ApiModelProperty("用户状态:封禁、未封禁")
    @TableField("STATUS")
    private String status;

    /**
     * 当前用户是否删除：1：true 0:false
     */
    @ApiModelProperty("当前用户是否删除：1：true 0:false")
    @TableField("IS_DEL")
    private Boolean isDel;

    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    @TableField("USER_EMAIL")
    private String userEmail;

    /**
     * 用户电话
     */
    @ApiModelProperty(value = "电话",dataType = "String",allowEmptyValue = true)
    @TableField("USER_IPHONE")
    private String userIphone;

    /**
     * 用户年龄
     */
    @ApiModelProperty("用户年龄")
    @TableField("USER_AGE")
    private Integer userAge;

    /**
     * 用户生日
     */
    @ApiModelProperty("用户生日")
    @TableField("USER_BIRTHDAY")
    private LocalDate userBirthday;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;

    /**
     * 学校id
     */
    @ApiModelProperty(value = "学校id" )
    @TableField("SCHOOL_ID")
    private Long schoolId;


    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String avatar="/avatar2.jpg";

    @TableField(exist = false)
    private String telephone;

    @TableField(exist = false)
    private String lastLoginIp="172.30.149.26";

    @TableField(exist = false)
    private long lastLoginTime=System.currentTimeMillis();

    @TableField(exist = false)
    private String creatorId="xiaomo";

    @TableField(exist = false)
    private String merchantCode="TLif2btpzg079h15bk";
    @TableField(exist = false)
    private String roleId="admin";
    /**
     * 用户角色列表
     */
    @TableField(exist = false)
    private SysRoleInfo role;
}
