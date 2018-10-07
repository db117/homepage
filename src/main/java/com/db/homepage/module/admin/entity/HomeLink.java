package com.db.homepage.module.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 书签
 * </p>
 *
 * @author db117
 * @since 2018-10-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HomeLink extends Model<HomeLink> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建者id
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    /**
     * 更新者id
     */
    private Long updateUserId;

    /**
     * 更新时间
     */
    private LocalDateTime updateDate;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer delFlag;

    /**
     * 名称
     */
    private String name;

    /**
     * 链接
     */
    private String url;

    /**
     * 图标
     */
    private String ico;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 类型id
     */
    private Long typeId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
