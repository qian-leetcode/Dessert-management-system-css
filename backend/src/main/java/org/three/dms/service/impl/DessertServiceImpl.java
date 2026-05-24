package org.three.dms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.three.dms.entity.Dessert;
import org.three.dms.mapper.DessertMapper;
import org.three.dms.service.DessertService;

import java.time.LocalDate;
import java.util.List;

@Service
public class DessertServiceImpl extends ServiceImpl<DessertMapper , Dessert> implements DessertService {
    @Autowired
    private DessertMapper dessertMapper;

    @Override
    public List<Dessert> selectDessertWithCategory(){
        return dessertMapper.selectDessertWithCategory();
    }

    // 1. 新增（参数全部填好）
    @Override
    public int insert_dessert(
            String name,
            String photoUrl,
            Double price,
            String description,
            LocalDate releaseDate,
            Integer catId,
            Integer dessertStatus,
            Integer dessertNumber
    ) {
        return dessertMapper.insertDessert(
                name,
                photoUrl,
                price,
                description,
                releaseDate,
                catId,
                dessertStatus,
                dessertNumber
        );
    }

    // 2. 删除
    @Override
    public int delete_dessert(Integer id) {
        return dessertMapper.deleteDessert(id);
    }

    // 3. 修改（参数全部填好）
    @Override
    public int update_dessert(
            Integer id,
            String name,
            String photoUrl,
            Double price,
            String description,
            LocalDate releaseDate,
            Integer catId,
            Integer dessertStatus,
            Integer dessertNumber
    ) {
        return dessertMapper.updateDessert(
                id,
                name,
                photoUrl,
                price,
                description,
                releaseDate,
                catId,
                dessertStatus,
                dessertNumber
        );
    }
}
