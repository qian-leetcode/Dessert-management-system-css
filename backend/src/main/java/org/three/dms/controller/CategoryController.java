package org.three.dms.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/list_name")
    public List<String> get_category_name() {
        return categoryService.get_category_name();
    }

    @GetMapping("/lists")
    public List<Category> get_category_lists() {
        return categoryService.list();
    }

    @PostMapping("/list")
    public CategoryDataInfo list(@RequestBody Map<String,String> map){
        CategoryDataInfo res = new CategoryDataInfo();
        try{
            // 预处理- 空值判定
            Integer page_num = Integer.parseInt(map.get("page_num"));
            Integer page_size=Integer.parseInt(map.get("page_size"));
            String name = map.get("name");
            String description =map.get("description");
            log.info("查询分类列表 - 入参: page_num={}, page_size={}, name='{}', description='{}'",
                    page_num, page_size, name, description);
            if(page_num == 0) page_num=1;
            if(page_size == 0) page_size=10;

            List<Category> categoryList = categoryService.list();
            log.info("数据库查询到 {} 条分类", categoryList.size());
            int len = categoryList.size();
            List<Category> temp = new ArrayList<Category>();
            for(int i = 0 ; i <  len; i++){
                if(categoryList.get(i).getName().contains(name) &&
                        categoryList.get(i).getDescription().contains(description)){
                    temp.add(categoryList.get(i));
                }
            }
            log.info("过滤后剩余 {} 条 (条件: name包含'{}', description包含'{}')",
                    temp.size(), name, description);
            res.setCode(200);
            res.setMsg("success");
            res.setTotal(temp.size());
            log.info("code={}, msg='{}', total={}", 200, "success", temp.size());
            log.info("列表查询成功");
            len = temp.size();
            for(int i = page_size * (page_num - 1); i < min((page_size * (page_num - 1)) + page_size , len); i++){
                res.add(temp.get(i));
            }
            log.info("分页返回 {} 条 (page_num={}, page_size={})",
                    Math.min(page_size, len - page_size * (page_num - 1)), page_num, page_size);
        }
        catch(Exception e){
            res.setCode(500);
            res.setMsg(e.getMessage());
            log.error("查询分类列表异常: {}", e.getMessage(), e);
        }
        return res;
    }

    @PostMapping("/add")
    public Map<String , Object>  add(@RequestBody Map<String,String> map){
        Map<String,Object> res = new HashMap<>();
        try{
            String name = map.get("name");
            String description = map.get("description");

            log.info("添加分类 - 入参: name='{}', description='{}'", name, description);
            int pos = categoryService.insert_category(name, description);

            log.info("插入分类结果: pos={}", pos);
            if(pos == 0){
                res.put("code", 500);
                res.put("msg", "Insertion failed");
                log.warn("分类插入失败, name='{}', description='{}'", name, description);
            }
            else {
                res.put("code", 200);
                res.put("msg", "success");
                log.info("分类插入成功, id={}", pos);
            }
        }
        catch(Exception e){
            res.put("code", 500);
            res.put("msg", "Insertion failed");
            log.error("添加分类异常: {}", e.getMessage(), e);
        }
         return res;
    }

    @PostMapping("/delete")
    public Map<String , Object>  del(Integer id){
        Map<String,Object> res = new HashMap<>();
        log.info("删除分类请求，id：{}", id);
        try{
            int pos = categoryService.delete_category(id);
            if(pos == 0){
                res.put("code", 500);
                res.put("msg", "Deletion failed");
                log.warn("分类删除失败，数据不存在，id：{}", id);
            }
            else {
                res.put("code", 200);
                res.put("msg", "success");
                log.info("分类删除成功，id：{}", id);
            }
        }
        catch(Exception e){
            res.put("code", 500);
            res.put("msg", e.getMessage());
            log.error("分类删除异常，id：{}", id, e);
        }
        return res;
    }

    @PostMapping("/update")
    public Map<String , Object>  update(@RequestBody Map<String,String> map){
        Map<String,Object> res = new HashMap<>();
        log.info("分类更新请求，参数：{}", map);
        try{
            Integer id = Integer.parseInt(map.get("id"));
            String name = map.get("name");
            String description = map.get("description");
            int pos = categoryService.update_category(name , description, id);
            log.info("更新分类 - 入参: id={}, name='{}', description='{}'", id, name, description);
            if(pos == 0){
                res.put("code", 500);
                res.put("msg", "update failed");
                log.warn("分类更新失败，数据不存在，id：{}", id);
            }
            else {
                res.put("code", 200);
                res.put("msg", "success");
                log.info("分类更新成功，id：{}", id);
            }
        }
        catch(Exception e){
            res.put("code", 500);
            res.put("msg", e.getMessage());
            log.error("分类更新异常，参数：{}", map, e);
        }
        return res;
    }
}
