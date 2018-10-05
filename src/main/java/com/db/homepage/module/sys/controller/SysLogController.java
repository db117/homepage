package com.db.homepage.module.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.db.homepage.common.utils.Result;
import com.db.homepage.module.sys.entity.SysLogEntity;
import com.db.homepage.module.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 系统日志
 *
 * @author 117
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 进入列表页
     */
    @RequestMapping("list.html")
    public String list() {
        return "module/sys/log/loglist";
    }

    @RequestMapping("data")
    @ResponseBody
    public Result data(SysLogEntity sysLogEntity, Page<SysLogEntity> page) {
        QueryWrapper<SysLogEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(sysLogEntity.getUsername()), "USERNAME", sysLogEntity.getUsername());
        wrapper.like(StrUtil.isNotBlank(sysLogEntity.getOperation()), "OPERATION", sysLogEntity.getOperation());
        wrapper.orderByDesc("CREATE_DATE");
        return Result.getPage(sysLogService.page(page, wrapper));
    }
}
