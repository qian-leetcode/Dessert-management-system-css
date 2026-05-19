package org.three.dms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("dessert")
public class Dessert {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String dessertName;
    private String photo_url;
    private String price;
    private String description;
    private LocalDate release_date;
}
