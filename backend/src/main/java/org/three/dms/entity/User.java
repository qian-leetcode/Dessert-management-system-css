package org.three.dms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;


@Data
@TableName("t_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer user_id;

    private String user_name;
    private String user_gender;
    private String user_phone = "";
    private String username;
    private String password;
    private String user_position;
    private boolean user_active = false;
    private LocalDate user_hire_date;
    private String user_shift;

}
