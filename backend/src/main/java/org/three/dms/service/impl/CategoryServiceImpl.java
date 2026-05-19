package org.three.dms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.three.dms.entity.Category;
import org.three.dms.mapper.CategoryMapper;
import org.three.dms.service.CategoryService;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper , Category> implements CategoryService {
}
