package com.db.homepage.module.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.db.homepage.module.admin.entity.HomeIndex;
import com.db.homepage.module.admin.vo.HomeVo;

/**
 * <p>
 * 首页图标 服务类
 * </p>
 *
 * @author db117
 * @since 2018-10-06
 */
public interface HomeIndexService extends IService<HomeIndex> {

    /**
     * 获取首页对象
     */
    HomeVo getHomeVo();
}
