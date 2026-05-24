package org.three.dms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.three.dms.entity.Category;
import org.three.dms.mapper.CategoryMapper;
import org.three.dms.service.CategoryService;

import java.util.List;
import java.util.ListResourceBundle;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper , Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    // 获取分类名称
    @Override
    public List<String> get_category_name(){
        return categoryMapper.get_category_name();
    }

    // 新增分类
    @Override
    public int insert_category(String name, String description) {
        return categoryMapper.insert_category(name, description);
    }

    //删除分类
    @Override
    public int delete_category( int id){
        return categoryMapper.delete_category(id);
    }

    // 修改分类
    @Override
    public int update_category(String name, String description,int id){
        return categoryMapper.update_category(name, description,id);
    }
}
