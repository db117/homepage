package com.db.homepage.module.admin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.db.homepage.common.utils.Result;
import com.db.homepage.module.admin.entity.HomeLink;
import com.db.homepage.module.admin.service.HomeLinkService;
import com.db.homepage.module.admin.service.HomeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 书签 前端控制器
 * </p>
 *
 * @author db117
 * @since 2018-10-06
 */
@Controller
@RequestMapping("/admin/homeLink")
public class HomeLinkController {
    @Autowired
    private HomeLinkService linkService;
    @Autowired
    private HomeTypeService typeService;

    @RequestMapping("list.html")
    public String list(Model model, Long typeId) {
        model.addAttribute("typeId", typeId);
        model.addAttribute("types", typeService.list(new QueryWrapper<>()));
        return "module/admin/link/list";
    }

    @RequestMapping("data")
    @ResponseBody
    public Result date(Page<HomeLink> page, HomeLink homeLink) {
        QueryWrapper<HomeLink> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(homeLink.getName()), "name", homeLink.getName());
        wrapper.orderByAsc("sort");
        wrapper.eq(homeLink.getTypeId() != null, "type_id", homeLink.getTypeId());
        return Result.getPage(linkService.page(page, wrapper));
    }

    @RequestMapping("form.html")
    public String form(HomeLink homeLink, Model model, Long type_id) {
        model.addAttribute("homeLink", homeLink.getId() == null ? new HomeLink() : linkService.getById(homeLink.getId()));
        model.addAttribute("type_id", type_id);
        model.addAttribute("types", typeService.list(new QueryWrapper<>()));
        return "module/admin/link/form";
    }

    @RequestMapping("save")
    @ResponseBody
    public Result save(HomeLink homeLink) {
        if (homeLink.getId() == null) {
            return Result.getBool(linkService.save(homeLink));
        } else {
            return Result.getBool(linkService.updateById(homeLink));
        }
    }

    @ResponseBody
    @RequestMapping("delete")
    public Result delete(Long id) {
        return Result.getBool(linkService.removeById(id));
    }

}
