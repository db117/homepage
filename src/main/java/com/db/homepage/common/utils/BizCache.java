package com.db.homepage.common.utils;

import com.db.homepage.module.admin.entity.HomeIndex;
import com.db.homepage.module.admin.entity.HomeLink;
import com.db.homepage.module.admin.entity.HomeType;
import com.db.homepage.module.admin.service.HomeIndexService;
import com.db.homepage.module.admin.service.HomeLinkService;
import com.db.homepage.module.admin.service.HomeTypeService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 业务缓存
 *
 * @author 大兵
 * @date 2018-10-07 17:16
 **/
@SuppressWarnings("unchecked")
@Slf4j
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
    private static Cache<String, Object> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .maximumSize(1000)
            .initialCapacity(128).build();
    private HomeIndexService indexService = SpringContextUtils.getType(HomeIndexService.class);
    private HomeTypeService typeService = SpringContextUtils.getType(HomeTypeService.class);
    private HomeLinkService linkService = SpringContextUtils.getType(HomeLinkService.class);

    /**
     * 获取所有首页标签
     */
    public List<HomeIndex> findAllIndex() {
        try {
            return (List<HomeIndex>) cache.get(HOME_INDEX, () ->
                    indexService.lambdaQuery().orderByAsc(HomeIndex::getSort).list());
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    /**
     * 获取所有分类
     */
    public List<HomeType> findAllType() {
        try {
            return (List<HomeType>) cache.get(HOME_TYPE, () -> typeService.lambdaQuery().orderByAsc(HomeType::getSort).list());
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    /**
     * 获取所有网址
     */
    public List<HomeLink> findAllLink() {
        try {
            return (List<HomeLink>) cache.get(HOME_LINK, () ->
                    linkService.lambdaQuery().orderByAsc(HomeLink::getSort).list());
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    /**
     * 根据类型id获取网站
     *
     * @param typeId 类型id
     * @return 网址集合
     */
    public List<HomeLink> findLinkByTypeId(Long typeId) {
        try {
            return (List<HomeLink>) cache.get(HOME_TYPE + typeId, () ->
                    findAllLink().stream().filter(l -> Objects.equals(l.getTypeId(), typeId))
                            .sorted(Comparator.comparing(HomeLink::getSort)).collect(Collectors.toList()));
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return Lists.newArrayList();
    }

    public void clear() {
        cache.cleanUp();
    }
}
