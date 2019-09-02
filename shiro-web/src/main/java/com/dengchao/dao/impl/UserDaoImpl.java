package com.dengchao.dao.impl;

import com.dengchao.VO.User;
import com.dengchao.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author : dengchao
 * @description :
 * @create : 2019/7/14
 */
@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUserByUsername(String username) {
        String sql = "select username,password from users where username = ?";
        List<User> list = jdbcTemplate.query(sql, new String[] {username}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list.get(0);
    }

    @Override
    public List<String> getRolesByUsername(String username) {
        String sql = "select role_name from user_roles where username = ?";
        return jdbcTemplate.query(sql, new String[] {username}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {

                return resultSet.getString("role_name");
            }
        });
    }
}
