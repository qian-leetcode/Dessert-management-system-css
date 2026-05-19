package org.three.dms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.three.dms.entity.Dessert;
import org.three.dms.mapper.DessertMapper;
import org.three.dms.service.DessertService;

@Service
public class DessertServiceImpl extends ServiceImpl<DessertMapper , Dessert> implements DessertService {
}
