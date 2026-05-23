package org.three.dms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.three.dms.entity.Category;

import java.util.List;


@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    @Select("select name from category")
    List<String> get_category_name();
}
