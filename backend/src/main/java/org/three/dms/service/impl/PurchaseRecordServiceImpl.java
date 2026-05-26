package org.three.dms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.three.dms.entity.PurchaseRecord;
import org.three.dms.mapper.PurchaseRecordMapper;
import org.three.dms.service.PurchaseRecordService;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseRecordServiceImpl extends ServiceImpl<PurchaseRecordMapper, PurchaseRecord> implements PurchaseRecordService {
    @Autowired
    private PurchaseRecordMapper purchaseRecordMapper;

    @Override
    public List<PurchaseRecord> findAllPurchaseRecords(){
        return purchaseRecordMapper.findAllPurchaseRecords();
    }

    @Override
    public int insertPurchaseRecord(String purchaseOrderNumber, LocalDate purchaseDate, Integer materialId, Double purchaseQuantity, Double purchasePrice, Double purchaseAmount,
                                    String supplierName, String productionBatch, LocalDate productionDate, Integer paymentStatus, Integer userId,String remark ,LocalDate create_time) {
        return purchaseRecordMapper.insertPurchaseRecord(
                purchaseOrderNumber, purchaseDate, materialId, purchaseQuantity, purchasePrice, purchaseAmount,
                supplierName, productionBatch, productionDate, paymentStatus, userId, remark , create_time
        );
    }

    @Override
    public int deletePurchaseRecord(Integer purchaseId) {
        return purchaseRecordMapper.deletePurchaseRecord(purchaseId);
    }

    @Override
    public int updatePurchaseRecord(Integer purchaseId, String purchaseOrderNumber, LocalDate purchaseDate, Integer materialId, Double purchaseQuantity, Double purchasePrice, Double purchaseAmount,
                                    String supplierName, String productionBatch, LocalDate productionDate, Integer paymentStatus, Integer userId, String remark) {
        return purchaseRecordMapper.updatePurchaseRecord(
                purchaseId, purchaseOrderNumber, purchaseDate, materialId, purchaseQuantity, purchasePrice, purchaseAmount,
                supplierName, productionBatch, productionDate, paymentStatus, userId,  remark
        );
    }
}
