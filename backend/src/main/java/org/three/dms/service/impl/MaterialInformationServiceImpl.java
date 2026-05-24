package org.three.dms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.three.dms.entity.MaterialInformation;
import org.three.dms.mapper.MaterialInformationMapper;
import org.three.dms.service.MaterialInformationService;

@Service
public class MaterialInformationServiceImpl extends ServiceImpl<MaterialInformationMapper , MaterialInformation> implements MaterialInformationService {
    @Autowired
    private MaterialInformationMapper materialInformationMapper;

    @Override
    public int insertMaterial(String materialCode, String materialName, String materialCategory, String materialSpecification,
                              String materialUnit, Integer materialShelfLifeDays, String materialStorageCondition, String materialRemark) {
        return materialInformationMapper.insertMaterial(
                materialCode, materialName, materialCategory, materialSpecification,
                materialUnit, materialShelfLifeDays, materialStorageCondition, materialRemark
        );
    }

    @Override
    public int deleteMaterial(Integer materialId) {
        return materialInformationMapper.deleteMaterial(materialId);
    }

    @Override
    public int updateMaterial(Integer materialId, String materialCode, String materialName, String materialCategory, String materialSpecification,
                              String materialUnit, Integer materialShelfLifeDays, String materialStorageCondition, String materialRemark) {
        return materialInformationMapper.updateMaterial(
                materialId, materialCode, materialName, materialCategory, materialSpecification,
                materialUnit, materialShelfLifeDays, materialStorageCondition, materialRemark
        );
    }
}
