package com.db.homepage.module.admin.service.impl;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.homepage.common.utils.IPUtils;
import com.db.homepage.module.admin.entity.HomeAccess;
import com.db.homepage.module.admin.mapper.HomeAccessMapper;
import com.db.homepage.module.admin.service.HomeAccessService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author db117
 * @since 2018-10-14
 */
@Service
public class HomeAccessServiceImpl extends ServiceImpl<HomeAccessMapper, HomeAccess> implements HomeAccessService {
    // 一个小时
    private Cache<String, String> cache = CacheUtil.newFIFOCache(1024, 60 * 60 * 1000L);

    @Override
    public void access(HttpServletRequest request) {
        String ip = IPUtils.getIpAddr(request);

        HomeAccess homeAccess = new HomeAccess();
        homeAccess.setIp(ip);

        // 一个小时内部重复记录
        if (cache.get(ip) == null) {
            baseMapper.insert(homeAccess);

            cache.put(ip, ip);
        }
    }
}
