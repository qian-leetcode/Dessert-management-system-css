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
        MaterialDataInfo res = new MaterialDataInfo();
        try {
            Integer page_num = ((Number) map.get("page_num")).intValue();
            Integer page_size = ((Number) map.get("page_size")).intValue();

            String material_code = (String) map.get("material_code");
            String material_name = (String) map.get("material_name");
            String material_category = (String) map.get("material_category");
            Integer material_shelf_life_days_min = ((Number) map.get("material_shelf_life_days_min")).intValue();
            Integer material_shelf_life_days_max = ((Number) map.get("material_shelf_life_days_max")).intValue();
            String material_storage_condition = (String) map.get("material_storage_condition");
            String material_remark = (String) map.get("material_remark");
            log.info("查询物料列表 - 入参: page_num={}, page_size={}, material_code={}, material_name={}", page_num, page_size, material_code, material_name);
            // int 最大值
            int max = Integer.MAX_VALUE;
            // int 最小值
            int min = Integer.MIN_VALUE;
            min = Math.max(min, material_shelf_life_days_min);
            max = Math.min(max, material_shelf_life_days_max);

            List<MaterialInformation> begin_material_list = materialInformationService.list();
            log.info("查询物料总条数: {}", begin_material_list.size());
            List<MaterialInformation> temp_list = new ArrayList<>();
            for (MaterialInformation materialInformation:begin_material_list){
                Integer time = materialInformation.getMaterial_shelf_life_days();
                if(materialInformation.getMaterial_code().contains(material_code)
                        && materialInformation.getMaterial_name().contains(material_name)
                        && materialInformation.getMaterial_category().contains(material_category)
                        && materialInformation.getMaterial_storage_condition().contains(material_remark)
                        &&  materialInformation.getMaterial_storage_condition().contains(material_storage_condition)
                        && (time >= min && time <= max)
                ){
                    temp_list.add(materialInformation);
                }
            }
            log.info("物料条件筛选后条数: {}", temp_list.size());

            int total = temp_list.size();
            res.setTotal(total);
            res.setCode(200);
            res.setMsg("success");
            for (int i = page_size *(page_num - 1); i < min((long) page_size *(page_num - 1) + page_size , total); i++) {
                res.add(temp_list.get(i));
            }
            log.info("物料列表查询成功, 返回条数: {}", total);
        }
        catch (Exception e){
            res.setCode(500);
            res.setMsg(e.getMessage());
            res.setTotal(0);
            log.error("查询物料列表异常: {}", e.getMessage(), e);
        }
        return res;
    }

    // 新增物料
    @PostMapping("/add")
    public Map<String, Object> addMaterial(@RequestBody Map<String, String> map) {
        Map<String, Object> res = new HashMap<>();
        try {
            String materialCode = map.get("material_code");
            String materialName = map.get("material_name");
            String materialCategory = map.get("material_category");
            String materialSpecification = map.get("material_specification");
            String materialUnit = map.get("material_unit");
            Integer materialShelfLifeDays = Integer.parseInt(map.get("material_shelf_life_days"));
            String materialStorageCondition = map.get("material_storage_condition");
            String materialRemark = map.get("material_remark");

            log.info("添加物料 - 入参: code={}, name='{}', category={}, unit={}", materialCode, materialName, materialCategory, materialUnit);

            int result = materialInformationService.insertMaterial(
                    materialCode, materialName, materialCategory, materialSpecification,
                    materialUnit, materialShelfLifeDays, materialStorageCondition, materialRemark
            );

            log.info("插入物料结果: result={}", result);

            if (result > 0) {
                res.put("code", 200);
                res.put("msg", "新增物料成功");
                log.info("物料新增成功, code={}", materialCode);
            } else {
                res.put("code", 500);
                res.put("msg", "新增物料失败");
                log.warn("物料新增失败, code={}", materialCode);
            }
        }
        catch (Exception e){
            res.put("code", 400);
            res.put("msg", e.getMessage());
            log.error("添加物料异常: {}", e.getMessage(), e);
        }

        return res;
    }

    // 删除物料
    @PostMapping("/delete")
    public Map<String, Object> deleteMaterial(Integer id) {
        Map<String, Object> res = new HashMap<>();
        try{
            log.info("删除物料 - 入参: id={}", id);
            int result = materialInformationService.deleteMaterial(id);
            log.info("删除物料结果: result={}", result);
            if (result > 0) {
                res.put("code", 200);
                res.put("msg", "删除物料成功");
                log.info("物料删除成功, id={}", id);
            } else {
                res.put("code", 500);
                res.put("msg", "删除物料失败");
                log.warn("物料删除失败, id={}", id);
            }
        }
        catch (Exception e){
            res.put("code", 500);
            res.put("msg", "删除物料失败");
            res.put("exception", e.getMessage());
            log.error("删除物料异常: {}", e.getMessage(), e);
        }
        return res;
    }

    // 修改物料（不修改主键）
    @PostMapping("/update")
    public Map<String, Object> updateMaterial(@RequestBody Map<String, String> map) {
        Map<String, Object> res = new HashMap<>();
        try {
            Integer materialId = Integer.parseInt(map.get("materialId"));
            String materialCode = map.get("materialCode");
            String materialName = map.get("materialName");
            String materialCategory = map.get("materialCategory");
            String materialSpecification = map.get("materialSpecification");
            String materialUnit = map.get("materialUnit");
            Integer materialShelfLifeDays = Integer.parseInt(map.get("materialShelfLifeDays"));
            String materialStorageCondition = map.get("materialStorageCondition");
            String materialRemark = map.get("materialRemark");
            log.info("更新物料 - 入参: id={}, code={}, name='{}'", materialId, materialCode, materialName);

            int result = materialInformationService.updateMaterial(
                    materialId, materialCode, materialName, materialCategory, materialSpecification,
                    materialUnit, materialShelfLifeDays, materialStorageCondition, materialRemark
            );

            log.info("更新物料结果: result={}", result);

            if (result > 0) {
                res.put("code", 200);
                res.put("msg", "修改物料成功");
                log.info("物料修改成功, id={}", materialId);
            } else {
                res.put("code", 500);
                res.put("msg", "修改物料失败");
                log.warn("物料修改失败, id={}", materialId);
            }
        }
        catch (Exception e){
            res.put("code", 500);
            res.put("msg", e.getMessage());
            log.error("修改物料异常: {}", e.getMessage(), e);
        }
        return res;
    }
}
