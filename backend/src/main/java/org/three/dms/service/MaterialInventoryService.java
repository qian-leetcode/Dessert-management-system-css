package org.three.dms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.three.dms.entity.MaterialInventory;

import java.time.LocalDate;
import java.util.List;

public interface MaterialInventoryService  extends IService<MaterialInventory> {
    List<MaterialInventory> selectByMaterial();

    // 新增
    int insertInventory(Integer materialId, Double currentInventoryLevel, Double safetyStockQuantity, LocalDate lastPurchaseTime);

    // 删除
    int deleteInventory(Integer inventoryId);

    // 修改
    int updateInventory(Integer inventoryId, Integer materialId, Double currentInventoryLevel, Double safetyStockQuantity, LocalDate lastPurchaseTime);
}
