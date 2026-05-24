package org.three.dms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.three.dms.entity.Dessert;

import java.time.LocalDate;
import java.util.List;

public interface DessertService extends IService<Dessert> {
    List<Dessert> selectDessertWithCategory();

    // 新增
    int insert_dessert(
            String name,
            String photoUrl,
            Double price,
            String description,
            LocalDate releaseDate,
            Integer catId,
            Integer dessertStatus,
            Integer dessertNumber
    );

    // 删除
    int delete_dessert(Integer id);

    // 修改
    int update_dessert(
            Integer id,
            String name,
            String photoUrl,
            Double price,
            String description,
            LocalDate releaseDate,
            Integer catId,
            Integer dessertStatus,
            Integer dessertNumber
    );
}
