package org.three.dms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.three.dms.entity.Category;

import java.util.List;


public interface CategoryService extends IService<Category> {
    List<String> get_category_name();
}
