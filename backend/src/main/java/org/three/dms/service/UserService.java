package org.three.dms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.three.dms.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<String> get_username ();

    int insert_user(User user);
}
