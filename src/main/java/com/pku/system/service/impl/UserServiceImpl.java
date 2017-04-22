package com.pku.system.service.impl;

import com.pku.system.dao.UserDao;
import com.pku.system.model.User;
import com.pku.system.service.RoleService;
import com.pku.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by jiangdongyu on 2017/4/13.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    RoleService roleService;

    @Override
    @Transactional
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    public User selectById(int id) {
        return userDao.selectById(id);
    }

    @Override
    public User selectByName(String username) {
        return userDao.selectByName(username);
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public Set<String> findRoles(String username) {
        return userDao.findRoles(username);
    }

    @Override
    public Set<String> findPermissions(String username) {
        User user = selectByName(username);
        if(user == null) {
            return Collections.EMPTY_SET;
        }
        return roleService.findPermissions(user.getR_id());
    }
}
