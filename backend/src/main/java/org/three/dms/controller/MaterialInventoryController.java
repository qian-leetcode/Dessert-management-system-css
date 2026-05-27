package org.three.dms.controller;

import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.three.dms.common.InventoryDataInfo;
import org.three.dms.entity.MaterialInventory;
import org.three.dms.service.MaterialInventoryService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Long.min;

@Slf4j
@RestController
@RequestMapping("/api/material_inventory")
@CrossOrigin(origins = "*")
public class MaterialInventoryController {
    @Resource
    private MaterialInventoryService materialInventoryService;

    @GetMapping("/list")
    public List<MaterialInventory> list(){
        return materialInventoryService.list();
    }

    @PostMapping("/get_inventory_information")
    public InventoryDataInfo getInventoryInformation(@RequestBody Map<String, String> map){
        InventoryDataInfo res = new InventoryDataInfo();
        try {
            String material_name = map.get("material_name");
            String latest_purchase_date = map.get("latest_purchase_date");
            Integer page_num = Integer.valueOf(map.get("page_num"));
            Integer page_size = Integer.valueOf(map.get("page_size"));
            log.info("查询库存列表 - 入参: page_num={}, page_size={}, material_name={}, latest_purchase_date={}", page_num, page_size, material_name, latest_purchase_date);

            List<MaterialInventory> begin_inventory_list = materialInventoryService.selectByMaterial();
            log.info("查询库存总条数: {}", begin_inventory_list.size());
            List<MaterialInventory> end_inventory_list = new ArrayList<>();
            for (MaterialInventory inventory:begin_inventory_list){
                if(inventory.getMaterial_name().contains(material_name) &&
                        inventory.getLast_purchase_time().toString().contains(latest_purchase_date)
                ){
                    end_inventory_list.add(inventory);
                }
            }
            log.info("库存条件筛选后条数: {}", end_inventory_list.size());
            int total = end_inventory_list.size();
            res.setCode(200);
            res.setTotal(total);
            for (int i = page_size *(page_num - 1); i < min((long) page_size *(page_num - 1) + page_size , total); i++) {
                res.add(end_inventory_list.get(i));
            }
            res.setMsg("success");
            log.info("库存列表查询成功, 返回条数: {}", total);
        }
        catch (Exception e){
            res.setCode(500);
            res.setMsg(e.getMessage());
            res.setTotal(0);
            log.error("查询库存列表异常: {}", e.getMessage(), e);
        }
        return res;
    }

    // 新增库存
    @PostMapping("/add")
    public Map<String, Object> addInventory(@RequestBody Map<String, String> map) {
        Map<String, Object> res = new HashMap<>();
        try {
            Integer materialId = Integer.parseInt(map.get("material_id"));
            Double currentInventoryLevel = Double.parseDouble(map.get("current_inventory_level"));
            Double safetyStockQuantity = Double.parseDouble(map.get("safety_stock_quantity"));
            String dateStr  = map.get("last_purchase_time");
            LocalDate lastPurchaseTime = LocalDate.parse(dateStr.substring(0,10));
            log.info("添加库存 - 入参: material_id={}, inventory={}, safety={}, time={}", materialId, currentInventoryLevel, safetyStockQuantity, lastPurchaseTime);

            int result = materialInventoryService.insertInventory(materialId, currentInventoryLevel, safetyStockQuantity, lastPurchaseTime);
            log.info("插入库存结果: result={}", result);

            if (result > 0) {
                res.put("code", 200);
                res.put("msg", "新增库存成功");
                log.info("库存新增成功, material_id={}", materialId);
            } else {
                res.put("code", 500);
                res.put("msg", "新增库存失败");
                log.warn("库存新增失败, material_id={}", materialId);
            }
        }
        catch (Exception e){
            res.put("code", 500);
            res.put("msg", "新增库存失败");
            res.put("exception", e.getMessage());
            log.error("添加库存异常: {}", e.getMessage(), e);
        }
        return res;
    }

    // 删除库存
    @PostMapping("/delete")
    public Map<String, Object> deleteInventory(Integer id) {
        Map<String, Object> res = new HashMap<>();
        try {
            log.info("删除库存 - 入参: id={}", id);
            int result = materialInventoryService.deleteInventory(id);
            log.info("删除库存结果: result={}", result);

            if (result > 0) {
                res.put("code", 200);
                res.put("msg", "删除库存成功");
                log.info("库存删除成功, id={}", id);
            } else {
                res.put("code", 500);
                res.put("msg", "删除库存失败");
                log.warn("库存删除失败, id={}", id);
            }
        }
        catch (Exception e){
            res.put("code", 500);
            res.put("msg", e.getMessage());
            log.error("删除库存异常: {}", e.getMessage(), e);
        }
        return res;
    }

    // 修改库存（不修改主键）
    @PostMapping("/update")
    public Map<String, Object> updateInventory(@RequestBody Map<String, String> map) {
        Map<String, Object> res = new HashMap<>();
        try {
            Integer inventoryId = Integer.parseInt(map.get("inventory_id"));
            Integer materialId = Integer.parseInt(map.get("material_id"));
            Double currentInventoryLevel = Double.parseDouble(map.get("current_inventory_level"));
            Double safetyStockQuantity = Double.parseDouble(map.get("safety_stock_quantity"));
            LocalDate lastPurchaseTime = LocalDate.parse(map.get("last_purchase_time"));
            log.info("更新库存 - 入参: inventory_id={}, material_id={}", inventoryId, materialId);
            int result = materialInventoryService.updateInventory(inventoryId, materialId, currentInventoryLevel, safetyStockQuantity, lastPurchaseTime);
            log.info("更新库存结果: result={}", result);
            if (result > 0) {
                res.put("code", 200);
                res.put("msg", "修改库存成功");
                log.info("库存修改成功, inventory_id={}", inventoryId);
            } else {
                res.put("code", 500);
                res.put("msg", "修改库存失败");
                log.warn("库存修改失败, inventory_id={}", inventoryId);
            }
        }
        catch (Exception e){
            res.put("code", 500);
            res.put("msg", e.getMessage());
            log.error("修改库存异常: {}", e.getMessage(), e);
        }
        return res;
    }
}
