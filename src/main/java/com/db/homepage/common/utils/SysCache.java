package com.db.homepage.common.utils;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.db.homepage.module.sys.entity.*;
import com.db.homepage.module.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 字典工具类
 *
 * @author 大兵
 * @date 2018-07-17 16:07
 **/
@Component
@SuppressWarnings("unchecked")
public class SysCache {
    /**
     * 字典缓存key
     */
    private static final String DICT_KEY = "dict";
    /**
     * 用户缓存key
     */
    private static final String USER_KEY = "user";
    /**
     * 机构缓存key
     */
    private static final String DEPT_KEY = "dept";
    /**
     * 角色缓存KEY
     */
    private static final String ROLE_KEY = "role";
    /**
     * 用户角色缓存key
     */
    private static final String USER_ROLE_KEY = "user_role";
    /**
     * 角色机构缓存KEY
     */
    private static final String ROLE_DEPT_KEY = "role_dept";
    /**
     * 菜单缓存key
     */
    private static final String MENU_KEY = "menu_key";
    /**
     * 菜单角色缓存key
     */
    private static final String ROLE_MENU_KEY = "role_menu_key";
    private static Cache<String, Object> cache = CacheUtil.newFIFOCache(128);
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleDeptService sysRoleDeptService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 获取所有字典
     */
    public List<SysDictEntity> getAllDict() {
        List<SysDictEntity> list = (List<SysDictEntity>) cache.get(DICT_KEY);
        if (list == null) {
            list = sysDictService.list(new QueryWrapper<>());
            cache.put(DICT_KEY, list);
        }
        return list;
    }

    /**
     * 根据id获取字典
     *
     * @param id 字典id
     * @return 字典对象
     */
    public SysDictEntity getDictById(Long id) {
        return getAllDict().stream().filter(d -> d.getId().equals(id)).findFirst().orElse(new SysDictEntity());
    }

    /**
     * 获取该类型的最大排序
     *
     * @param type 类型
     * @return 排序
     */
    public Integer getMaxOrderNum(String type) {
        int l = getAllDict().stream().filter(d -> d.getType().equals(type)).map(SysDictEntity::getOrderNum).mapToInt(i -> i).max().orElse(0);
        return l == 0 ? 0 : l + 10;
    }

    /**
     * 根据字典类型获取字典列表
     * 如果没有则返回null
     *
     * @param type 字典类型
     */
    public List<SysDictEntity> getDictByType(String type) {
        if (StrUtil.isBlank(type)) {
            return new ArrayList<>();
        }
        return getAllDict().stream().filter(dict -> dict.getType().equals(type)).collect(Collectors.toList());
    }

    /**
     * 根据字典类型和字典获取字典值
     * 如果没有找到返回null
     *
     * @param type 字典类型
     * @param code 字典码
     */
    public SysDictEntity getDictByTypeAndCode(String type, String code) {
        if (StrUtil.isBlank(type) || StrUtil.isBlank(code)) {
            return null;
        }
        return getAllDict().stream().filter(dict -> dict.getType().equals(type) && dict.getCode().equals(code)).findFirst().orElse(null);
    }

    /**
     * 获取所有用户
     */
    public List<SysUserEntity> getAllUser() {
        List<SysUserEntity> list = (List<SysUserEntity>) cache.get(USER_KEY);
        if (Objects.isNull(list)) {
            list = sysUserService.list(new QueryWrapper<>());
            cache.put(USER_KEY, list);
        }
        return list;
    }

    /**
     * 根据id获取用户
     *
     * @param id 用户id
     */
    public SysUserEntity getUserById(Long id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return getAllUser().stream().filter(user -> id.equals(user.getUserId())).findFirst().orElse(null);
    }

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户
     */
    public SysUserEntity getUserByUsername(String username) {
        return getAllUser().stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    /**
     * 根据机构id获取所有用户
     *
     * @param deptId 机构id
     */
    public List<SysUserEntity> getUserByDeptId(Long deptId) {
        if (Objects.isNull(deptId)) {
            return null;
        }
        return getAllUser().stream().filter(user -> user.getDeptId().equals(deptId)).collect(Collectors.toList());
    }

    /**
     * 获取所有的角色
     */
    public List<SysRoleEntity> getAllRole() {
        List<SysRoleEntity> roleEntityList = (List<SysRoleEntity>) cache.get(ROLE_KEY);
        if (Objects.isNull(roleEntityList)) {
            roleEntityList = sysRoleService.list(new QueryWrapper<>());

            roleEntityList.forEach(role -> {
                role.setDeptName(getDeptById(role.getDeptId()).getName());
                role.setMenuIdList(getAllRoleMenu().stream().filter(rm -> rm.getRoleId().equals(role.getRoleId()))
                        .map(SysRoleMenuEntity::getMenuId).collect(Collectors.toList()));
                role.setDeptIdList(getAllRoleDept().stream().filter(rd -> rd.getRoleId().equals(role.getRoleId()))
                        .map(SysRoleDeptEntity::getDeptId).collect(Collectors.toList()));
            });
            cache.put(ROLE_KEY, roleEntityList);
        }
        return roleEntityList;
    }

    /**
     * 根据角色id获取角色
     *
     * @param id 角色id
     * @return 角色对象
     */
    public SysRoleEntity getRoleById(Long id) {
        return getAllRole().stream().filter(r -> r.getRoleId().equals(id)).findFirst().orElse(new SysRoleEntity());
    }

    /**
     * 获取用户的所有角色
     *
     * @param userId 用户id
     * @return 角色列表
     */
    public List<SysRoleEntity> getRoleByUserId(Long userId) {
        List<SysRoleEntity> list = new ArrayList<>();
        getAllUserRole().stream().filter(ur -> ur.getUserId().equals(userId)).collect(Collectors.toList()).forEach(ur -> {
            list.add(getRoleById(ur.getRoleId()));
        });
        return list;
    }

    /**
     * 获取所有的机构
     */
    public List<SysDeptEntity> getAllDept() {
        List<SysDeptEntity> list = (List<SysDeptEntity>) cache.get(DEPT_KEY);
        if (Objects.isNull(list)) {
            list = sysDeptService.list(new QueryWrapper<>());
            List<SysDeptEntity> temp = list;
            list.forEach(d -> {
                String pName = temp.stream().filter(t -> t.getDeptId().equals(d.getParentId())).findFirst().orElse(new SysDeptEntity()).getName();
                d.setParentName(pName);
                if (d.getDeptId().equals(1L)) {
                    d.setParentName("一级部门");
                }
            });
            cache.put(DEPT_KEY, list);
        }
        return list;
    }

    /**
     * 获取所有的角色机构关联
     */
    public List<SysRoleDeptEntity> getAllRoleDept() {
        List<SysRoleDeptEntity> list = (List<SysRoleDeptEntity>) cache.get(ROLE_DEPT_KEY);
        if (Objects.isNull(list)) {
            list = sysRoleDeptService.list(new QueryWrapper<>());
            cache.put(ROLE_DEPT_KEY, list);
        }
        return list;
    }

    /**
     * 根据id获取机构
     *
     * @param id 机构id
     * @return 机构
     */
    public SysDeptEntity getDeptById(Long id) {
        return getAllDept().stream().filter(d -> d.getDeptId().equals(id)).findFirst().orElse(new SysDeptEntity());
    }

    /**
     * 获取角色的所有机构权限
     *
     * @param roleId 角色id
     */
    public List<SysDeptEntity> getDeptByRoleId(Long roleId) {

        List<Long> deptIds = getAllRoleDept().stream().filter(rd -> rd.getRoleId().equals(roleId)).map(SysRoleDeptEntity::getDeptId).collect(Collectors.toList());

        return getAllDept().stream().filter(dept -> deptIds.contains(dept.getDeptId())).collect(Collectors.toList());
    }

    /**
     * 获取所有的用户角色关联
     */
    public List<SysUserRoleEntity> getAllUserRole() {
        List<SysUserRoleEntity> list = (List<SysUserRoleEntity>) cache.get(USER_ROLE_KEY);
        if (Objects.isNull(list)) {
            list = sysUserRoleService.list(new QueryWrapper<>());
            cache.put(USER_ROLE_KEY, list);
        }
        return list;
    }

    /**
     * 获取用户的所有的角色id
     *
     * @param userId 用户id
     * @return 角色id集合
     */
    public List<Long> getRoleIdByUserId(Long userId) {
        return getAllUserRole().stream().filter(ur -> ur.getUserId().equals(userId)).map(SysUserRoleEntity::getRoleId).collect(Collectors.toList());
    }

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户id
     */
    public List<String> getPermsByUserId(Long userId) {
        return getMenuByUserId(userId).stream().map(SysMenuEntity::getPerms).collect(Collectors.toList());
    }

    /**
     * 获取用户可以访问数据的用户id
     *
     * @param userId 用户id
     */
    public long[] getDeptIdByUserId(Long userId) {
        //获取用户所有的角色
        long[] roles = getAllUserRole().stream().filter(ur -> userId.equals(ur.getRoleId())).mapToLong(SysUserRoleEntity::getRoleId).toArray();
        ///获取用户可以访问的所有部门
        long[] depts = getAllRoleDept().stream().filter(rd -> ArrayUtil.contains(roles, rd.getRoleId())).mapToLong(SysRoleDeptEntity::getDeptId).toArray();

        if (ArrayUtil.isEmpty(depts)) {
            return ArrayUtil.unWrap(userId);
        }
        //获取部门下的所有用户id

        return getAllUser().stream().filter(u -> ArrayUtil.contains(depts, u.getDeptId())).mapToLong(SysUserEntity::getUserId).toArray();
    }

    /**
     * 获取所有的菜单
     */
    public List<SysMenuEntity> getAllMenu() {
        List<SysMenuEntity> list = (List<SysMenuEntity>) cache.get(MENU_KEY);
        if (Objects.isNull(list)) {
            list = sysMenuService.list(new QueryWrapper<>());
            List<SysMenuEntity> temp = list;
            list.forEach(menu -> {
                String parentName = temp.stream().filter(t -> t.getMenuId().equals(menu.getParentId())).findFirst().orElse(new SysMenuEntity()).getName();
                menu.setParentName(StrUtil.isNotBlank(parentName) ? parentName : "顶级菜单");
            });
            cache.put(MENU_KEY, list.stream().sorted(Comparator.comparing(SysMenuEntity::getOrderNum)).collect(Collectors.toList()));
        }
        return list;
    }

    /**
     * 获取所有非按钮菜单
     */
    public List<SysMenuEntity> getMenuNotButten() {
        return getAllMenu().stream().filter(m -> !m.getType().equals(2)).collect(Collectors.toList());
    }

    /**
     * 根据菜单id获取菜单
     *
     * @param id 菜单id
     */
    public SysMenuEntity getMenuById(Long id) {
        return getAllMenu().stream().filter(m -> m.getMenuId().equals(id)).findFirst().orElse(new SysMenuEntity());
    }

    /**
     * 根据角色id获取 所有菜单
     *
     * @param roleId 角色id
     */
    public List<SysMenuEntity> getMenuByRoleId(long roleId) {
        List<Long> menuIds = getAllRoleMenu().stream().filter(rm -> rm.getRoleId().equals(roleId)).map(SysRoleMenuEntity::getMenuId).collect(Collectors.toList());

        return getAllMenu().stream().filter(m -> menuIds.contains(m.getMenuId())).collect(Collectors.toList());
    }

    /**
     * 获取用户的可访问菜单
     *
     * @param userId 用户id
     */
    public List<SysMenuEntity> getMenuByUserId(long userId) {
        List<Long> roles = getRoleIdByUserId(userId);

        List<Long> menus = getAllRoleMenu().stream().filter(rm -> roles.contains(rm.getRoleId())).map(SysRoleMenuEntity::getMenuId).collect(Collectors.toList());

        return getAllMenu().stream().filter(m -> menus.contains(m.getMenuId())).collect(Collectors.toList());
    }

    /**
     * 获取所有的角色菜单关联
     */
    public List<SysRoleMenuEntity> getAllRoleMenu() {
        List<SysRoleMenuEntity> list = (List<SysRoleMenuEntity>) cache.get(ROLE_MENU_KEY);
        if (Objects.isNull(list)) {
            list = sysRoleMenuService.list(new QueryWrapper<>());
            cache.put(ROLE_MENU_KEY, list);
        }
        return list;
    }

    /**
     * 清空缓存
     */
    public void clear() {
        cache.clear();
    }
}
