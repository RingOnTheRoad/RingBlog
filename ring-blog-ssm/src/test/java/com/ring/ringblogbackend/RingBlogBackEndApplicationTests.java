package com.ring.ringblogbackend;

import com.ring.ringblogbackend.dao.UserDao;
import com.ring.ringblogbackend.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RingBlogBackEndApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
        User user = new User();
        user.setId(1L);
        user.setAccount("admin");
        user.setPassword("admin");
        user.setDescription("test");
        user.setAvatar("");

        userDao.insert(user);
    }

}
