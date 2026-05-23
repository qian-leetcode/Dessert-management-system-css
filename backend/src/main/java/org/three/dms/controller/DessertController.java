package org.three.dms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.three.dms.common.DessertDataInfo;
import org.three.dms.entity.Dessert;
import org.three.dms.service.DessertService;

import java.util.ArrayList;
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
}
