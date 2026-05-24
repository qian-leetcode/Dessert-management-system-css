package org.three.dms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.three.dms.common.CategoryDataInfo;
import org.three.dms.entity.Category;
import org.three.dms.service.CategoryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Long.min;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list_name")
    public List<String> get_category_name() {
        return categoryService.get_category_name();
    }

    @PostMapping("/list")
    public CategoryDataInfo list(@RequestBody Map<String,String> map){
        // 预处理
        Integer page_num = Integer.parseInt(map.get("page_num"));
        Integer page_size=Integer.parseInt(map.get("page_size"));
        String name = map.get("name");
        String description =map.get("description");
        if(page_num == 0) page_num=1;
        if(page_size == 0) page_size=10;

        List<Category> categoryList = categoryService.list();
        CategoryDataInfo res = new CategoryDataInfo();
        int len = categoryList.size();
        List<Category> temp = new ArrayList<Category>();
        for(int i = 0 ; i <  len; i++){
            if(categoryList.get(i).getName().contains(name) &&  categoryList.get(i).getDescription().contains(description)){
                temp.add(categoryList.get(i));
            }
        }
        res.setCode(200);
        res.setMsg("success");
        res.setTotal(temp.size());
        len = temp.size();
        for(int i = page_size * (page_num - 1); i < min((page_size * (page_num - 1)) + page_size , len); i++){
            res.add(temp.get(i));
        }
        return res;
    }

    @PostMapping("/add")
    public Map<String , Object>  add(@RequestBody Map<String,String> map){
         String name = map.get("name");
         String description = map.get("description");
         int pos = categoryService.insert_category(name, description);
//         System.out.println(name);
//         System.out.println(description);
         Map<String,Object> res = new HashMap<>();
         if(pos == 0){
             res.put("code", 500);
             res.put("msg", "Insertion failed");
         }
         else {
             res.put("code", 200);
             res.put("msg", "success");
         }
         return res;
    }

    @PostMapping("/delete")
    public Map<String , Object>  del(Integer id){
        Map<String,Object> res = new HashMap<>();
        int pos = categoryService.delete_category(id);
        if(pos == 0){
            res.put("code", 500);
            res.put("msg", "Deletion failed");
        }
        else {
            res.put("code", 200);
            res.put("msg", "success");
        }
        return res;
    }

    @PostMapping("/update")
    public Map<String , Object>  update(@RequestBody Map<String,String> map){
        Integer id = Integer.parseInt(map.get("id"));
        String name = map.get("name");
        String description = map.get("description");
        int pos = categoryService.update_category(name , description, id);
        Map<String,Object> res = new HashMap<>();
        if(pos == 0){
            res.put("code", 500);
            res.put("msg", "update failed");
        }
        else {
            res.put("code", 200);
            res.put("msg", "success");
        }
        return res;
    }
}
