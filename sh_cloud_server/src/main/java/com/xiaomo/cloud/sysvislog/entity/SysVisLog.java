package com.xiaomo.cloud.sysvislog.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 系统访问日志表
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysVisLog extends Model {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否执行成功（Y-是，N-否）
     */
    private String success;

    /**
     * 具体消息
     */
    private String message;

    /**
     * ip
     */
    private String ip;

    /**
     * 地址
     */
    private String location;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 操作类型（字典 1登入 2登出）
     */
    private Integer visType;

    /**
     * 访问时间
     */
    @Excel(name = "操作时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd HH:mm:ss", width = 20)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visTime;

    /**
     * 访问账号
     */
    private String account;


}
