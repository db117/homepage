package com.db.homepage.module.sys.service.impl;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.homepage.common.utils.SysCache;
import com.db.homepage.module.sys.dao.SysUserDao;
import com.db.homepage.module.sys.entity.SysUserEntity;
import com.db.homepage.module.sys.service.SysDeptService;
import com.db.homepage.module.sys.service.SysUserRoleService;
import com.db.homepage.module.sys.service.SysUserService;
import com.db.homepage.module.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * 系统用户
 *
 * @author 117
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save1(SysUserEntity user) {
        user.setCreateTime(new Date());

        //sha256加密
        String salt = RandomUtil.randomString(20);
        user.setSalt(salt);
        user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
        baseMapper.insert(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserEntity user) {
        if (StrUtil.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {

            user.setPassword(ShiroUtils.sha256(user.getPassword(), SysCache.getUserById(user.getUserId()).getSalt()));
        }
        this.updateById(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }


    @Override
    public boolean updatePassword(Long userId, String password, String newPassword, String cleartext) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);

        return this.update(userEntity,
                new QueryWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
    }

    @Override
    public IPage findList(Page<SysUserEntity> page, SysUserEntity sysUserEntity) {
        return baseMapper.findList(page, sysUserEntity);
    }


}
