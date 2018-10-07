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
 * 书签类型
 * </p>
 *
 * @author db117
 * @since 2018-10-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HomeType extends Model<HomeType> {

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
     * 排序
     */
    private Integer sort;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
