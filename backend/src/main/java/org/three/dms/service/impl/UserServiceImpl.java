package org.three.dms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.three.dms.entity.User;
import org.three.dms.mapper.UserMapper;
import org.three.dms.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<String> get_username(){
        return userMapper.get_username();
    }

    @Override
    public int insert_user(User user){
        return userMapper.insert_user(user);
    }
}
