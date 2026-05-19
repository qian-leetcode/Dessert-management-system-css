create database if not exists desserts;

use desserts;

# 甜品种类表
create table if not exists category(
    id int auto_increment primary key comment '甜品种类ID',  -- 主键
    name varchar(100) not null comment '甜品种类名称', -- 种类名称
    description varchar(500) default '' comment '甜品种类描述' -- 甜品种类描述
) comment '甜品种类表';

# 甜品表
create table if not exists dessert(
    id int auto_increment primary key comment '甜品ID', -- 编号
    name varchar(100) not null comment '甜品名称', -- 名称
    photoUrl varchar(500) default '' comment '甜品图片url', -- 图片地址
    price double comment '甜品价格', -- 价格
    description varchar(500) default '' comment '甜品描述', -- 甜品描述
    release_date date comment '甜品发布时间', -- 发布时间
    cat_id int references category(id)
) comment '甜品表';

# 员工信息表
CREATE TABLE if not exists t_user (
    id INT AUTO_INCREMENT PRIMARY KEY comment '员工ID',    -- 员工编号  自增长
    name VARCHAR(100) NOT NULL comment '员工姓名',           -- 员工姓名
    gender CHAR(1) comment '',                        -- 性别
    phone VARCHAR(20) default '' comment '联系电话',                    -- 联系电话
    username VARCHAR(200) NOT NULL comment '账号用户名',       -- 登录用户名
    password VARCHAR(200) NOT NULL comment '登录密码',       -- 登录密码，经BCrypt算法加密(不显示)
    position VARCHAR(50) comment '岗位职称名',                 -- 岗位职位
    active INT(1) DEFAULT 0 comment '是否具有管理权限',              -- 1用户可用，0 用户不可用(不显示 ，管理员显示并进行修改)
                                        -- 可用为可编辑，不可用为不可编辑
    hire_date DATE comment '入职日期',                       -- 入职日期
    shift VARCHAR(20) comment '上班班次'                   -- 上班班次
) comment '员工信息表';

# 职称信息表
create table if not exists t_role(
    id int auto_increment primary key comment '职称ID', -- 编号
    role varchar(200) comment '职称名称', -- 角色名
    role_permissions VARCHAR(20) comment '职称权限'                     -- 角色权限 仅 老板 店长 为管理员
) comment '职称信息表';

# 员工职称表
create table if not exists t_user_role(
    id int auto_increment primary key,  -- 编号
    user_id int references t_user(id),  -- 引用用户
    role_id int references t_role(id)   -- 引用角色
) comment '员工职称表';

# 原材料信息表
create table if not exists material_information(
    material_id int unsigned not null auto_increment comment '原料ID',
    material_code varchar(30) not null comment '原料编码',
    material_name varchar(100) not null comment '原料名称',
    material_category varchar(50) default '' comment '原料分类',
    material_specification varchar(100) default '' comment '规格',
    material_unit varchar(20) not null comment '计量单位',
    material_shelf_life_days int unsigned default null comment '保质期',
    material_storage_condition varchar(255) default '' comment '存储条件',
    material_remark varchar(255) default '' comment '备注',
    primary key (material_id)
)comment '原料信息表';

# 库存表
create table if not exists material_inventory(
    inventory_id int unsigned not null auto_increment comment '库存ID',
    material_id int unsigned not null comment '原料ID',
    current_inventory_level decimal(10 , 2) not null default 0.00 comment '当前库存量',
    safety_stock_quantity decimal(10 , 2) not null default 0.00 comment '安全库存数量',
    Last_purchase_time date default null comment '最后采购时间',
    primary key (inventory_id)
)comment '原料存储表';

# 采购信息表
create table if not exists purchase_record (
    purchase_id int unsigned not null auto_increment comment '采购记录ID',
    purchase_order_number varchar(30) not null comment '采购单号',
    purchase_date date not null comment '采购日期',
    material_id int unsigned not null comment '原料ID',
    purchase_quantity decimal(10 , 2) not null comment '采购数量',
    purchase_price decimal(10 , 2) not null comment '采购单价',
    purchase_amount decimal(10 , 2) not null comment '总金额',
    supplier_name varchar(100) default '' comment '供应商名称',
    production_batch varchar(50) default '' comment '生产批次',
    production_date date default null comment '生产日期',
    payment_status tinyint unsigned not null default 0 comment '付款状态：0-未付 , 1-部分支付 ，2-已付清',
    procuring_entity varchar(50) default '' comment '采购人',
    remark varchar(50) default '' comment '备注',
    create_time datetime not null default current_timestamp comment '创建时间',
    primary key (purchase_id)

) comment '采购信息表';