package com.ring.ringblogbackend.serivce;

import com.ring.ringblogbackend.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    /**
     * 根据用户名查询用户
     * @return boolean
     */
    public boolean findUserByAccount(String account);

    /**
     * 添加用户(注册)
     */
    public void addUser(User user);

    /**
     * 根据用户名密码查询用户(登录)
     */
    public boolean login(User user);

    /**
     * 根据用户名字查询信息
     */
    public User findUser(String account);

    /**
     * 更新用户信息
     */
    public void updateMsg(User user);

    /**
     * 根据id查询用户
     */
    public User findById(String id);

    /**
     * 根据ID查询头像
     */
    public byte[] findImgById(String id);

    /**
     * 根据用户Id更改账户密码
     */
    public boolean updatePwdByAccount(String account, String oldPwd, String newPwd);


}
