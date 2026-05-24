package org.three.dms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.three.dms.entity.Category;

import java.util.List;


@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    // 获取分类名称
    @Select("select name from category")
    List<String> get_category_name();

    // 新增分类
    @Insert("INSERT INTO category (name , description) values (#{name}, #{description})")
    int insert_category(@Param("name") String name, @Param("description") String description);

    //删除分类
    @Delete("Delete from category where id = #{id}")
    int delete_category(@Param("id") int id);

    // 修改分类
    @Update("update category set name = #{name} , description = #{description} where id = #{id}")
    int update_category(@Param("name") String name, @Param("description") String description, @Param("id") int id);
}
