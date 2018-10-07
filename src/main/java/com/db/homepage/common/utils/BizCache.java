package com.db.homepage.common.utils;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.db.homepage.module.admin.entity.HomeIndex;
import com.db.homepage.module.admin.entity.HomeLink;
import com.db.homepage.module.admin.entity.HomeType;
import com.db.homepage.module.admin.service.HomeIndexService;
import com.db.homepage.module.admin.service.HomeLinkService;
import com.db.homepage.module.admin.service.HomeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务缓存
 *
 * @author 大兵
 * @date 2018-10-07 17:16
 **/
@Component
@SuppressWarnings("unchecked")
public class BizCache {
    /**
     * index缓存key
     */
    private static final String HOME_INDEX = "homeIndex";
    /**
     * 类型缓存key
     */
    private static final String HOME_TYPE = "homeType";
    /**
     * 网址缓存key
     */
    private static final String HOME_LINK = "homeLink";
    private static Cache<String, Object> cache = CacheUtil.newFIFOCache(128);
    @Autowired
    private HomeIndexService indexService;
    @Autowired
    private HomeTypeService typeService;
    @Autowired
    private HomeLinkService linkService;

    /**
     * 获取所有首页标签
     */
    public List<HomeIndex> findAllIndex() {
        List<HomeIndex> list = (List<HomeIndex>) cache.get(HOME_INDEX);
        if (list == null) {
            QueryWrapper<HomeIndex> wrapper = new QueryWrapper<>();
            wrapper.orderByAsc("sort");
            list = indexService.list(wrapper);
            cache.put(HOME_INDEX, list);
        }
        return list;
    }

    /**
     * 获取所有分类
     */
    public List<HomeType> findAllType() {
        List<HomeType> list = (List<HomeType>) cache.get(HOME_TYPE);
        if (list == null) {
            QueryWrapper<HomeType> wrapper = new QueryWrapper<>();
            wrapper.orderByAsc("sort");
            list = typeService.list(wrapper);
            cache.put(HOME_TYPE, list);
        }
        return list;
    }

    /**
     * 获取所有网址
     */
    public List<HomeLink> findAllLink() {
        List<HomeLink> list = (List<HomeLink>) cache.get(HOME_LINK);
        if (list == null) {
            QueryWrapper<HomeLink> wrapper = new QueryWrapper<>();
            wrapper.orderByAsc("sort");
            list = linkService.list(wrapper);
            cache.put(HOME_LINK, list);
        }
        return list;
    }

    /**
     * 根据类型id获取网站
     *
     * @param typeId 类型id
     * @return 网址集合
     */
    public List<HomeLink> findLinkByTypeId(Long typeId) {
        return findAllLink().stream().filter(l -> l.getTypeId().equals(typeId)).sorted(Comparator.comparing(HomeLink::getSort)).collect(Collectors.toList());
    }

    public void clear() {
        cache.clear();
    }
}
