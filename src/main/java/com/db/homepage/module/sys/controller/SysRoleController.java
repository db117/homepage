package com.db.homepage.module.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.db.homepage.common.annotation.SysLog;
import com.db.homepage.common.utils.Result;
import com.db.homepage.common.utils.SysCache;
import com.db.homepage.module.sys.entity.SysRoleEntity;
import com.db.homepage.module.sys.service.SysRoleDeptService;
import com.db.homepage.module.sys.service.SysRoleMenuService;
import com.db.homepage.module.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 角色管理
 *
 * @author 117
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleDeptService sysRoleDeptService;

    /**
     * 进入列表
     */
    @GetMapping(value = "list.html")
    public String list() {
        return "module/sys/role/rolelist";
    }

    /**
     * 获取数据
     */
    @PostMapping(value = "data")
    @ResponseBody
    public Result data(SysRoleEntity menu, Page<SysRoleEntity> page) {
        QueryWrapper<SysRoleEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(menu.getRoleName()), "role_name", menu.getRoleName());

        sysRoleService.page(page, wrapper);


        //设置其他属性
        page.getRecords().forEach(role -> {
            SysRoleEntity temp = SysCache.getRoleById(role.getRoleId());

            role.setDeptIdList(temp.getDeptIdList());
            role.setMenuIdList(temp.getMenuIdList());
            role.setDeptName(temp.getDeptName());
        });
        return Result.getPage(page);
    }

    @GetMapping(value = "form.html")
    public String form(Model model, Long roleId) {
        model.addAttribute("role", SysCache.getRoleById(roleId));
        return "module/sys/role/roleform";
    }


    /**
     * 保存角色
     */
    @SysLog("保存角色")
    @RequestMapping("/save")
    @ResponseBody
    public Result save(SysRoleEntity role) {

        sysRoleService.save1(role);

        SysCache.clear();
        return Result.getSuccess();
    }

    /**
     * 修改角色
     */
    @SysLog("修改角色")
    @RequestMapping("/update")
    @ResponseBody
    public Result update(SysRoleEntity role) {

        sysRoleService.update(role);

        SysCache.clear();
        return Result.getSuccess();
    }

    /**
     * 删除角色
     */
    @SysLog("删除角色")
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);

        SysCache.clear();
        return Result.getSuccess();
    }
}
