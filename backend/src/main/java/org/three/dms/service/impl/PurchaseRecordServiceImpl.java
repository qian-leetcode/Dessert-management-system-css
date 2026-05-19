package org.three.dms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.three.dms.entity.PurchaseRecord;
import org.three.dms.mapper.PurchaseRecordMapper;
import org.three.dms.service.PurchaseRecordService;

@Service
public class PurchaseRecordServiceImpl extends ServiceImpl<PurchaseRecordMapper, PurchaseRecord> implements PurchaseRecordService {
}
