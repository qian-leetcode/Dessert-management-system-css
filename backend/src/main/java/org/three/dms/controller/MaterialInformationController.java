package org.three.dms.controller;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.three.dms.common.MaterialDataInfo;
import org.three.dms.entity.MaterialInformation;
import org.three.dms.service.MaterialInformationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Long.min;

@Slf4j
@RestController
@RequestMapping("/api/material_information")
@CrossOrigin(origins = "*")
public class MaterialInformationController {
    @Resource
    private MaterialInformationService materialInformationService;

    @GetMapping("/list")
    public List<MaterialInformation> list(){
        log.info("list");
        return materialInformationService.list();
    }

    @PostMapping("/query_list")
    public MaterialDataInfo queryList(@RequestBody Map<String,Object> map){
        Integer page_num = (Integer) map.get("page_num");
        Integer page_size = (Integer) map.get("page_size");

        String material_code = (String) map.get("material_code");
        String material_name = (String) map.get("material_name");
        String material_category = (String) map.get("material_category");
        String material_shelf_life_days = (String) map.get("material_shelf_life_days");
        String material_storage_condition = (String) map.get("material_storage_condition");
        String material_remark = (String) map.get("material_remark");
        List<MaterialInformation> begin_material_list = materialInformationService.list();
        List<MaterialInformation> temp_list = new ArrayList<>();
        for (MaterialInformation materialInformation:begin_material_list){
            if(materialInformation.getMaterial_code().contains(material_code)
            && materialInformation.getMaterial_name().contains(material_name)
            && materialInformation.getMaterial_category().contains(material_category)
            && materialInformation.getMaterial_storage_condition().contains(material_remark)
            && materialInformation.getMaterial_shelf_life_days().toString().contains(material_shelf_life_days)
            &&  materialInformation.getMaterial_storage_condition().contains(material_storage_condition)){
                temp_list.add(materialInformation);
            }
        }
        MaterialDataInfo res = new MaterialDataInfo();
        int total = temp_list.size();
        res.setTotal(total);
        res.setCode(200);
        res.setMsg("success");
        for (int i = page_size *(page_num - 1); i < min((long) page_size *(page_num - 1) + page_size , total); i++) {
            res.add(temp_list.get(i));
        }
        return res;
    }

    // 新增物料
    @PostMapping("/add")
    public Map<String, Object> addMaterial(@RequestBody Map<String, String> map) {
        String materialCode = map.get("material_code");
        String materialName = map.get("material_name");
        String materialCategory = map.get("material_category");
        String materialSpecification = map.get("material_specification");
        String materialUnit = map.get("material_unit");
        Integer materialShelfLifeDays = Integer.parseInt(map.get("material_shelf_life_days"));
        String materialStorageCondition = map.get("material_storage_condition");
        String materialRemark = map.get("material_remark");

        int result = materialInformationService.insertMaterial(
                materialCode, materialName, materialCategory, materialSpecification,
                materialUnit, materialShelfLifeDays, materialStorageCondition, materialRemark
        );

        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("code", 200);
            response.put("msg", "新增物料成功");
        } else {
            response.put("code", 500);
            response.put("msg", "新增物料失败");
        }
        return response;
    }

    // 删除物料
    @PostMapping("/delete")
    public Map<String, Object> deleteMaterial(Integer id) {
        Map<String, Object> response = new HashMap<>();
        try{
            int result = materialInformationService.deleteMaterial(id);
            if (result > 0) {
                response.put("code", 200);
                response.put("msg", "删除物料成功");
            } else {
                response.put("code", 500);
                response.put("msg", "删除物料失败");
            }
        }
        catch (Exception e){
            response.put("code", 500);
            response.put("msg", "删除物料失败");
            response.put("exception", e);
        }
        return response;
    }

    // 修改物料（不修改主键）
    @PostMapping("/update")
    public Map<String, Object> updateMaterial(@RequestBody Map<String, String> map) {
        Integer materialId = Integer.parseInt(map.get("materialId"));
        String materialCode = map.get("materialCode");
        String materialName = map.get("materialName");
        String materialCategory = map.get("materialCategory");
        String materialSpecification = map.get("materialSpecification");
        String materialUnit = map.get("materialUnit");
        Integer materialShelfLifeDays = Integer.parseInt(map.get("materialShelfLifeDays"));
        String materialStorageCondition = map.get("materialStorageCondition");
        String materialRemark = map.get("materialRemark");

        int result = materialInformationService.updateMaterial(
                materialId, materialCode, materialName, materialCategory, materialSpecification,
                materialUnit, materialShelfLifeDays, materialStorageCondition, materialRemark
        );

        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("code", 200);
            response.put("msg", "修改物料成功");
        } else {
            response.put("code", 500);
            response.put("msg", "修改物料失败");
        }
        return response;
    }
}
