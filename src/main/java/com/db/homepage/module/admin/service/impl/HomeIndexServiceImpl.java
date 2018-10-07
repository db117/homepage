package com.db.homepage.module.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.homepage.common.utils.BizCache;
import com.db.homepage.module.admin.entity.HomeIndex;
import com.db.homepage.module.admin.entity.HomeType;
import com.db.homepage.module.admin.mapper.HomeIndexMapper;
import com.db.homepage.module.admin.service.HomeIndexService;
import com.db.homepage.module.admin.vo.HomeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 首页图标 服务实现类
 * </p>
 *
 * @author db117
 * @since 2018-10-06
 */
@Service
public class HomeIndexServiceImpl extends ServiceImpl<HomeIndexMapper, HomeIndex> implements HomeIndexService {
    @Autowired
    private BizCache bizCache;

    @Override
    public HomeVo getHomeVo() {
        HomeVo homeVo = new HomeVo();
        homeVo.setIndexList(bizCache.findAllIndex());

        List<HomeType> typeList = bizCache.findAllType();

        // 放入网址
        typeList.forEach(t -> {
            t.setLinkList(bizCache.findLinkByTypeId(t.getId()));
        });

        // 排序
        typeList = typeList.stream().sorted(Comparator.comparing(HomeType::getSort)).collect(Collectors.toList());

        homeVo.setTypeList(typeList);
        return homeVo;
    }
}
