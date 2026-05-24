package org.three.dms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@TableName("material_inventory")
public class MaterialInventory {
    @TableId(type = IdType.AUTO)
    private Integer inventory_id;

    private Integer material_id;
    @TableField(exist = false)
    private String material_name;
    private double current_inventory_level;
    private double safety_stock_quantity;
    private LocalDate last_purchase_time;


}
