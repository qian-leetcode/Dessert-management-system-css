package org.three.dms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.three.dms.entity.Dessert;

import java.util.List;

@Mapper
public interface DessertMapper extends BaseMapper<Dessert> {

    @Select("SELECT d.id, d.name , d.photo_url, d.price, d.description, d.release_date, " +
            "       c.name as dessert_category " +
            "FROM dessert d " +
            "LEFT JOIN category c ON d.cat_id = c.id")
    List<Dessert> selectDessertWithCategory();
}
