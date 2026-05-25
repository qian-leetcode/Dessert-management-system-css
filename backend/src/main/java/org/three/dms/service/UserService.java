package org.three.dms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.three.dms.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService extends IService<User> {
    List<String> get_username ();

    int insert_user(User user);

    String get_password (String userName);

    // 删除
    int delete_user(Integer id);

    // 修改
    int update_user(Integer id, String name, String gender, String phone, String username, String password, String position, LocalDate hireDate, String shift);
}
