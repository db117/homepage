package com.db.homepage.module.sys.controller;

import cn.hutool.core.collection.CollUtil;
import com.db.homepage.common.utils.Constant;
import com.db.homepage.common.utils.Result;
import com.db.homepage.common.utils.SysCache;
import com.db.homepage.module.sys.entity.SysDeptEntity;
import com.db.homepage.module.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 部门管理
 */
@Controller
@RequestMapping("/sys/dept")
public class SysDeptController extends AbstractController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 选择页面
     *
     * @return 选择页面
     */
    @RequestMapping(value = "tree.html")
    public String tree() {
        return "module/sys/dept/depttre";
    }

    /**
     * 列表页面
     *
     * @return 列表页面
     */
    @GetMapping(value = "list.html")
    public String list() {
        return "module/sys/dept/deptlist";
    }

    /**
     * 进入表单页面
     */
    @GetMapping(value = "form.html")
    public String form(SysDeptEntity deptEntity, Model model) {
        model.addAttribute("dept", SysCache.getDeptById(deptEntity.getDeptId()));
        return "module/sys/dept/deptform";
    }

    /**
     * 列表数据
     */
    @RequestMapping("/data")
    @ResponseBody
    public Result data() {
        return Result.getPageRes((long) SysCache.getAllDept().size(), SysCache.getAllDept());
    }

    /**
     * 返回所有部门
     */
    @RequestMapping(value = "treeData")
    @ResponseBody
    public List<SysDeptEntity> treeData() {
        List<SysDeptEntity> deptList = CollUtil.newArrayList(SysCache.getAllDept());

        deptList.forEach(d -> {
            d.setOpen(true);
        });
        //添加一级部门
        if (getUserId() == Constant.SUPER_ADMIN) {
            SysDeptEntity root = new SysDeptEntity();
            root.setDeptId(0L);
            root.setName("一级部门");
            root.setParentId(-1L);
            root.setOpen(true);
            deptList.add(root);
        }
        return deptList;
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public Result save(SysDeptEntity dept) {
        sysDeptService.save(dept);

        SysCache.clear();

        return Result.getSuccess();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @ResponseBody
    public Result update(SysDeptEntity dept) {
        sysDeptService.updateById(dept);

        SysCache.clear();

        return Result.getSuccess();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(long deptId) {
        //判断是否有子部门
        List<Long> deptList = sysDeptService.queryDetpIdList(deptId);
        if (deptList.size() > 0) {
            return Result.getFailure("请先删除子部门");
        }

        sysDeptService.removeById(deptId);

        SysCache.clear();

        return Result.getSuccess();
    }

}
