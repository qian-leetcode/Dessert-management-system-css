package org.three.dms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.three.dms.entity.User;
import org.three.dms.mapper.UserMapper;
import org.three.dms.service.UserService;

import java.time.LocalDate;
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

    @Override
    public String get_password(String username){
        return userMapper.get_password(username);
    }

    @Override
    public int delete_user(Integer id) {
        return userMapper.delete_user(id);
    }

    @Override
    public int update_user(Integer id, String name, String gender, String phone, String username, String password, String position, LocalDate hireDate, String shift) {
        return userMapper.update_user(id, name, gender, phone, username, password, position, hireDate, shift);
    }
}
