package org.three.dms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@TableName("material_inventory")
public class MaterialInventory {
    @TableId(type = IdType.AUTO)
    private Integer inventory_id;

    private Integer material_id;
    private double currentInventoryLevel;
    private double safetyStockQuantity;
    private LocalDate lastPurchaseTime;
}
