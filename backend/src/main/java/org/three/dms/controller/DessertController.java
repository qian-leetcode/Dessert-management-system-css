package org.three.dms.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.three.dms.common.DessertDataInfo;
import org.three.dms.entity.Dessert;
import org.three.dms.service.DessertService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Long.min;

@RestController
@RequestMapping("/api/dessert")
@CrossOrigin(origins = "*")
@Slf4j
public class DessertController {
    @Resource
    private DessertService dessertService;

    @GetMapping("/linked_list")
    public List<Dessert> linked_list(){
        return dessertService.selectDessertWithCategory();
    }

    @PostMapping("/list")
    public DessertDataInfo list(@RequestBody Map<String,String> map){
        DessertDataInfo res = new DessertDataInfo();
        try{
            log.info("查询甜品列表 - 入参: {}", map);
            List<Dessert> desserts = dessertService.selectDessertWithCategory();
            log.info("查询甜品总数: {}", desserts.size());
            double temp_price_max = 0.0;
            for (Dessert dessert : desserts) {
                double temp_price = dessert.getPrice();
                if(temp_price > temp_price_max){
                    temp_price_max = temp_price;
                }
            }
            log.info("查询最高价格: {}", temp_price_max);

            // 预处理
            String dessert_name = map.get("dessert_name");
            String dessert_description = map.get("dessert_description");
            String dessert_category = map.get("dessert_category");
            String minStr = map.get("dessert_min_price");

            double temp_price_min = minStr == "" ? 0.0 : Double.parseDouble(minStr);
            Integer page_num = Integer.parseInt(map.get("page_num")) == 0 ? 1 : Integer.parseInt(map.get("page_num"));
            Integer page_size = Integer.parseInt(map.get("page_size")) == 0 ? 10 : Integer.parseInt(map.get("page_size"));
            int total;

            log.info("分页参数: page_num={}, page_size={}, 最低价格={}", page_num, page_size, temp_price_min);

            List<Dessert> temp_desserts = new ArrayList<>();
            // 筛选条件
            for (Dessert dessert : desserts) {
                double priceStr = dessert.getPrice();
                if(dessert.getName().contains(dessert_name) &&
                        dessert.getDescription().contains(dessert_description) &&
                        dessert.getDessert_category().contains(dessert_category) &&
                        priceStr >= temp_price_min &&
                        priceStr <= temp_price_max){
                    temp_desserts.add(dessert);
                }
            }
            log.info("条件筛选后结果数: {}", temp_desserts.size());

            res.setCode(200);
            res.setMsg("success");
            total = temp_desserts.size();
            res.setTotal(total);
            for (int i = page_size *(page_num - 1); i < min((long) page_size *(page_num - 1) + page_size , total); i++) {
                res.add(temp_desserts.get(i));
            }
            log.info("甜品列表查询成功, 返回记录数: {}", total);
        }
        catch(Exception e){
            res.setCode(500);
            res.setMsg("failure");
            log.error("查询甜品列表异常: {}", e.getMessage(), e);
        }
        return res;
    }

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Map<String, String> map){
        Map<String, Object> res = new HashMap<>();
        try{
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String value = entry.getValue();
                if (value == null || value.isEmpty()) {
                    res.put("code", 400 );
                    res.put("msg", entry.getKey() + "' 不能为空");
                    log.warn("[参数校验] 非空校验失败, 字段:{} 值为空", entry.getKey());
                    return res;
                }
            }

            Integer catId = Integer.parseInt(map.get("cat_id"));
            String name = map.get("name");
            String photoUrl = map.get("photo_url");
            Double price = Double.parseDouble(map.get("price"));
            String description = map.get("description");

            LocalDate releaseDate = OffsetDateTime.parse(map.get("release_date")).toLocalDate();
            Integer dessertStatus = Integer.parseInt(map.get("dessert_status"));
            Integer dessertNumber = Integer.parseInt(map.get("dessert_number"));

            log.info("添加甜品 - 入参: cat_id={}, name='{}', price={}, description='{}'", catId, name, price, description);

            int result = dessertService.insert_dessert(
                    name,
                    photoUrl,
                    price,
                    description,
                    releaseDate,
                    catId,
                    dessertStatus,
                    dessertNumber
            );

            log.info("插入甜品结果: result={}", result);
            if (result > 0) {
                res.put("code", 200);
                res.put("msg", "新增成功");
                log.info("甜品新增成功, name={}", name);
            } else {
                res.put("code", 500);
                res.put("msg", "新增失败");
                log.warn("甜品新增失败, name={}", name);
            }
        }
        catch(Exception e){
            res.put("code", 500);
            res.put("msg", "新增失败");
            log.error("添加甜品异常: {}", e.getMessage(), e);
        }
        return res;
    }

    @PostMapping("/delete")
    public Map<String, Object> delete(Integer id) {
        Map<String, Object> res = new HashMap<>();
        try {
            log.info("删除甜品 - 入参: id={}", id);
            int result = dessertService.delete_dessert(id);

            log.info("删除甜品结果: result={}", result);
            if (result > 0) {
                res.put("code", 200);
                res.put("msg", "删除成功");
                log.info("甜品删除成功, id={}", id);
            } else {
                res.put("code", 500);
                res.put("msg", "删除失败");
                log.warn("甜品删除失败, id={}", id);
            }
        }
        catch(Exception e){
            res.put("code", 500);
            res.put("msg", "删除失败");
            log.error("删除甜品异常: {}", e.getMessage(), e);
        }
        return res;
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody Map<String, String> map) {
        Map<String, Object> res = new HashMap<>();
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String value = entry.getValue();
                if (value == null || value.isEmpty()) {
                    res.put("code", 400 );
                    res.put("msg", entry.getKey() + "' 不能为空");
                    log.warn("[参数校验] 非空校验失败, 字段:{} 值为空", entry.getKey());
                    return res;
                }
            }

            Integer id = Integer.parseInt(map.get("id"));
            String name = map.get("name");
            String photoUrl = map.get("photo_url");
            Double price = Double.parseDouble(map.get("price"));
            String description = map.get("description");
            LocalDate releaseDate = LocalDate.parse(map.get("release_date"));
            Integer catId = Integer.parseInt(map.get("cat_id"));
            Integer dessertStatus = Integer.parseInt(map.get("dessert_status"));
            Integer dessertNumber = Integer.parseInt(map.get("dessert_number"));

            log.info("更新甜品 - 入参: id={}, name='{}', price={}, cat_id={}", id, name, price, catId);

            int result = dessertService.update_dessert(
                    id,
                    name,
                    photoUrl,
                    price,
                    description,
                    releaseDate,
                    catId,
                    dessertStatus,
                    dessertNumber
            );

            log.info("更新甜品结果: result={}", result);

            if (result > 0) {
                res.put("code", 200);
                res.put("msg", "修改成功");
                log.info("甜品修改成功, id={}", id);
            } else {
                res.put("code", 500);
                res.put("msg", "修改失败");
                log.warn("甜品修改失败, id={}", id);
            }
        }
        catch (Exception e) {
            res.put("code", 500);
            res.put("msg", "修改失败");
            log.error("修改甜品异常: {}", e.getMessage(), e);
        }
        return res;
    }
}
