package org.three.dms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.three.dms.entity.TRole;

import java.util.List;

@Mapper
public interface TRoleMapper extends BaseMapper<TRole> {
    @Select("select role from t_role")
    List<String> get_role();
}
