package three.dms.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RestController;
import org.three.dms.entity.Category;
import org.three.dms.service.CategoryService;

import java.util.List;

@SpringBootTest // 关键！让 Spring 启动并加载所有bean
public class Test1_category {

    @Autowired
    private CategoryService categoryService;

    @Test
    void testListCategory(){
        // 现在这里 categoryService 绝对不会是 null
        List<Category> list = categoryService.list();

        for (Category category : list){
            System.out.println(category.getName() + " | " + category.getDescription());
        }
    }
}
