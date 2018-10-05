package com.db.homepage.module.sys.controller;

import com.db.homepage.module.sys.service.SysMenuService;
import com.db.homepage.module.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统页面视图
 *
 * @author 117
 */
@Controller
public class SysPageController {
    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("modules/{module}/{url}.html")
    public String module(@PathVariable("module") String module, @PathVariable("url") String url) {
        return "modules/" + module + "/" + url;
    }

    @RequestMapping(value = {"/", "index.html"})
    public String index(Model model) {
        model.addAttribute("sysUser", ShiroUtils.getUserEntity());
        model.addAttribute("menuList", sysMenuService.getUserMenuList(ShiroUtils.getUserId()));
        return "index";
    }


    @RequestMapping("login.html")
    public String login() {
        return "login";
    }

    @RequestMapping("main.html")
    public String main() {
        return "main";
    }

    @RequestMapping("404.html")
    public String notFound() {
        return "404";
    }

}
