package org.three.dms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.three.dms.entity.MaterialInventory;
import org.three.dms.mapper.MaterialInventoryMapper;
import org.three.dms.service.MaterialInventoryService;

import java.time.LocalDate;
import java.util.List;


@Service
public class MaterialInventoryServiceImpl extends ServiceImpl<MaterialInventoryMapper , MaterialInventory> implements MaterialInventoryService {

    @Resource
    private MaterialInventoryMapper materialInventoryMapper;

    @Override
    public List<MaterialInventory> selectByMaterial(){
        return materialInventoryMapper.selectByMaterial();
    }

    @Override
    public int insertInventory(Integer materialId, Double currentInventoryLevel, Double safetyStockQuantity, LocalDate lastPurchaseTime) {
        return materialInventoryMapper.insertInventory(materialId, currentInventoryLevel, safetyStockQuantity, lastPurchaseTime);
    }

    @Override
    public int deleteInventory(Integer inventoryId) {
        return materialInventoryMapper.deleteInventory(inventoryId);
    }

    @Override
    public int updateInventory(Integer inventoryId, Integer materialId, Double currentInventoryLevel, Double safetyStockQuantity, LocalDate lastPurchaseTime) {
        return materialInventoryMapper.updateInventory(inventoryId, materialId, currentInventoryLevel, safetyStockQuantity, lastPurchaseTime);
    }
}
