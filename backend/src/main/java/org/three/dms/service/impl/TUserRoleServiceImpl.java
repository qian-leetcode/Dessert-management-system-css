package org.three.dms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.three.dms.entity.TUserRole;
import org.three.dms.mapper.TUserRoleMapper;
import org.three.dms.service.TUserRoleService;

@Service
public class TUserRoleServiceImpl extends ServiceImpl<TUserRoleMapper, TUserRole> implements TUserRoleService {
}
