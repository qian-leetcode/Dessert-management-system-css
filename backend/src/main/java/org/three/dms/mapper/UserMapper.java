package org.three.dms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.three.dms.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
