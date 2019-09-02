package com.dengchao.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dengchao.demo.realm.CustomRealm;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomRealmApplicationTests {


    @Test
    public void contextLoads() {
        CustomRealm realm = new CustomRealm();

        DefaultSecurityManager manager = new DefaultSecurityManager();
        manager.setRealm(realm);

        /**
        * 加密
        */
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        realm.setCredentialsMatcher(matcher);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("dengchao","123");
        //3.认证
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        subject.checkRole("admin");

        subject.checkPermissions("user:delete","user:add");


    }

}
