package org.three.dms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.three.dms.entity.MaterialInventory;
import org.three.dms.service.MaterialInventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/material_inventory")
public class MaterialInventoryController {
    @Autowired
    private MaterialInventoryService materialInventoryService;

    @GetMapping("/list")
    public List<MaterialInventory> list(){
//        List<MaterialInventory> users = materialInventoryService.list();
//        System.out.println("users = " + users);
        return materialInventoryService.list();
    }
}
