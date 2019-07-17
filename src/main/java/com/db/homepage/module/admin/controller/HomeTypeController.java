package com.db.homepage.module.admin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.db.homepage.common.utils.BizCache;
import com.db.homepage.common.utils.Result;
import com.db.homepage.module.admin.entity.HomeType;
import com.db.homepage.module.admin.service.HomeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 书签类型 前端控制器
 * </p>
 *
 * @author db117
 * @since 2018-10-06
 */
@Controller
@RequestMapping("/admin/homeType")
public class HomeTypeController {

    @Autowired
    private HomeTypeService typeService;

    @RequestMapping("list.html")
    public String list() {
        return "module/admin/type/list";
    }

    @RequestMapping("data")
    @ResponseBody
    public Result date(Page<HomeType> page, HomeType homeType) {
        QueryWrapper<HomeType> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(homeType.getName()), "name", homeType.getName());
        wrapper.orderByAsc("sort");
        return Result.getPage(typeService.page(page, wrapper));
    }

    @RequestMapping("form.html")
    public String form(HomeType homeType, Model model) {
        model.addAttribute("homeType", homeType.getId() == null ? new HomeType() : typeService.getById(homeType.getId()));
        return "module/admin/type/form";
    }

    @RequestMapping("save")
    @ResponseBody
    public Result save(HomeType homeType) {
        BizCache.clear();
        if (homeType.getId() == null) {
            return Result.getBool(typeService.save(homeType));
        } else {
            return Result.getBool(typeService.updateById(homeType));
        }
    }

    @ResponseBody
    @RequestMapping("delete")
    public Result delete(Long id) {
        BizCache.clear();
        return Result.getBool(typeService.removeById(id));
    }
}
