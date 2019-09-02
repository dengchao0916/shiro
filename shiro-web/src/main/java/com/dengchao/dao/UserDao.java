package com.dengchao.dao;

import com.dengchao.VO.User;

import java.util.List;

/**
 * @author : dengchao
 * @description :
 * @create : 2019/7/14
 */
public interface UserDao {
    User getUserByUsername(String username);

    List<String> getRolesByUsername(String username);
}
