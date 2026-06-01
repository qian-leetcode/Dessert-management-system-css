package org.three.dms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.three.dms.entity.PurchaseRecord;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface PurchaseRecordMapper extends BaseMapper<PurchaseRecord> {
    @Select("SELECT " +
            "p.purchase_id, p.purchase_order_number, p.purchase_date, p.material_id, m.material_name as material_name, " +
            "p.purchase_quantity, p.purchase_price, p.purchase_amount, p.supplier_name, " +
            "p.production_batch, p.production_date, p.payment_status, u.name as procuring_name , " +
            "p.procuring_entity, p.remark, p.create_time ,p.user_id " +
            "FROM purchase_record p " +
            "LEFT JOIN material_information m  on m.material_id = p.material_id " +
            "LEFT JOIN t_user u on p.user_id = u.id")
//    @Results({
//            @Result(property = "material_name", column = "material_name"),
//            @Result(property = "procuring_name", column = "procuring_name")
//    })
    List<PurchaseRecord> findAllPurchaseRecords();

    // 新增采购记录（create_time 数据库自动赋值，不用传）
    @Insert("INSERT INTO purchase_record(" +
            "purchase_order_number, purchase_date, material_id, purchase_quantity, purchase_price, purchase_amount, " +
            "supplier_name, production_batch, production_date, payment_status, user_id, procuring_entity, remark, create_time) " +
            "VALUES(#{purchaseOrderNumber}, #{purchaseDate}, #{materialId}, #{purchaseQuantity}, #{purchasePrice}, #{purchaseAmount}, " +
            "#{supplierName}, #{productionBatch}, #{productionDate}, #{paymentStatus}, #{userId}, #{procuringEntity}, #{remark}, #{createTime})")
    int insertPurchaseRecord(
            @Param("purchaseOrderNumber") String purchaseOrderNumber,
            @Param("purchaseDate") LocalDate purchaseDate,
            @Param("materialId") Integer materialId,
            @Param("purchaseQuantity") Double purchaseQuantity,
            @Param("purchasePrice") Double purchasePrice,
            @Param("purchaseAmount") Double purchaseAmount,
            @Param("supplierName") String supplierName,
            @Param("productionBatch") String productionBatch,
            @Param("productionDate") LocalDate productionDate,
            @Param("paymentStatus") Integer paymentStatus,
            @Param("userId") Integer userId,
            @Param("procuringEntity") String procuringEntity,
            @Param("remark") String remark,
            @Param("createTime") LocalDate createTime
    );

    // 删除采购记录（按主键purchase_id）
    @Delete("DELETE FROM purchase_record WHERE purchase_id = #{purchaseId}")
    int deletePurchaseRecord(@Param("purchaseId") Integer purchaseId);

    // 修改采购记录（不修改主键、create_time）
    @Update("UPDATE purchase_record SET " +
            "purchase_order_number = #{purchaseOrderNumber}, purchase_date = #{purchaseDate}, material_id = #{materialId}, " +
            "purchase_quantity = #{purchaseQuantity}, purchase_price = #{purchasePrice}, purchase_amount = #{purchaseAmount}, " +
            "supplier_name = #{supplierName}, production_batch = #{productionBatch}, production_date = #{productionDate}, " +
            "payment_status = #{paymentStatus}, user_id = #{userId}, remark = #{remark} " +
            "WHERE purchase_id = #{purchaseId}")
    int updatePurchaseRecord(
            @Param("purchaseId") Integer purchaseId,
            @Param("purchaseOrderNumber") String purchaseOrderNumber,
            @Param("purchaseDate") LocalDate purchaseDate,
            @Param("materialId") Integer materialId,
            @Param("purchaseQuantity") Double purchaseQuantity,
            @Param("purchasePrice") Double purchasePrice,
            @Param("purchaseAmount") Double purchaseAmount,
            @Param("supplierName") String supplierName,
            @Param("productionBatch") String productionBatch,
            @Param("productionDate") LocalDate productionDate,
            @Param("paymentStatus") Integer paymentStatus,
            @Param("userId") Integer userId,
            @Param("remark") String remark
    );

}
