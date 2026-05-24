package org.three.dms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.three.dms.entity.User;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select username from t_user")
    List<String> get_username();

    @Insert("INSERT INTO t_user(name, gender, phone, username, password, position, hire_date, shift) " +
            "VALUES(#{name}, #{gender}, #{phone}, #{username}, #{password}, #{position}, #{hire_date}, #{shift})")
    int insert_user(User user);

    @Select("select password from t_user where username = #{userName}")
    String get_password(@Param("userName") String userName);



    // 删除用户（按ID）
    @Delete("DELETE FROM t_user WHERE id = #{id}")
    int delete_user(@Param("id") Integer id);

    // 修改用户（不修改主键）
    @Update("UPDATE t_user SET name=#{name}, gender=#{gender}, phone=#{phone}, username=#{username}, " +
            "password=#{password}, position=#{position}, active=#{active}, hire_date=#{hireDate}, shift=#{shift} WHERE id=#{id}")
    int update_user(
            @Param("id") Integer id,
            @Param("name") String name,
            @Param("gender") String gender,
            @Param("phone") String phone,
            @Param("username") String username,
            @Param("password") String password,
            @Param("position") String position,
            @Param("active") Integer active,
            @Param("hireDate") LocalDate hireDate,
            @Param("shift") String shift
    );
}
