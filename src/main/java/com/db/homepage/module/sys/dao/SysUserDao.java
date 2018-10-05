package com.db.homepage.module.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.db.homepage.module.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户
 *
 * @author 117
 */
public interface SysUserDao extends BaseMapper<SysUserEntity> {

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 分页查找用户
     *
     * @param page          分页对象
     * @param sysUserEntity 用户对戏
     */
    Page<SysUserEntity> findList(Page<SysUserEntity> page, @Param("sysUser") SysUserEntity sysUserEntity);

}
