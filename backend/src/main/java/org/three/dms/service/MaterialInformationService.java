package org.three.dms.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.three.dms.entity.MaterialInformation;

public interface MaterialInformationService extends IService<MaterialInformation> {
    // 新增
    int insertMaterial(String materialCode, String materialName, String materialCategory, String materialSpecification,
                       String materialUnit, Integer materialShelfLifeDays, String materialStorageCondition, String materialRemark);

    // 删除
    int deleteMaterial(Integer materialId);

    // 修改
    int updateMaterial(Integer materialId, String materialCode, String materialName, String materialCategory, String materialSpecification,
                       String materialUnit, Integer materialShelfLifeDays, String materialStorageCondition, String materialRemark);
}
