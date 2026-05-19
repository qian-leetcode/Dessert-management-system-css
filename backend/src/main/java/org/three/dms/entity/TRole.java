package org.three.dms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_role")
public class TRole {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String role_name;
    private String role_permission;
}
