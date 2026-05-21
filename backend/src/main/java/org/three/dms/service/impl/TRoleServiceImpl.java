package org.three.dms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.three.dms.entity.TRole;
import org.three.dms.mapper.TRoleMapper;
import org.three.dms.service.TRoleService;

import java.util.List;

@Service
public class TRoleServiceImpl extends ServiceImpl<TRoleMapper,TRole> implements TRoleService {
    @Autowired
    private TRoleMapper tRoleMapper;

    @Override
    public List<String> get_role(){
        return tRoleMapper.get_role();
    }
}
