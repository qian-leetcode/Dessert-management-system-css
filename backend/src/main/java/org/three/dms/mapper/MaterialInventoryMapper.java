package org.three.dms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.three.dms.entity.MaterialInventory;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface MaterialInventoryMapper extends BaseMapper<MaterialInventory> {
    @Select("SELECT q.inventory_id , q.material_id , q.current_inventory_level,q.safety_stock_quantity ,q.last_purchase_time ," +
            "m.material_name " +
            "from material_inventory q " +
            "LEFT JOIN material_information m on m.material_id = q.material_id")
    List<MaterialInventory> selectByMaterial();

    // 新增库存记录
    @Insert("INSERT INTO material_inventory(material_id, current_inventory_level, safety_stock_quantity, last_purchase_time) " +
            "VALUES(#{materialId}, #{currentInventoryLevel}, #{safetyStockQuantity}, #{lastPurchaseTime})")
    int insertInventory(
            @Param("materialId") Integer materialId,
            @Param("currentInventoryLevel") Double currentInventoryLevel,
            @Param("safetyStockQuantity") Double safetyStockQuantity,
            @Param("lastPurchaseTime") LocalDate lastPurchaseTime
    );

    // 删除库存记录（按主键inventory_id）
    @Delete("DELETE FROM material_inventory WHERE inventory_id = #{inventoryId}")
    int deleteInventory(@Param("inventoryId") Integer inventoryId);

    // 修改库存记录（不修改主键）
    @Update("UPDATE material_inventory SET " +
            "material_id = #{materialId}, current_inventory_level = #{currentInventoryLevel}, " +
            "safety_stock_quantity = #{safetyStockQuantity}, last_purchase_time = #{lastPurchaseTime} " +
            "WHERE inventory_id = #{inventoryId}")
    int updateInventory(
            @Param("inventoryId") Integer inventoryId,
            @Param("materialId") Integer materialId,
            @Param("currentInventoryLevel") Double currentInventoryLevel,
            @Param("safetyStockQuantity") Double safetyStockQuantity,
            @Param("lastPurchaseTime") LocalDate lastPurchaseTime
    );
}
