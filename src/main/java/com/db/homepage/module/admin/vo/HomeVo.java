package com.db.homepage.module.admin.vo;

import com.db.homepage.module.admin.entity.HomeIndex;
import com.db.homepage.module.admin.entity.HomeType;
import lombok.Data;

import java.util.List;

/**
 * 首页显示对象
 *
 * @author 大兵
 * @date 2018-10-07 17:11
 **/
@Data
public class HomeVo {
    List<HomeIndex> indexList;

    List<HomeType> typeList;
}
