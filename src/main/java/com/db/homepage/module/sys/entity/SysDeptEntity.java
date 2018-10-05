package com.db.homepage.module.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 部门管理
 *
 * @author 117
 */
@TableName("sys_dept")
@Data
public class SysDeptEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    @TableId(type = IdType.ID_WORKER)
    private Long deptId;
    /**
     * 上级部门ID，一级部门为0
     */
    private Long parentId;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 上级部门名称
     */
    @TableField(exist = false)
    private String parentName;
    /**
     * 排序
     */
    private Integer orderNum;

    @TableLogic
    private Integer delFlag;
    /**
     * ztree属性
     */
    @TableField(exist = false)
    private Boolean open;
    @TableField(exist = false)
    private List<?> list;


    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
