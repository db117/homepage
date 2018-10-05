package com.db.homepage.module.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.db.homepage.module.sys.entity.SysDeptEntity;

import java.util.List;

/**
 * 部门管理
 *
 * @author 117
 */
public interface SysDeptDao extends BaseMapper<SysDeptEntity> {

    /**
     * 查询子部门ID列表
     *
     * @param parentId 上级部门ID
     */
    List<Long> queryDetpIdList(Long parentId);

}
