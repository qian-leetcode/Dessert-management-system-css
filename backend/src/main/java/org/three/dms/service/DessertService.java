package org.three.dms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.three.dms.entity.Dessert;

import java.util.List;

public interface DessertService extends IService<Dessert> {
    List<Dessert> selectDessertWithCategory();
}
