create database if not exists desserts;

use desserts;
           # 甜品种类表
create table if not exists category(
    id int auto_increment primary key,  -- 主键
    name varchar(100) not null, -- 种类名称
    description varchar(500) -- 甜品种类描述
);

# 甜品表
create table if not exists dessert(
    id int auto_increment primary key, -- 编号
    name varchar(100) not null, -- 名称
    photoUrl varchar(500), -- 图片地址
    price double, -- 价格
    description varchar(500), -- 甜品描述
    release_date date, -- 发布时间
    cat_id int references category(id)
    );

# 员工信息表
CREATE TABLE if not exists t_user (
    id INT AUTO_INCREMENT PRIMARY KEY,    -- 员工编号  自增长
    name VARCHAR(100) NOT NULL,           -- 员工姓名
    gender CHAR(1),                        -- 性别
    phone VARCHAR(20),                    -- 联系电话
    username VARCHAR(200) NOT NULL,       -- 登录用户名
    password VARCHAR(200) NOT NULL,       -- 登录密码，经BCrypt算法加密(不显示)
    position VARCHAR(50),                 -- 岗位职位
    active INT(1) DEFAULT 1,              -- 1用户可用，0 用户不可用(不显示 ，管理员显示并进行修改)
                                        -- 可用为可编辑，不可用为不可编辑
    hire_date DATE,                       -- 入职日期
    shift VARCHAR(20)                    -- 上班班次
    );

# 职称信息表
create table if not exists t_role(
    id int auto_increment primary key, -- 编号
    role varchar(200), -- 角色名
    role_permissions VARCHAR(20)                      -- 角色权限 仅 老板 店长 为管理员
    );

# 员工职称表
create table if not exists t_user_role(
    id int auto_increment primary key, # 编号
    user_id int references t_user(id),  # 引用用户
    role_id int references t_role(id)   # 引用角色
    );

# 原材料信息表

# 库存表

# 采购信息表