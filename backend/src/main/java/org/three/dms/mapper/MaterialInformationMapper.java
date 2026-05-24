package org.three.dms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.three.dms.entity.MaterialInformation;

@Mapper
public interface MaterialInformationMapper extends BaseMapper<MaterialInformation> {
    // 新增物料
    @Insert("INSERT INTO material_information(" +
            "material_code, material_name, material_category, material_specification, " +
            "material_unit, material_shelf_life_days, material_storage_condition, material_remark) " +
            "VALUES(#{materialCode}, #{materialName}, #{materialCategory}, #{materialSpecification}, " +
            "#{materialUnit}, #{materialShelfLifeDays}, #{materialStorageCondition}, #{materialRemark})")
    int insertMaterial(
            @Param("materialCode") String materialCode,
            @Param("materialName") String materialName,
            @Param("materialCategory") String materialCategory,
            @Param("materialSpecification") String materialSpecification,
            @Param("materialUnit") String materialUnit,
            @Param("materialShelfLifeDays") Integer materialShelfLifeDays,
            @Param("materialStorageCondition") String materialStorageCondition,
            @Param("materialRemark") String materialRemark
    );

    // 删除物料（按主键material_id）
    @Delete("DELETE FROM material_information WHERE material_id = #{materialId}")
    int deleteMaterial(@Param("materialId") Integer materialId);

    // 修改物料（不修改主键）
    @Update("UPDATE material_information SET " +
            "material_code = #{materialCode}, material_name = #{materialName}, material_category = #{materialCategory}, " +
            "material_specification = #{materialSpecification}, material_unit = #{materialUnit}, " +
            "material_shelf_life_days = #{materialShelfLifeDays}, material_storage_condition = #{materialStorageCondition}, " +
            "material_remark = #{materialRemark} " +
            "WHERE material_id = #{materialId}")
    int updateMaterial(
            @Param("materialId") Integer materialId,
            @Param("materialCode") String materialCode,
            @Param("materialName") String materialName,
            @Param("materialCategory") String materialCategory,
            @Param("materialSpecification") String materialSpecification,
            @Param("materialUnit") String materialUnit,
            @Param("materialShelfLifeDays") Integer materialShelfLifeDays,
            @Param("materialStorageCondition") String materialStorageCondition,
            @Param("materialRemark") String materialRemark
    );
}
