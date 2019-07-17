package com.db.homepage.module.sys.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.db.homepage.common.annotation.SysLog;
import com.db.homepage.common.utils.Constant;
import com.db.homepage.common.utils.DbException;
import com.db.homepage.common.utils.Result;
import com.db.homepage.common.utils.SysCache;
import com.db.homepage.module.sys.entity.SysMenuEntity;
import com.db.homepage.module.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 系统菜单
 */
@Controller
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {
    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping(value = "list.html")
    public String list1() {
        return "module/sys/menu/menulist";
    }

    /**
     * 选择页面
     *
     * @return 选择页面
     */
    @RequestMapping(value = "tree.html")
    public String tree() {
        return "module/sys/menu/menutree";
    }


    @RequestMapping(value = "data")
    @ResponseBody
    public Result listData() {

        return Result.getPageRes((long) SysCache.getAllMenu().size(), SysCache.getAllMenu());
    }

    @RequestMapping(value = "treeData")
    @ResponseBody
    public List<SysMenuEntity> treeData() {
        List<SysMenuEntity> list = CollUtil.newArrayList(SysCache.getMenuNotButten());

        list.forEach(m -> {
            m.setOpen(true);
        });

        //添加顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        list.add(root);

        return list;
    }

    /**
     * 进入表单页面
     */
    @GetMapping(value = "form.html")
    public String form(Long menuId, Model model) {
        model.addAttribute("menu", SysCache.getMenuById(menuId));
        return "module/sys/menu/menuform";
    }


    /**
     * 所有菜单列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<SysMenuEntity> list() {
        List<SysMenuEntity> menuList = sysMenuService.list(null);
        for (SysMenuEntity sysMenuEntity : menuList) {
            SysMenuEntity parentMenuEntity = sysMenuService.getById(sysMenuEntity.getParentId());
            if (parentMenuEntity != null) {
                sysMenuEntity.setParentName(parentMenuEntity.getName());
            }
        }

        return menuList;
    }


    /**
     * 保存
     */
    @SysLog("保存菜单")
    @RequestMapping("/save")
    @ResponseBody
    public Result save(SysMenuEntity menu) {
        //数据校验
        verifyForm(menu);

        if (menu.getOrderNum() == null) {
            menu.setOrderNum(100);
        }
        sysMenuService.save(menu);

        SysCache.clear();
        return Result.getSuccess();
    }

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @RequestMapping("/update")
    @ResponseBody
    public Result update(SysMenuEntity menu) {
        //数据校验
        verifyForm(menu);

        sysMenuService.updateById(menu);
        SysCache.clear();
        return Result.getSuccess();
    }

    /**
     * 删除
     */
    @SysLog("删除菜单")
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(long menuId) {
        if (menuId <= 31) {
            return Result.getFailure("系统菜单，不能删除");
        }

        //判断是否有子菜单或按钮
        List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
        if (menuList.size() > 0) {
            return Result.getFailure("请先删除子菜单或按钮");
        }

        sysMenuService.delete(menuId);
        SysCache.clear();
        return Result.getSuccess();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenuEntity menu) {
        if (StrUtil.isBlank(menu.getName())) {
            throw new DbException("菜单名称不能为空");
        }

        if (menu.getParentId() == null) {
            throw new DbException("上级菜单不能为空");
        }

        //菜单
        if (menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (StrUtil.isBlank(menu.getUrl())) {
                throw new DbException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenuEntity parentMenu = sysMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if (menu.getType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                throw new DbException("上级菜单只能为目录类型");
            }
            return;
        }

        //按钮
        if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                throw new DbException("上级菜单只能为菜单类型");
            }
        }
    }
}
