package com.db.homepage.module.sys.controller;


import com.db.homepage.common.utils.Result;
import com.db.homepage.module.sys.shiro.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录相关
 *
 * @author 117
 */
@Controller
public class SysLoginController {

    /**
     * 登录
     */
    @ResponseBody
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    public Result login(String username, String password, String captcha, Boolean rememberMe) {

        try {
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe == null ? false : rememberMe);
            subject.login(token);
        } catch (UnknownAccountException e) {
            return Result.getFailure(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return Result.getFailure("账号或密码不正确");
        } catch (LockedAccountException e) {
            return Result.getFailure("账号已被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            return Result.getFailure("账户验证失败");
        }

        return Result.getSuccess();
    }

    /**
     * 退出
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
        ShiroUtils.logout();
        return "redirect:login.html";
    }
}
