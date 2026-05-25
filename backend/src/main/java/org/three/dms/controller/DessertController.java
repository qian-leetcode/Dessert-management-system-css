package org.three.dms.controller;

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
public class DessertController {
    @Autowired
    private DessertService dessertService;

    @GetMapping("/linked_list")
    public List<Dessert> linked_list(){
        return dessertService.selectDessertWithCategory();
    }

    @PostMapping("/list")
    public DessertDataInfo list(@RequestBody Map<String,String> map){
        List<Dessert> desserts = dessertService.selectDessertWithCategory();
        double temp_price_max = 0.0;
        for (Dessert dessert : desserts) {
            double temp_price = dessert.getPrice();
            if(temp_price > temp_price_max){
                temp_price_max = temp_price;
            }
        }

        // 预处理
        String dessert_name = map.get("dessert_name");
        String dessert_description = map.get("dessert_description");
        String dessert_category = map.get("dessert_category");
        String minStr = map.get("dessert_min_price");

        double temp_price_min = minStr == "" ? 0.0 : Double.parseDouble(minStr);
        Integer page_num = Integer.parseInt(map.get("page_num")) == 0 ? 1 : Integer.parseInt(map.get("page_num"));
        Integer page_size = Integer.parseInt(map.get("page_size")) == 0 ? 10 : Integer.parseInt(map.get("page_size"));
        int total;

        DessertDataInfo res = new DessertDataInfo();
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

        res.setCode(200);
        res.setMsg("success");
        total = temp_desserts.size();
        res.setTotal(total);

        for (int i = page_size *(page_num - 1); i < min((long) page_size *(page_num - 1) + page_size , total); i++) {

            res.add(temp_desserts.get(i));
        }
//        System.out.println(res);
        return res;
    }

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Map<String, String> map){
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(map.get(entry.getKey()) +" " +entry.getValue());
        }
        // 1. 从 map 里取出所有参数
        Integer catId = Integer.parseInt(map.get("cat_id"));
        String name = map.get("name");
        String photoUrl = map.get("photo_url");
        Double price = Double.parseDouble(map.get("price"));
        String description = map.get("description");
        // 解析带 Z 的国际标准时间
        LocalDate releaseDate = OffsetDateTime.parse(map.get("release_date")).toLocalDate();
        Integer dessertStatus = Integer.parseInt(map.get("dessert_status"));
        Integer dessertNumber = Integer.parseInt(map.get("dessert_number"));

        // 2. 调用 Service 新增
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

        // 3. 构造返回结果（和你上面 category 接口风格对齐）
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("code", 200);
            response.put("msg", "新增成功");
        } else {
            response.put("code", 500);
            response.put("msg", "新增失败");
        }
        return response;
    }

    @PostMapping("/delete")
    public Map<String, Object> delete(Integer id) {

        // 2. 调用Service删除方法
        int result = dessertService.delete_dessert(id);

        // 3. 构造返回结果
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("code", 200);
            response.put("msg", "删除成功");
        } else {
            response.put("code", 500);
            response.put("msg", "删除失败");
        }
        return response;
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody Map<String, String> map) {
        // 1. 从map中获取所有参数并转换类型
        Integer id = Integer.parseInt(map.get("id"));
        String name = map.get("name");
        String photoUrl = map.get("photo_url");
        Double price = Double.parseDouble(map.get("price"));
        String description = map.get("description");
        LocalDate releaseDate = LocalDate.parse(map.get("release_date"));
        Integer catId = Integer.parseInt(map.get("cat_id"));
        Integer dessertStatus = Integer.parseInt(map.get("dessert_status"));
        Integer dessertNumber = Integer.parseInt(map.get("dessert_number"));

        // 2. 调用Service修改方法
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

        // 3. 构造返回结果
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("code", 200);
            response.put("msg", "修改成功");
        } else {
            response.put("code", 500);
            response.put("msg", "修改失败");
        }
        return response;
    }

}
