package com.db.homepage.module.admin.controller;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.db.homepage.common.utils.Result;
import com.db.homepage.module.admin.entity.HomeIndex;
import com.db.homepage.module.admin.service.HomeIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 首页图标 前端控制器
 * </p>
 *
 * @author db117
 * @since 2018-10-06
 */
@Controller
@RequestMapping("/admin/homeIndex")
public class HomeIndexController {
    @Autowired
    private HomeIndexService indexService;

    /**
     * 列表
     */
    @RequestMapping(value = "list.html")
    public String list() {
        return "module/admin/homeindex/list";
    }

    /**
     * 获取数据
     *
     * @param page  页面对象
     * @param index 用户对象
     */
    @RequestMapping(value = "data")
    @ResponseBody
    public Result data(Page<HomeIndex> page, HomeIndex index) {
        QueryWrapper<HomeIndex> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        wrapper.like(StrUtil.isNotBlank(index.getName()), "name", index.getName());
        return Result.getPage(indexService.page(page, wrapper));
    }

    @RequestMapping("form.html")
    public String form(HomeIndex homeIndex, Model model) {
        model.addAttribute("homeIndex", homeIndex.getId() == null ? new HomeIndex() : indexService.getById(homeIndex.getId()));
        return "module/admin/homeindex/form";
    }

    /**
     * 保存
     */
    @RequestMapping("save")
    @ResponseBody
    public Result save(HomeIndex homeIndex) {
        if (StrUtil.isBlank(homeIndex.getIco())) {
//            String favicon = "http://statics.dnspod.cn/proxy_favicon/_/favicon?domain=" + homeIndex.getUrl();
            String favicon = "http://" + homeIndex.getUrl() + "/favicon.ico";

            HttpResponse execute = HttpUtil.createGet(favicon).execute();

            if (execute.getStatus() == 301) {
                favicon = "https://" + homeIndex.getUrl() + "/favicon.ico";
                homeIndex.setIco(Base64.encode(HttpUtil.createGet(favicon).execute().bodyStream()));
            } else {
                homeIndex.setIco(Base64.encode(execute.bodyStream()));
            }
        }
        if (homeIndex.getId() == null) {
            return Result.getBool(indexService.save(homeIndex));
        }
        return Result.getBool(indexService.updateById(homeIndex));
    }

    /**
     * 上传图片转换为base64
     *
     * @param file 上传的图片
     */
    @RequestMapping("upload")
    @ResponseBody
    public Result imgUpload(MultipartFile file) {
        Map<String, String> map = new HashMap<>();
        String encode = null;
        try {
            encode = Base64.encode(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("src", encode);
        return Result.getData(map);
    }


    @RequestMapping("delete")
    @ResponseBody
    public Result delete(Long id) {
        return Result.getBool(indexService.removeById(id));
    }
}
