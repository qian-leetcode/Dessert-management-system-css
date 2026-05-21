package org.three.dms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.three.dms.entity.PurchaseRecord;
import org.three.dms.service.PurchaseRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/purchase_record")
public class PurchaseRecordController {
    @Autowired
    private PurchaseRecordService purchaseRecordService;

    @GetMapping("/list")
    public List<PurchaseRecord> list(){
        return purchaseRecordService.list();
    }
}
