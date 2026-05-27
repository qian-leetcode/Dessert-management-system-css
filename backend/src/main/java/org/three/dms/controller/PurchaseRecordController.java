package org.three.dms.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.three.dms.common.PurchaseRecordDataInfo;
import org.three.dms.entity.PurchaseRecord;
import org.three.dms.service.PurchaseRecordService;

import java.time.LocalDate;
import java.util.*;

import static java.lang.Long.min;

@Slf4j
@RestController
@RequestMapping("/api/purchase_record")
@CrossOrigin(origins = "*")
public class PurchaseRecordController {
    @Resource
    private PurchaseRecordService purchaseRecordService;

    @GetMapping("/list")
    public List<PurchaseRecord> list(){
        return purchaseRecordService.list();
    }

    @PostMapping("/purchase_record_list")
    public PurchaseRecordDataInfo purchase_record_list(@RequestBody Map<String,Object> map){
        PurchaseRecordDataInfo res = new PurchaseRecordDataInfo();
        try {
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
            String user_name = (String) map.get("user_name");
            log.info("查询采购记录列表 - 入参: page_num={}, page_size={}, orderNo={}, supplier={}", page_num, page_size, purchase_order_number, supplier_name);

            List<PurchaseRecord> begin_record_list = purchaseRecordService.findAllPurchaseRecords();
            List<PurchaseRecord> end_record_list = new ArrayList<>();
            log.info("查询采购记录总条数: {}", begin_record_list.size());

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
                                record.getCreate_time().toString().contains(create_time) &&
                                record.getProcuring_name().contains(user_name)
                ) {
                    end_record_list.add(record);
                }
            }
            log.info("采购记录条件筛选后条数: {}", end_record_list.size());

            res.setTotal(end_record_list.size());
            int total = end_record_list.size();
            for (int i = page_size *(page_num - 1); i < min((long) page_size *(page_num - 1) + page_size , total); i++) {
                res.add(end_record_list.get(i));
            }
            res.setCode(200);
            res.setMsg("success");
            log.info("采购记录列表查询成功, 返回条数: {}", total);
        }
        catch (Exception e){
            res.setCode(500);
            res.setMsg(e.getMessage());
            log.error("查询采购记录列表异常: {}", e.getMessage(), e);
        }
        return res;
    }

    // 新增采购记录
    @PostMapping("/add")
    public Map<String, Object> addPurchaseRecord(@RequestBody Map<String, String> map) {
        Map<String, Object> res = new HashMap<>();
        try {
            for (Map.Entry<String, String> entry : map.entrySet()){
                if(entry.getValue() == null || entry.getValue().equals("")){
                    res.put("code", 500);
                    res.put("msg", "不能有空值");
                    log.warn("[参数校验] 新增采购记录参数为空");
                    return res;
                }
            }
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

            String remark = map.get("remark");
            LocalDate createTime = LocalDate.parse(map.get("create_time"));
            log.info("添加采购记录 - 入参: orderNo={}, materialId={}, supplier={}", purchaseOrderNumber, materialId, supplierName);

            int result = purchaseRecordService.insertPurchaseRecord(
                    purchaseOrderNumber, purchaseDate, materialId, purchaseQuantity, purchasePrice, purchaseAmount,
                    supplierName, productionBatch, productionDate, paymentStatus, userId,  remark , createTime
            );
            log.info("插入采购记录结果: result={}", result);

            if (result > 0) {
                res.put("code", 200);
                res.put("msg", "新增采购记录成功");
                log.info("采购记录新增成功, orderNo={}", purchaseOrderNumber);
            } else {
                res.put("code", 500);
                res.put("msg", "新增采购记录失败");
                log.warn("采购记录新增失败, orderNo={}", purchaseOrderNumber);
            }
        }
        catch (Exception e){
            res.put("code", 500);
            res.put("msg", e.getMessage());
            log.error("添加采购记录异常: {}", e.getMessage(), e);
        }
        return res;
    }

    // 删除采购记录
    @PostMapping("/delete")
    public Map<String, Object> deletePurchaseRecord(Integer id) {
        Map<String, Object> res = new HashMap<>();
        try {
            log.info("删除采购记录 - 入参: id={}", id);
            int result = purchaseRecordService.deletePurchaseRecord(id);
            log.info("删除采购记录结果: result={}", result);
            if (result > 0) {
                res.put("code", 200);
                res.put("msg", "删除采购记录成功");
                log.info("采购记录删除成功, id={}", id);
            } else {
                res.put("code", 500);
                res.put("msg", "删除采购记录失败");
                log.warn("采购记录删除失败, id={}", id);
            }
        }
        catch (Exception e){
            res.put("code", 500);
            res.put("msg",e.getMessage());
            log.error("删除采购记录异常: {}", e.getMessage(), e);
        }
        return res;
    }

    // 修改采购记录（不修改主键、创建时间）
    @PostMapping("/update")
    public Map<String, Object> updatePurchaseRecord(@RequestBody Map<String, String> map) {
        Map<String, Object> res = new HashMap<>();
        try {
            for (Map.Entry<String, String> entry : map.entrySet()){
                if(entry.getValue() == null || entry.getValue().equals("")){
                    res.put("code", 500);
                    res.put("msg", "不能有空值");
                    log.warn("[参数校验] 修改采购记录参数为空");
                    return res;
                }
            }
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
            String remark = map.get("remark");

            log.info("更新采购记录 - 入参: id={}, orderNo={}, materialId={}", purchaseId, purchaseOrderNumber, materialId);

            int result = purchaseRecordService.updatePurchaseRecord(
                    purchaseId, purchaseOrderNumber, purchaseDate, materialId, purchaseQuantity, purchasePrice, purchaseAmount,
                    supplierName, productionBatch, productionDate, paymentStatus, userId,remark
            );
            log.info("更新采购记录结果: result={}", result);

            if (result > 0) {
                res.put("code", 200);
                res.put("msg", "修改采购记录成功");
                log.info("采购记录修改成功, id={}", purchaseId);
            } else {
                res.put("code", 500);
                res.put("msg", "修改采购记录失败");
                log.warn("采购记录修改失败, id={}", purchaseId);
            }
        }
        catch (Exception e){
            res.put("code", 500);
            res.put("msg", e.getMessage());
            log.error("修改采购记录异常: {}", e.getMessage(), e);
        }
        return res;
    }
}
