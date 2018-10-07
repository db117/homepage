package com.db.homepage.module.sys.controller;


import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.db.homepage.common.annotation.SysLog;
import com.db.homepage.common.utils.Result;
import com.db.homepage.common.utils.SysCache;
import com.db.homepage.module.sys.entity.SysUserEntity;
import com.db.homepage.module.sys.service.SysDeptService;
import com.db.homepage.module.sys.service.SysUserRoleService;
import com.db.homepage.module.sys.service.SysUserService;
import com.db.homepage.module.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Objects;

/**
 * 系统用户
 *
 * @author 117
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysCache sysCache;
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 用户列表
     */
    @RequestMapping(value = "list.html")
    public String list() {
        return "module/sys/user/userlist";
    }

    /**
     * 获取数据
     *
     * @param page 页面对象
     * @param user 用户对象
     */
    @RequestMapping(value = "data")
    @ResponseBody
    public Result data(Page<SysUserEntity> page, SysUserEntity user) {

        return Result.getPage(sysUserService.findList(page, user));
    }

    /**
     * 进入用户表单
     *
     * @param sysUserEntity 用户对象
     */
    @RequestMapping(value = "form.html")
    public String form(SysUserEntity sysUserEntity, Model model) {
        SysUserEntity sysUser = sysUserService.getById(sysUserEntity.getUserId());
        if (Objects.nonNull(sysUser)) {
            sysUser.setDeptName(sysCache.getDeptById(sysUser.getDeptId()).getName());
            sysUser.setRoleIdList(sysCache.getRoleIdByUserId(sysUser.getUserId()));
            model.addAttribute("sysUser", sysUser);
        } else {
            model.addAttribute("sysUser", new SysUserEntity());
        }
        model.addAttribute("roleList", sysCache.getAllRole());

        return "module/sys/user/userform";
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @RequestMapping("/password")
    @ResponseBody
    public Result password(String password, String newPassword) {
        //明文密码
        String cleartext = password;

        //原密码
        password = ShiroUtils.sha256(password, getUser().getSalt());
        //新密码
        newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());

        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword, cleartext);
        if (!flag) {
            return Result.getFailure("原密码不正确");
        }

        return Result.getSuccess();
    }


    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @RequestMapping("/save")
    @ResponseBody
    public Result save(SysUserEntity user) {

        sysUserService.save1(user);

        sysCache.clear();
        return Result.getSuccess();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @ResponseBody
    public Result update(SysUserEntity user) {

        sysUserService.update(user);

        sysCache.clear();
        return Result.getSuccess();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(Long[] userIds) {
        if (ArrayUtil.contains(userIds, 1L)) {
            return Result.getFailure("系统管理员不能删除");
        }

        if (ArrayUtil.contains(userIds, getUserId())) {
            return Result.getFailure("当前用户不能删除");
        }

        sysUserService.removeByIds(Arrays.asList(userIds));

        sysCache.clear();
        return Result.getSuccess();
    }

    /**
     * 个人信息
     *
     * @param userId 用户id
     */
    @RequestMapping(value = "info.html")
    public String userInfo(String userId, Model model) {
        SysUserEntity sysUser = sysUserService.getById(userId);
        sysUser.setDeptName(sysCache.getDeptById(sysUser.getDeptId()).getName());
        sysUser.setRoleIdList(sysCache.getRoleIdByUserId(sysUser.getUserId()));
        model.addAttribute("sysUser", sysUser);

        model.addAttribute("roleList", sysCache.getAllRole());
        return "module/sys/user/userinfo";
    }
}
