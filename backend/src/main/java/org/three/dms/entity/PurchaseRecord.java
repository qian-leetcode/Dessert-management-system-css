package org.three.dms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@TableName("purchase_record")
public class PurchaseRecord {
    // 主键ID，自增
    @TableId(type = IdType.AUTO)
    private Integer purchase_id;

    // 采购订单编号
    private String purchase_order_number;

    // 采购日期
    private LocalDate purchase_date;

    // 物料ID（外键）
    private Integer material_id;

    // 原料名称
    @TableField(exist = false)
    private String material_name;

    // 采购人
    @TableField(exist = false)
    private String procuring_name;

    // 采购数量
    private double purchase_quantity;

    // 采购单价
    private double purchase_price;

    // 采购金额（=数量*单价）
    private double purchase_amount;

    // 供应商名称
    private String supplier_name;

    // 生产批次号
    private String production_batch;

    //生产日期
    private LocalDate production_date;

    //付款状态（0=未付款，1=已付款等）
    private Integer payment_status;

    // 操作人ID
    private Integer user_id;


    // 备注
    private String remark;

    // 创建时间（默认当前时间）
    private LocalDateTime create_time;
}