package org.three.dms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Override
    public List<String> get_category_name(){
        return categoryMapper.get_category_name();
    }
}
