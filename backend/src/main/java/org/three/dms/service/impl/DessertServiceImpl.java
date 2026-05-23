package org.three.dms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.three.dms.entity.Dessert;
import org.three.dms.mapper.DessertMapper;
import org.three.dms.service.DessertService;

import java.util.List;

@Service
public class DessertServiceImpl extends ServiceImpl<DessertMapper , Dessert> implements DessertService {
    @Autowired
    private DessertMapper dessertMapper;

    @Override
    public List<Dessert> selectDessertWithCategory(){
        return dessertMapper.selectDessertWithCategory();
    }
}
