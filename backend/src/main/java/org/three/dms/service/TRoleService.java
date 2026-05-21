package org.three.dms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.three.dms.entity.TRole;

import java.util.List;

public interface TRoleService extends IService<TRole> {
    List<String> get_role ();
}
