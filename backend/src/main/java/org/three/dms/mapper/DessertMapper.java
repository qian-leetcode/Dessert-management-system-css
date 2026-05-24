package org.three.dms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.three.dms.entity.Dessert;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DessertMapper extends BaseMapper<Dessert> {

    @Select("SELECT d.id, d.name , d.photo_url, d.price, d.description, d.release_date, " +
            "       c.name as dessert_category " +
            "FROM dessert d " +
            "LEFT JOIN category c ON d.cat_id = c.id")
    List<Dessert> selectDessertWithCategory();

    // 新增
    @Insert("INSERT INTO dessert(name,photo_url,price,description,release_date,cat_id,dessert_status,dessert_number) " +
            "VALUES(#{name},#{photoUrl},#{price},#{description},#{releaseDate},#{catId},#{dessertStatus},#{dessertNumber})")
    int insertDessert(
            @Param("name") String name,
            @Param("photoUrl") String photoUrl,
            @Param("price") Double price,
            @Param("description") String description,
            @Param("releaseDate") LocalDate releaseDate,
            @Param("catId") Integer catId,
            @Param("dessertStatus") Integer dessertStatus,
            @Param("dessertNumber") Integer dessertNumber
    );

    // 删除
    @Delete("DELETE FROM dessert WHERE id=#{id}")
    int deleteDessert(@Param("id") Integer id);

    // 修改
    @Update("UPDATE dessert SET name=#{name},photo_url=#{photoUrl},price=#{price},description=#{description}," +
            "release_date=#{releaseDate},cat_id=#{catId},dessert_status=#{dessertStatus},dessert_number=#{dessertNumber} WHERE id=#{id}")
    int updateDessert(
            @Param("id") Integer id,
            @Param("name") String name,
            @Param("photoUrl") String photoUrl,
            @Param("price") Double price,
            @Param("description") String description,
            @Param("releaseDate") LocalDate releaseDate,
            @Param("catId") Integer catId,
            @Param("dessertStatus") Integer dessertStatus,
            @Param("dessertNumber") Integer dessertNumber
    );
}
