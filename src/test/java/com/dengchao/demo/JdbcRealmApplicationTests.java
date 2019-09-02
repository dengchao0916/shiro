package com.dengchao.demo;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcRealmApplicationTests {
    private DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/shiro?useUnicode=true&characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("admin");
    }

    @Test
    public void contextLoads() {
        JdbcRealm realm = new JdbcRealm();
        realm.setDataSource(dataSource);
        realm.setPermissionsLookupEnabled(true);

        String sql = "select password from test_user where username = ?";
        realm.setAuthenticationQuery(sql);

        //1.构造SecurityManager环境
        DefaultSecurityManager manager = new DefaultSecurityManager();
        manager.setRealm(realm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("dengchao123", "123");
        //3.认证
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated());

       /* subject.checkRole("admin");

        subject.checkRoles("admin","user");

        subject.checkPermission("user:delete");*/
    }

}
