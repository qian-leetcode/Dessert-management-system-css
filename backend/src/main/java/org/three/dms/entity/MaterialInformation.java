package org.three.dms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("material_information")
public class MaterialInformation {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String material_code;
    private String material_name;
    private String material_category;
    private String material_specification;
    private String material_unit;
    private String material_shelf_life_days;
    private String material_storage_condition;
    private String material_remark;
}
