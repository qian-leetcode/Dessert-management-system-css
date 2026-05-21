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
    private Integer id;

    private String name;
    private String gender;
    private String phone = "";
    private String username;
    private String password;
    private String position;
    private boolean active = false;
    private LocalDate hire_date;
    private String shift;

}
