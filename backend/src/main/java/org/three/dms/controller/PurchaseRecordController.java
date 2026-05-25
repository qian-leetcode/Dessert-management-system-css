package org.three.dms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.three.dms.common.PurchaseRecordDataInfo;
import org.three.dms.entity.PurchaseRecord;
import org.three.dms.service.PurchaseRecordService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Long.min;

@RestController
@RequestMapping("/api/purchase_record")
@CrossOrigin(origins = "*")
public class PurchaseRecordController {
    @Autowired
    private PurchaseRecordService purchaseRecordService;

    @GetMapping("/list")
    public List<PurchaseRecord> list(){
        return purchaseRecordService.list();
    }

    @PostMapping("/purchase_record_list")
    public PurchaseRecordDataInfo purchase_record_list(@RequestBody Map<String,Object> map){
        // 从 map 中获取分页参数和查询条件
        Integer page_num = (Integer) map.get("page_num");
        Integer page_size = (Integer) map.get("page_size");

        String purchase_order_number = (String) map.get("purchase_order_number");
        String purchase_date = (String) map.get("purchase_date");
        String material_id = (String) map.get("material_id");
        String supplier_name = (String) map.get("supplier_name");
        String production_batch = (String) map.get("production_batch");
        String production_date = (String) map.get("production_date");
        String payment_status = (String) map.get("payment_status");
        String remark = (String) map.get("remark");
        String create_time = (String) map.get("create_time");
        List<PurchaseRecord> begin_record_list = purchaseRecordService.findAllPurchaseRecords();
        List<PurchaseRecord> end_record_list = new ArrayList<>();
        for (PurchaseRecord record : begin_record_list) {
            if (
                    record.getPurchase_order_number().contains(purchase_order_number) &&
                            record.getPurchase_date().toString().contains(purchase_date) &&
                            (record.getMaterial_id() == null || record.getMaterial_id().toString().contains(material_id)) &&
                            record.getSupplier_name().contains(supplier_name) &&
                            record.getProduction_batch().contains(production_batch) &&
                            record.getProduction_date().toString().contains(production_date) &&
                            record.getPayment_status().toString().contains(payment_status) &&
                            record.getRemark().contains(remark) &&
                            record.getCreate_time().toString().contains(create_time)
            ) {
                end_record_list.add(record);
            }
        }
        PurchaseRecordDataInfo res = new PurchaseRecordDataInfo();
        res.setTotal(end_record_list.size());
        int total = end_record_list.size();
        for (int i = page_size *(page_num - 1); i < min((long) page_size *(page_num - 1) + page_size , total); i++) {
            res.add(end_record_list.get(i));
        }
        res.setCode(200);
        res.setMsg("success");
        return res;
    }

    // 新增采购记录
    @PostMapping("/add")
    public Map<String, Object> addPurchaseRecord(@RequestBody Map<String, String> map) {
        String purchaseOrderNumber = map.get("purchase_order_number");
        LocalDate purchaseDate = LocalDate.parse(map.get("purchase_date"));
        Integer materialId = Integer.parseInt(map.get("material_id"));
        Double purchaseQuantity = Double.parseDouble(map.get("purchase_quantity"));
        Double purchasePrice = Double.parseDouble(map.get("purchase_price"));
        Double purchaseAmount = Double.parseDouble(map.get("purchase_amount"));
        String supplierName = map.get("supplier_name");
        String productionBatch = map.get("production_batch");
        LocalDate productionDate = LocalDate.parse(map.get("production_date"));
        Integer paymentStatus = Integer.parseInt(map.get("payment_status"));
        Integer userId = Integer.parseInt(map.get("user_id"));
        String procuringEntity = map.get("procuring_entity");
        String remark = map.get("remark");

        int result = purchaseRecordService.insertPurchaseRecord(
                purchaseOrderNumber, purchaseDate, materialId, purchaseQuantity, purchasePrice, purchaseAmount,
                supplierName, productionBatch, productionDate, paymentStatus, userId, procuringEntity, remark
        );

        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("code", 200);
            response.put("msg", "新增采购记录成功");
        } else {
            response.put("code", 500);
            response.put("msg", "新增采购记录失败");
        }
        return response;
    }

    // 删除采购记录
    @PostMapping("/delete")
    public Map<String, Object> deletePurchaseRecord(Integer id) {
        int result = purchaseRecordService.deletePurchaseRecord(id);

        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("code", 200);
            response.put("msg", "删除采购记录成功");
        } else {
            response.put("code", 500);
            response.put("msg", "删除采购记录失败");
        }
        return response;
    }

    // 修改采购记录（不修改主键、创建时间）
    @PostMapping("/update")
    public Map<String, Object> updatePurchaseRecord(@RequestBody Map<String, String> map) {
        Integer purchaseId = Integer.parseInt(map.get("purchase_id"));
        String productionBatch = map.get("production_batch");
        LocalDate productionDate = LocalDate.parse(map.get("production_date"));
        LocalDate purchaseDate = LocalDate.parse(map.get("purchase_date"));
        Integer materialId = Integer.parseInt(map.get("material_id"));
        Double purchaseQuantity = Double.parseDouble(map.get("purchase_quantity"));
        Double purchasePrice = Double.parseDouble(map.get("purchase_price"));
        String supplierName = map.get("supplier_name");
        Double purchaseAmount = Double.parseDouble(map.get("purchase_amount"));
        String purchaseOrderNumber = map.get("purchase_order_number");
        Integer paymentStatus = Integer.parseInt(map.get("payment_status"));
        Integer userId = Integer.parseInt(map.get("user_id"));
        String procuringEntity = map.get("procuring_entity");
        String remark = map.get("remark");

        int result = purchaseRecordService.updatePurchaseRecord(
                purchaseId, purchaseOrderNumber, purchaseDate, materialId, purchaseQuantity, purchasePrice, purchaseAmount,
                supplierName, productionBatch, productionDate, paymentStatus, userId, procuringEntity, remark
        );

        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("code", 200);
            response.put("msg", "修改采购记录成功");
        } else {
            response.put("code", 500);
            response.put("msg", "修改采购记录失败");
        }
        return response;
    }
}
