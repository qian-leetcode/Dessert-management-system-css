# create database if not exists desserts DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
drop database if exists  dessert_three_groups;

create database if not exists dessert_three_groups DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use dessert_three_groups;

drop table if exists dessert;
drop table if exists t_user_role;
drop table if exists purchase_record;
drop table if exists material_inventory;
drop table if exists category;
drop table if exists t_user;
drop table if exists t_role;
drop table if exists material_information;

# 甜品种类表
create table if not exists category(
    id int auto_increment primary key comment '甜品种类ID',  -- 主键
    name varchar(100) not null comment '甜品种类名称', -- 种类名称
    description varchar(500) default '' comment '甜品种类描述' -- 甜品种类描述
) engine=InnoDB default charset=utf8mb4  comment '甜品种类表';

# 甜品表
create table if not exists dessert(
    id int auto_increment primary key comment '甜品ID', -- 编号
    name varchar(100) not null comment '甜品名称', -- 名称
    photo_url varchar(500) default '' comment '甜品图片url', -- 图片地址
    price double comment '甜品价格', -- 价格
    description varchar(500) default '' comment '甜品描述', -- 甜品描述
    release_date date comment '甜品发布时间', -- 发布时间
    cat_id int not null comment '所属种类ID',
    dessert_status tinyint(1) default 1 comment '当前状态：1-在售，0-下架',
    dessert_number int default 0 comment '甜品件数',
    CONSTRAINT fk_dessert_category FOREIGN KEY (cat_id) REFERENCES category(id)
        ON DELETE RESTRICT ON UPDATE CASCADE
) engine=InnoDB default charset=utf8mb4 comment '甜品表';

# 职称信息表
create table if not exists t_role(
    id int auto_increment primary key comment '职称ID', -- 编号
    role varchar(200) comment '职称名称', -- 角色名
    role_permissions varchar(100) default '' comment '角色权限'
) engine=InnoDB default charset=utf8mb4 comment '职称信息表';

# 员工信息表
CREATE table if not exists t_user (
    id INT AUTO_INCREMENT PRIMARY KEY comment '员工ID',    -- 员工编号  自增长
    name VARCHAR(100) NOT NULL comment '员工姓名',           -- 员工姓名
    gender CHAR(1) comment '性别：M-男，F-女',                        -- 性别
    phone VARCHAR(20) default '' comment '联系电话',                    -- 联系电话
    username VARCHAR(200) NOT NULL comment '账号用户名',       -- 登录用户名
    password VARCHAR(200) NOT NULL comment '登录密码',       -- 登录密码，经Bcrypt算法加密(不显示)
    position VARCHAR(50) comment '岗位职称名',                 -- 岗位职位
    hire_date DATE comment '入职日期',                       -- 入职日期
    shift VARCHAR(20) comment '上班班次',                   -- 上班班次
    constraint uk_username unique (username)              -- 用户名唯一
) engine=InnoDB default charset=utf8mb4 comment '员工信息表';

# 员工职称表
create table if not exists t_user_role(
    id int auto_increment primary key comment '记录ID',  -- 编号
    user_id INT NOT NULL COMMENT '员工ID',
    role_id INT NOT NULL COMMENT '职称ID',
    constraint fk_tur_user foreign key (user_id) references t_user(id)
        on delete cascade on update cascade ,
    constraint fk_tur_role foreign key (role_id) references t_role(id)
        on delete restrict on update cascade ,
    constraint uk_user_role UNIQUE (user_id, role_id)       -- 防止重复关联
) engine=InnoDB default charset=utf8mb4 comment '员工职称表';

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
) engine=InnoDB default charset=utf8mb4 comment '原料信息表';

# 库存表
create table if not exists material_inventory(
    inventory_id int unsigned not null auto_increment comment '库存ID',
    material_id int unsigned not null comment '原料ID',
    current_inventory_level double not null default 0.00 comment '当前库存量',
    safety_stock_quantity double not null default 0.00 comment '安全库存数量',
    last_purchase_time date default null comment '最后采购时间',
    primary key (inventory_id),
    constraint fk_inventory_material foreign key (material_id) references material_information(material_id)
        on delete cascade on update cascade
) engine=InnoDB default charset=utf8mb4 comment '原料存储表';

# 采购信息表
create table if not exists purchase_record (
    purchase_id int unsigned not null auto_increment comment '采购记录ID',
    purchase_order_number varchar(30) not null comment '采购单号',
    purchase_date date not null comment '采购日期',
    material_id int unsigned not null comment '原料ID',
    purchase_quantity double not null comment '采购数量',
    purchase_price double not null comment '采购单价',
    purchase_amount double not null comment '总金额',
    supplier_name varchar(100) default '' comment '供应商名称',
    production_batch varchar(50) default '' comment '生产批次',
    production_date date default null comment '生产日期',
    payment_status tinyint unsigned not null default 0 comment '付款状态：0-未付 , 1-部分支付 ，2-已付清',
    user_id int default null comment '采购人ID',
    procuring_entity varchar(50) default '' comment '采购人',
    remark varchar(255) default '' comment '备注',
    create_time datetime not null default current_timestamp comment '创建时间',
    primary key (purchase_id),
    constraint fk_purchase_material foreign key (material_id) references material_information(material_id)
        on delete restrict on update cascade ,
    constraint fk_purchase_user foreign key (user_id) references t_user(id)
        on delete set null on update cascade
) engine=InnoDB default charset=utf8mb4 comment '采购信息表';
#
# -- 创建索引
# create index index_material_id on purchase_record(material_id);