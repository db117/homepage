package com.db.homepage.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.homepage.module.sys.dao.SysLogDao;
import com.db.homepage.module.sys.entity.SysLogEntity;
import com.db.homepage.module.sys.service.SysLogService;
import org.springframework.stereotype.Service;


@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {
}
