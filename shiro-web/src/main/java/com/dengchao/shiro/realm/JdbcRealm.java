package com.dengchao.shiro.realm;

import com.dengchao.VO.User;
import com.dengchao.dao.UserDao;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description :
 * @Author : dengchao
 * @Create : 2019/7/13
 */
public class JdbcRealm extends AuthorizingRealm {

    @Autowired
    private UserDao dao;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String username = (String)principals.getPrimaryPrincipal();
        Set<String> roles = getRolesByUsername(username);
        Set<String> permission = getRolesByPermission(username);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(permission);
        authorizationInfo.setRoles(roles);

        return authorizationInfo;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        String password = getPasswordByUsername(username);

        if (StringUtils.isEmpty(password)) {
            return null;
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, "customRealm");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("happy"));
        return authenticationInfo;
    }

    /**
     * 模拟从数据库获取用户密码
     */
    private String getPasswordByUsername(String username) {
        User user = dao.getUserByUsername(username);
        return null != user ? user.getPassword() : null;
    }

    /**
     * 模拟从数据库获取角色
     */
    private Set<String> getRolesByUsername(String username) {
        System.out.println("从数据库获取角色");
         List<String> list = dao.getRolesByUsername(username);
        Set<String> set = new HashSet<>(list);
        return set;
    }

    /**
     * 模拟从数据库获取权限
     */
    private Set<String> getRolesByPermission(String username) {
        Set<String> set = new HashSet<>();
        set.add("user:delete");
        set.add("user:add");
        return set;
    }

    public static void main(String[] args) {
        Md5Hash hash = new Md5Hash("123", "happy");
        System.out.println(hash.toString());
    }
}