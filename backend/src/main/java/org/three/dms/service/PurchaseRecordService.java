package org.three.dms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.three.dms.entity.PurchaseRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseRecordService extends IService<PurchaseRecord> {
    public List<PurchaseRecord> findAllPurchaseRecords();

    // 新增
    int insertPurchaseRecord(String purchaseOrderNumber, LocalDate purchaseDate, Integer materialId, Double purchaseQuantity, Double purchasePrice, Double purchaseAmount,
                             String supplierName, String productionBatch, LocalDate productionDate, Integer paymentStatus, Integer userId, String procuringEntity, String remark, LocalDate createTime);

    // 删除
    int deletePurchaseRecord(Integer purchaseId);

    // 修改
    int updatePurchaseRecord(Integer purchaseId, String purchaseOrderNumber, LocalDate purchaseDate, Integer materialId, Double purchaseQuantity, Double purchasePrice, Double purchaseAmount,
                             String supplierName, String productionBatch, LocalDate productionDate, Integer paymentStatus, Integer userId,  String remark);
}
