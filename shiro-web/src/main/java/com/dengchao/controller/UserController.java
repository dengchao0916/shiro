package com.dengchao.controller;

import com.dengchao.VO.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : dengchao
 * @description :
 * @create : 2019/7/14
 */
@Controller
public class UserController {

    @RequestMapping(value = "/subLogin", method = RequestMethod.POST)
    @ResponseBody
    public String subLogin(User user) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            token.setRememberMe(user.isRememberMe());
            subject.login(token);
        } catch (AuthenticationException e) {
            return e.getMessage();
        }

        if (subject.hasRole("admin")) {
            return "admin";
        }
        return "failed";
    }

    // @RequiresRoles("admin")
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    @ResponseBody
    public String role() {
        return "role";
    }

    // @RequiresRoles("admin1")
    @RequestMapping(value = "/role1", method = RequestMethod.GET)
    @ResponseBody
    public String role1() {
        return "role1";
    }

    @RequestMapping(value = "/perms", method = RequestMethod.GET)
    @ResponseBody
    public String perms() {
        return "perms";
    }

    @RequestMapping(value = "/perms1", method = RequestMethod.GET)
    @ResponseBody
    public String perms1() {
        return "perms1";
    }
}
