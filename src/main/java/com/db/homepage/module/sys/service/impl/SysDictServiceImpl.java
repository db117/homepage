package com.db.homepage.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.homepage.module.sys.dao.SysDictDao;
import com.db.homepage.module.sys.entity.SysDictEntity;
import com.db.homepage.module.sys.service.SysDictService;
import org.springframework.stereotype.Service;


@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictDao, SysDictEntity> implements SysDictService {
}
