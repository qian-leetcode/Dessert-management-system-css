package org.three.dms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("dessert")
public class Dessert {

    @TableId(type = IdType.AUTO)
    private Integer id;

    // 甜品分类
    @TableField(exist = false)
//    @JsonProperty("dessert_category")
    private String dessert_category;

    private String name;
    private String photo_url;
    private double price;
    private String description;
    private LocalDate release_date;
    private Integer dessert_status;
    private Integer dessert_number;
}
