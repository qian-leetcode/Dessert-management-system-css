package org.three.dms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.three.dms.entity.MaterialInformation;
import org.three.dms.mapper.MaterialInformationMapper;
import org.three.dms.service.MaterialInformationService;

@Service
public class MaterialInformationServiceImpl extends ServiceImpl<MaterialInformationMapper , MaterialInformation> implements MaterialInformationService {
}
