package org.three.dms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_user_role")
public class TUserRole {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer user_id;
    private Integer role_id;
}
