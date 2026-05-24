package org.three.dms.controller;

import jakarta.annotation.Resource;
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

@RestController
@RequestMapping("/api/material_inventory")
@CrossOrigin(origins = "*")
public class MaterialInventoryController {
    @Resource
    private MaterialInventoryService materialInventoryService;

    @GetMapping("/list")
    public List<MaterialInventory> list(){
//        List<MaterialInventory> users = materialInventoryService.list();
//        System.out.println("users = " + users);
        return materialInventoryService.list();
    }

    @PostMapping("/get_inventory_information")
    public InventoryDataInfo getInventoryInformation(@RequestBody Map<String, String> map){
        System.out.println(map.size());
        for (Map.Entry<String, String> entry : map.entrySet()){
            System.out.println(map.get(entry.getKey()) + " " + entry.getValue());
        }
        String inventory_id = map.get("inventory_id");
        String material_id = map.get("material_id");
        String latest_purchase_date = map.get("latest_purchase_date");
        Integer page_num = Integer.valueOf(map.get("page_num"));
        Integer page_size = Integer.valueOf(map.get("page_size"));
        List<MaterialInventory> begin_inventory_list = materialInventoryService.selectByMaterial();
        List<MaterialInventory> end_inventory_list = new ArrayList<>();
        for (MaterialInventory inventory:begin_inventory_list){
            if(inventory.getMaterial_id().toString().contains(material_id) &&
                inventory.getInventory_id().toString().contains(inventory_id) &&
                    inventory.getLast_purchase_time().toString().contains(latest_purchase_date)
            ){
                end_inventory_list.add(inventory);
            }
        }
        InventoryDataInfo res = new InventoryDataInfo();
        int total = end_inventory_list.size();
        res.setCode(200);
        res.setTotal(total);
        for (int i = page_size *(page_num - 1); i < min((long) page_size *(page_num - 1) + page_size , total); i++) {
            res.add(end_inventory_list.get(i));
        }
        res.setMsg("success");
        return res;
    }

    // 新增库存
    @PostMapping("/add")
    public Map<String, Object> addInventory(@RequestBody Map<String, String> map) {
        Integer materialId = Integer.parseInt(map.get("materialId"));
        Double currentInventoryLevel = Double.parseDouble(map.get("currentInventoryLevel"));
        Double safetyStockQuantity = Double.parseDouble(map.get("safetyStockQuantity"));
        LocalDate lastPurchaseTime = LocalDate.parse(map.get("lastPurchaseTime"));

        int result = materialInventoryService.insertInventory(materialId, currentInventoryLevel, safetyStockQuantity, lastPurchaseTime);

        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("code", 200);
            response.put("msg", "新增库存成功");
        } else {
            response.put("code", 500);
            response.put("msg", "新增库存失败");
        }
        return response;
    }

    // 删除库存
    @PostMapping("/delete")
    public Map<String, Object> deleteInventory(@RequestBody Map<String, String> map) {
        Integer inventoryId = Integer.parseInt(map.get("inventoryId"));
        int result = materialInventoryService.deleteInventory(inventoryId);

        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("code", 200);
            response.put("msg", "删除库存成功");
        } else {
            response.put("code", 500);
            response.put("msg", "删除库存失败");
        }
        return response;
    }

    // 修改库存（不修改主键）
    @PostMapping("/update")
    public Map<String, Object> updateInventory(@RequestBody Map<String, String> map) {
        Integer inventoryId = Integer.parseInt(map.get("inventoryId"));
        Integer materialId = Integer.parseInt(map.get("materialId"));
        Double currentInventoryLevel = Double.parseDouble(map.get("currentInventoryLevel"));
        Double safetyStockQuantity = Double.parseDouble(map.get("safetyStockQuantity"));
        LocalDate lastPurchaseTime = LocalDate.parse(map.get("lastPurchaseTime"));

        int result = materialInventoryService.updateInventory(inventoryId, materialId, currentInventoryLevel, safetyStockQuantity, lastPurchaseTime);

        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("code", 200);
            response.put("msg", "修改库存成功");
        } else {
            response.put("code", 500);
            response.put("msg", "修改库存失败");
        }
        return response;
    }
}
