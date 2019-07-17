package com.db.homepage.module.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.db.homepage.common.utils.Result;
import com.db.homepage.common.utils.SysCache;
import com.db.homepage.module.sys.entity.SysDictEntity;
import com.db.homepage.module.sys.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/**
 * 数据字典
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.1.0 2018-01-27
 */
@Controller
@RequestMapping("sys/dict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;

    /**
     * 进入列表页面
     */
    @GetMapping(value = "list.html")
    public String listPage() {
        return "module/sys/dict/dictlist";
    }

    /**
     * 数据
     */
    @PostMapping(value = "data")
    @ResponseBody
    public Result data(SysDictEntity dict, Page<SysDictEntity> page) {
        QueryWrapper<SysDictEntity> wrapper = new QueryWrapper<SysDictEntity>();
        wrapper.like(StrUtil.isNotBlank(dict.getName()), "name", dict.getName());
        wrapper.like(StrUtil.isNotBlank(dict.getType()), "type", dict.getType());
        return Result.getPage(sysDictService.page(page, wrapper));
    }

    /**
     * 进入表单
     *
     * @param id id
     */
    @GetMapping(value = "form.html")
    public String form(Long id, Model model) {
        model.addAttribute("dict", SysCache.getDictById(id));
        return "module/sys/dict/dictform";
    }

    /**
     * 添加值表单
     *
     * @param id id
     */
    @GetMapping(value = "form2.html")
    public String form2(Long id, Model model) {
        SysDictEntity dict = SysCache.getDictById(id);
        SysDictEntity temp = new SysDictEntity();
        temp.setName(dict.getName());
        temp.setOrderNum(SysCache.getMaxOrderNum(dict.getType()));
        temp.setType(dict.getType());
        temp.setRemark(dict.getRemark());
        temp.setCode(dict.getCode() + "1");

        model.addAttribute("dict", temp);
        return "module/sys/dict/dictform";
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public Result save(SysDictEntity dict) {

        sysDictService.save(dict);

        SysCache.clear();

        return Result.getSuccess();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @ResponseBody
    public Result update(SysDictEntity dict) {

        sysDictService.updateById(dict);

        SysCache.clear();

        return Result.getSuccess();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(Long[] ids) {
        sysDictService.removeByIds(Arrays.asList(ids));

        SysCache.clear();
        return Result.getSuccess();
    }

}
