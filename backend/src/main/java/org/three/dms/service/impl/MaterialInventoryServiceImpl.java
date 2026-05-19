package org.three.dms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.three.dms.entity.MaterialInventory;
import org.three.dms.mapper.MaterialInventoryMapper;
import org.three.dms.service.MaterialInventoryService;


@Service
public class MaterialInventoryServiceImpl extends ServiceImpl<MaterialInventoryMapper , MaterialInventory> implements MaterialInventoryService {
}
