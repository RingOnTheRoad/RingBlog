package com.ring.ringblogbackend.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ring.ringblogbackend.dao.UserDao;
import com.ring.ringblogbackend.domain.User;
import com.ring.ringblogbackend.serivce.UserService;
import com.ring.ringblogbackend.utils.Encrypted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean findUserByAccount(String account) {
        LambdaQueryWrapper<User> lq = Wrappers.lambdaQuery();
        lq.eq(User::getAccount, account);
        User existUser = userDao.selectOne(lq);
        return existUser != null;
    }

    @Override
    public void addUser(User user) {
        userDao.insert(user);
    }

    @Override
    public boolean login(User user) {
        LambdaQueryWrapper<User> lq = Wrappers.lambdaQuery();
        lq.eq(User::getAccount, user.getAccount()).eq(User::getPassword, user.getPassword());
        User existUser = userDao.selectOne(lq);
        return existUser != null;
    }

    @Override
    public User findUser(String account) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, account);
        return userDao.selectOne(wrapper);
    }

    @Override
    public void updateMsg(User user) {
        userDao.updateById(user);
    }

    @Override
    public User findById(String id) {
        return userDao.selectById(id);
    }

    @Override
    public byte[] findImgById(String id) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, id);
        User user = userDao.selectOne(wrapper);
        return user.getAvatar();
    }

    @Override
    public boolean updatePwdByAccount(String account, String oldPwd, String newPwd) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, account);
        User user = userDao.selectOne(wrapper);
        if (!Objects.equals(user.getPassword(), Encrypted.encryptedPwd(oldPwd))) {
            return false;
        }
        user.setPassword(Encrypted.encryptedPwd(newPwd));
        userDao.update(user, wrapper);
        return true;
    }
}
