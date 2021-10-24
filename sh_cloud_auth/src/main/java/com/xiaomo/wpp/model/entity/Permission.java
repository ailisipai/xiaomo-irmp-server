package com.xiaomo.wpp.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Permission implements Serializable {

    private String roleId;
    private String permissionId;
    private String permissionName;
    private String actions;
    private List<Object> actionEntitySet;
    private String actionList;
    private String dataAccess;

}
