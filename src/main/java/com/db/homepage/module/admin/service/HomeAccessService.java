package com.db.homepage.module.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.db.homepage.module.admin.entity.HomeAccess;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author db117
 * @since 2018-10-14
 */
public interface HomeAccessService extends IService<HomeAccess> {
    /**
     * 进入首页
     */
    void access(HttpServletRequest request);
}
