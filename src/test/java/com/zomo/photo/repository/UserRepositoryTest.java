package com.zomo.photo.repository;

import com.zomo.photo.ApplicationTests;
import com.zomo.photo.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserRepositoryTest extends ApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void fun1(){
        User user=new User();
        user.setUsername("fanxining");
        user.setPassword("1990511");
        user.setName("范希宁11111111111111111111111111111111111111111111111111111111111111111111111111111111");
        user.setRoleId(1);
        User newUser=userRepository.save(user);
        Assert.assertNull(newUser);
    }

    @Test

    public void fun2(){
        String username="哈哈";
        Integer count=userRepository.countByUsername(username);
        System.out.println(count);
    }


}