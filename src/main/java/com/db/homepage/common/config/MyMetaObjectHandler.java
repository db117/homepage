package com.db.homepage.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.db.homepage.module.sys.entity.SysUserEntity;
import com.db.homepage.module.sys.shiro.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @author 大兵
 * @date 2018-04-04 22:41
 * 注入公共字段自动填充,任选注入方式即可
 **/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
//        log.debug("新增的时候干点不可描述的事情");

        SysUserEntity sysUser = ShiroUtils.getUserEntity();

        //创建时间
        Object createDate = getFieldValByName("createDate", metaObject);
        if (null == createDate) {
            setFieldValByName("createDate", new Date(), metaObject);
        }

        //创建人
        Object createBy = getFieldValByName("createBy", metaObject);
        if (null == createBy) {
            if (sysUser != null) {
                setFieldValByName("createBy", sysUser.getUserId().toString(), metaObject);
            }
        }

//        //更新时间
//        Object updateDate = getFieldValByName("updateDate", metaObject);
//        if (null == updateDate) {
//            setFieldValByName("updateDate", new Date(), metaObject);
//        }
//
//        //更新者
//        Object updateBy = getFieldValByName("updateBy", metaObject);
//        if (null == updateBy) {
//            if (sysUser != null) {
//                setFieldValByName("updateBy", sysUser.getUserId().toString(), metaObject);
//            }
//        }
        //删除标识
//        Object delFlag = getFieldValByName("delFlag", metaObject);
//        if (null == delFlag) {
//            setFieldValByName("delFlag", "0", metaObject);
//        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        log.debug("更新的时候干点不可描述的事情");
        SysUserEntity sysUser = ShiroUtils.getUserEntity();

        //更新者
        Object updateBy = getFieldValByName("updateBy", metaObject);
        if (null == updateBy) {
            if (sysUser != null) {
                setFieldValByName("updateBy", sysUser.getUserId().toString(), metaObject);
            }
        }
    }
}

