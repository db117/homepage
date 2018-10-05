package com.db.homepage.module.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.db.homepage.module.sys.entity.SysRoleEntity;


/**
 * 角色
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:52
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    void save1(SysRoleEntity role);

    void update(SysRoleEntity role);

    void deleteBatch(Long[] roleIds);

}
