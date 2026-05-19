package org.three.dms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("purchase_record")
public class PurchaseRecord {
    // 主键ID，自增

    @TableId(type = IdType.AUTO)
    private Integer purchaseId;

    // 采购订单编号
    private String purchaseOrderNumber;

    // 采购日期

    private LocalDate purchaseDate;

    // 物料ID（外键）
    private Integer materialId;

    // 采购数量
    private BigDecimal purchaseQuantity;

    // 采购单价
    private BigDecimal purchasePrice;

    // 采购金额（=数量*单价）
    private BigDecimal purchaseAmount;

    // 供应商名称
    private String supplierName;

    // 生产批次号
    private String productionBatch;

    //生产日期
    private LocalDate productionDate;

    //付款状态（0=未付款，1=已付款等）
    private Integer paymentStatus;

    // 操作人ID
    private Integer userId;

    // 采购人
    private String procuringEntity;

    // 备注
    private String remark;

    // 创建时间（默认当前时间）
    private LocalDateTime createTime;
}
