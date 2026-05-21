package org.three.dms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.three.dms.entity.Dessert;
import org.three.dms.entity.MaterialInventory;
import org.three.dms.service.DessertService;


import java.util.List;

@RestController
@RequestMapping("/api/dessert")
public class DessertController {
    @Autowired
    private DessertService dessertService;

    @GetMapping("/list")
    public List<Dessert> list(){
        return dessertService.list();
    }
}
