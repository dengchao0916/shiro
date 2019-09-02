package com.dengchao.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    private SimpleAccountRealm realm = new SimpleAccountRealm();

    @Before
    public void addUser(){
        realm.addAccount("dengchao","123","admin","user");
    }

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

        /*subject.logout();
        System.out.println("isAuthenticated: " + subject.isAuthenticated());*/
        subject.checkRoles("admin","user");


    }

}
