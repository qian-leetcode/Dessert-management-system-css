package org.three.dms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.three.dms.entity.Category;

import java.util.List;


public interface CategoryService extends IService<Category> {
    // 获取分类名称
    List<String> get_category_name();

    // 新增分类
    int insert_category(String name, String description);

    //删除分类
    int delete_category(int id);

    // 修改分类
    int update_category(String name, String description,int id);
}
