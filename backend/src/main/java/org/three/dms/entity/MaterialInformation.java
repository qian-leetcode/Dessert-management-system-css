package org.three.dms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("material_information")
public class MaterialInformation {

    @TableId(type = IdType.AUTO)
    private Integer material_id;

    @TableField("material_code")
    private String material_code;

    @TableField("material_name")
    private String material_name;

    @TableField("material_category")
    private String material_category;

    @TableField("material_specification")
    private String material_specification;

    @TableField("material_unit")
    private String material_unit;

    @TableField("material_shelf_life_days")
    private Integer material_shelf_life_days;

    @TableField("material_storage_condition")
    private String material_storage_condition;

    @TableField("material_remark")
    private String material_remark;

}