package com.ring.ringblogbackend.controller;

import com.ring.ringblogbackend.domain.Result;
import com.ring.ringblogbackend.domain.User;
import com.ring.ringblogbackend.serivce.UserService;
import com.ring.ringblogbackend.utils.Encrypted;
import com.ring.ringblogbackend.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 注册
     *
     * @return Result
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        // 查询是否已存在用户
        boolean existUser = userService.findUserByAccount(user.getAccount());

        if (existUser) {
            return Result.error("注册失败,已存在的用户");
        } else {
            // 加密密码并注册
            user.setPassword(Encrypted.encryptedPwd(user.getPassword()));
            userService.addUser(user);
            return Result.success(new Token().genToken(user.toString()));
        }
    }

    /**
     * 登录
     *
     * @return Result
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        //加密密码
        user.setPassword(Encrypted.encryptedPwd(user.getPassword()));
        //登录验证
        boolean existUser = userService.login(user);

        if (existUser) {
            return Result.success(new Token().genToken(user.toString()));
        } else {
            return Result.error("登录失败");
        }
    }

    /**
     * Token验证
     *
     * @return Result
     */
    @PostMapping("/token")
    public Result checkToken(@RequestBody User user) {
        String tokenMsg = new Token().parseToken(user.getToken());
        if (!Objects.equals(tokenMsg, "Token已经过期")) {
            return Result.success("OK");
        } else {
            return Result.error("Token已经过期");
        }
    }

    @GetMapping
    public Result getMsg(@RequestParam String account) {
        return Result.success(userService.findUser(account));
    }

    @PutMapping
    public Result updateMsg(@RequestParam("id") String id, @RequestParam("account") String account, @RequestParam("description") String description, @RequestParam("avatar") MultipartFile avatar) throws IOException {
        User tagrget_user = userService.findById(id);
        tagrget_user.setAccount(account);
        tagrget_user.setDescription(description);
        if (!avatar.isEmpty()) {
            tagrget_user.setAvatar(avatar.getBytes());
        }
        userService.updateMsg(tagrget_user);
        return Result.success("成功修改用户信息");
    }

    @GetMapping("/img/{id}")
    public byte[] findImgById(@PathVariable String id) {
        return userService.findImgById(id);
    }

    @PutMapping("/repwd")
    public Result updatePwdByAccount(@RequestParam("account") String account, @RequestParam("old_password") String oldPwd, @RequestParam("new_password") String newPwd) {
        boolean success = userService.updatePwdByAccount(account, oldPwd, newPwd);
        if (success) {
            return Result.success("成功修改密码");
        }
        return Result.error("原密码输入错误");
    }

}
