package com.db.homepage.module.admin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.db.homepage.common.utils.Result;
import com.db.homepage.module.admin.entity.HomeAccess;
import com.db.homepage.module.admin.service.HomeAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author db117
 * @since 2018-10-14
 */
@Controller
@RequestMapping("/admin/homeAccess")
public class HomeAccessController {
    @Autowired
    private HomeAccessService accessService;

    /**
     * 列表
     */
    @RequestMapping(value = "list.html")
    public String list() {
        return "module/admin/access/list";
    }

    /**
     * 获取数据
     *
     * @param page   页面对象
     * @param access 对象
     */
    @RequestMapping(value = "data")
    @ResponseBody
    public Result data(Page<HomeAccess> page, HomeAccess access) {
        QueryWrapper<HomeAccess> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.like(StrUtil.isNotBlank(access.getIp()), "ip", access.getIp());
        return Result.getPage(accessService.page(page, wrapper));
    }
}
