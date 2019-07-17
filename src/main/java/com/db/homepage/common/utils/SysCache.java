package com.db.homepage.common.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.db.homepage.module.sys.entity.*;
import com.db.homepage.module.sys.service.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 字典工具类
 *
 * @author 大兵
 * @date 2018-07-17 16:07
 **/
@Component
@SuppressWarnings("unchecked")
@Slf4j
@Order
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
    private static Cache<String, Object> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .maximumSize(1000)
            .initialCapacity(128).build();
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
        try {
            return (List<SysDictEntity>) cache.get(DICT_KEY, () -> sysDictService.lambdaQuery().list());
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return Lists.newArrayList();
    }

    /**
     * 根据id获取字典
     *
     * @param id 字典id
     * @return 字典对象
     */
    public SysDictEntity getDictById(Long id) {
        try {
            return (SysDictEntity) cache.get(DICT_KEY + "_by_id_" + id, () ->
                    getAllDict().stream().filter(d -> d.getId().equals(id)).findFirst().orElse(null));
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取该类型的最大排序
     *
     * @param type 类型
     * @return 排序
     */
    public Integer getMaxOrderNum(String type) {
        int l = 0;
        try {
            l = (int) cache.get(DICT_KEY + "_max_order_num" + type, () -> getAllDict().stream().filter(d -> d.getType().equals(type))
                    .map(SysDictEntity::getOrderNum).mapToInt(i -> i).max().orElse(0));
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
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
        try {
            return (List<SysUserEntity>) cache.get(USER_KEY, () -> sysUserService.lambdaQuery().list());
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return Lists.newArrayList();
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
        try {
            return (SysUserEntity) cache.get(USER_KEY + "id", () ->
                    getAllUser().stream().filter(user -> id.equals(user.getUserId())).findFirst().orElse(null));
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户
     */
    public SysUserEntity getUserByUsername(String username) {
        if (Objects.isNull(username)) {
            return null;
        }
        try {
            return (SysUserEntity) cache.get(USER_KEY + "username", () ->
                    getAllUser().stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null));
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据机构id获取所有用户
     *
     * @param deptId 机构id
     */
    public List<SysUserEntity> getUserByDeptId(Long deptId) {
        if (Objects.isNull(deptId)) {
            return Lists.newArrayList();
        }
        try {
            return (List<SysUserEntity>) cache.get(DEPT_KEY + "id", () ->
                    getAllUser().stream().filter(user -> user.getDeptId().equals(deptId)).collect(Collectors.toList()));
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return Lists.newArrayList();
    }

    /**
     * 获取所有的角色
     */
    public List<SysRoleEntity> getAllRole() {

        try {
            return (List<SysRoleEntity>) cache.get(ROLE_KEY, () -> {
                List<SysRoleEntity> roleEntityList = sysRoleService.lambdaQuery().list();
                roleEntityList.forEach(role -> {
                    role.setDeptName(getDeptById(role.getDeptId()).getName());
                    role.setMenuIdList(getAllRoleMenu().stream().filter(rm -> rm.getRoleId().equals(role.getRoleId()))
                            .map(SysRoleMenuEntity::getMenuId).collect(Collectors.toList()));
                    role.setDeptIdList(getAllRoleDept().stream().filter(rd -> rd.getRoleId().equals(role.getRoleId()))
                            .map(SysRoleDeptEntity::getDeptId).collect(Collectors.toList()));
                });
                return roleEntityList;
            });
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return Lists.newArrayList();
    }

    /**
     * 根据角色id获取角色
     *
     * @param id 角色id
     * @return 角色对象
     */
    public SysRoleEntity getRoleById(Long id) {
        try {
            return (SysRoleEntity) cache.get(ROLE_KEY + "id", () ->
                    getAllRole().stream().filter(r -> r.getRoleId().equals(id)).findFirst().orElse(null));
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取用户的所有角色
     *
     * @param userId 用户id
     * @return 角色列表
     */
    public List<SysRoleEntity> getRoleByUserId(Long userId) {
        List<SysRoleEntity> list = new ArrayList<>();
        try {
            List<SysRoleEntity> temp = (List<SysRoleEntity>) cache.get("getRoleByUserId" + userId, () ->
                    getAllUserRole().stream().filter(ur -> ur.getUserId().equals(userId))
                            .collect(Collectors.toList()));
            temp.forEach(ur -> list.add(getRoleById(ur.getRoleId())));
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * 获取所有的机构
     */
    public List<SysDeptEntity> getAllDept() {
        try {
            return (List<SysDeptEntity>) cache.get(DEPT_KEY, () -> {
                List<SysDeptEntity> list = sysDeptService.lambdaQuery().list();
                list.forEach(d -> {
                    String pName = list.stream().filter(t -> t.getDeptId().equals(d.getParentId()))
                            .findFirst().orElse(new SysDeptEntity()).getName();
                    d.setParentName(pName);
                    if (d.getDeptId().equals(1L)) {
                        d.setParentName("一级部门");
                    }
                });
                return list;
            });
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }

        return Lists.newArrayList();
    }

    /**
     * 获取所有的角色机构关联
     */
    public List<SysRoleDeptEntity> getAllRoleDept() {
        try {
            return (List<SysRoleDeptEntity>) cache.get(ROLE_DEPT_KEY, () ->
                    sysRoleDeptService.lambdaQuery().list());
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return Lists.newArrayList();
    }

    /**
     * 根据id获取机构
     *
     * @param id 机构id
     * @return 机构
     */
    public SysDeptEntity getDeptById(Long id) {
        try {
            cache.get("getDeptById" + id, () ->
                    getAllDept().stream().filter(d -> d.getDeptId().equals(id)).findFirst().orElse(null));
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return null;
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
        try {
            return (List<SysUserRoleEntity>) cache.get(USER_ROLE_KEY, () -> sysUserRoleService.lambdaQuery().list());
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return Lists.newArrayList();
    }

    /**
     * 获取用户的所有的角色id
     *
     * @param userId 用户id
     * @return 角色id集合
     */
    public List<Long> getRoleIdByUserId(Long userId) {
        try {
            return (List<Long>) cache.get("getRoleIdByUserId" + userId, () ->
                    getAllUserRole().stream().filter(ur -> ur.getUserId().equals(userId))
                            .map(SysUserRoleEntity::getRoleId).collect(Collectors.toList()));
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return Lists.newArrayList();
    }

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户id
     */
    public List<String> getPermsByUserId(Long userId) {
        try {
            return (List<String>) cache.get("getPermsByUserId" + userId, () ->
                    getMenuByUserId(userId).stream().map(SysMenuEntity::getPerms).collect(Collectors.toList()));
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return Lists.newArrayList();
    }

    /**
     * 获取用户可以访问数据的用户id
     *
     * @param userId 用户id
     */
    public long[] getDeptIdByUserId(Long userId) {
        try {
            return (long[]) cache.get("getDeptIdByUserId" + userId, () -> {
                //获取用户所有的角色
                long[] roles = getAllUserRole().stream().filter(ur -> userId.equals(ur.getRoleId())).mapToLong(SysUserRoleEntity::getRoleId).toArray();
                ///获取用户可以访问的所有部门
                long[] depts = getAllRoleDept().stream().filter(rd -> ArrayUtil.contains(roles, rd.getRoleId())).mapToLong(SysRoleDeptEntity::getDeptId).toArray();

                if (ArrayUtil.isEmpty(depts)) {
                    return ArrayUtil.unWrap(userId);
                }

                return getAllUser().stream().filter(u ->
                        ArrayUtil.contains(depts, u.getDeptId())).mapToLong(SysUserEntity::getUserId).toArray();
            });
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }

        return new long[0];
    }

    /**
     * 获取所有的菜单
     */
    public List<SysMenuEntity> getAllMenu() {
        try {
            return (List<SysMenuEntity>) cache.get(MENU_KEY, () -> {
                List<SysMenuEntity> list = sysMenuService.lambdaQuery().orderByAsc(SysMenuEntity::getOrderNum).list();
                list.forEach(menu -> {
                    String parentName = list.stream().filter(t -> t.getMenuId().equals(menu.getParentId())).findFirst().orElse(new SysMenuEntity()).getName();
                    menu.setParentName(StrUtil.isNotBlank(parentName) ? parentName : "顶级菜单");
                });
                return list;
            });
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return Lists.newArrayList();
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
        try {
            return (List<SysRoleMenuEntity>) cache.get(ROLE_MENU_KEY, () -> sysRoleMenuService.lambdaQuery().list());
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return Lists.newArrayList();
    }

    /**
     * 清空缓存
     */
    public void clear() {
        cache.cleanUp();
    }
}
