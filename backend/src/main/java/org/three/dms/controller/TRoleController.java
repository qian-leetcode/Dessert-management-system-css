package org.three.dms.controller;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.three.dms.entity.TRole;
import org.three.dms.service.TRoleService;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@CrossOrigin(origins = "*")
public class TRoleController {
    @Resource
    private TRoleService tRoleService;

    @GetMapping("/list")
    public List<TRole> findAll(){
        return tRoleService.list();
    }

    @GetMapping("/role")
    public List<String> getRole(){ return tRoleService.get_role() ;}
}
