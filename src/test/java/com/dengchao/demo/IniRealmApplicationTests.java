package com.dengchao.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IniRealmApplicationTests {
    private IniRealm realm = new IniRealm("classpath:user.ini");


    @Test
    public void contextLoads() {

        //1.构造SecurityManager环境
        DefaultSecurityManager manager = new DefaultSecurityManager();
        manager.setRealm(realm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("dengchao","123");
        //3.认证
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        subject.checkRole("admin");

        subject.checkPermission("user:delete");
        subject.checkPermission("user:update");




    }

}
