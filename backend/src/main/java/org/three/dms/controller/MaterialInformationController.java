package org.three.dms.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.three.dms.entity.MaterialInformation;
import org.three.dms.service.MaterialInformationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/material_information")
public class MaterialInformationController {
    @Autowired
    private MaterialInformationService materialInformationService;

    @GetMapping("/list")
    public List<MaterialInformation> list(){
        log.info("list");
        return materialInformationService.list();
    }
}
