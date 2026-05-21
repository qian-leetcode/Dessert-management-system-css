package org.three.dms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.three.dms.entity.User;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select username from t_user")
    List<String> get_username();

    @Insert("INSERT INTO t_user(name, gender, phone, username, password, position, active, hire_date, shift) " +
            "VALUES(#{name}, #{gender}, #{phone}, #{username}, #{password}, #{position}, #{active}, #{hire_date}, #{shift})")
    int insert_user(User user);
}
