create table admin_user_info
(
    id          int auto_increment comment '主键'
        primary key,
    user_id     int          not null comment '用户ID',
    user_name   varchar(64)  not null comment '用户名称',
    nick_name   varchar(64)  null,
    user_pwd    varchar(64)  not null comment '用户密码',
    avatar      varchar(255) null comment '头像',
    update_date datetime     not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '管理员信息表';

create table member_points
(
    id          int auto_increment comment '主键'
        primary key,
    user_id     int                          not null comment '用户id',
    points      double(10, 1)                not null comment '积分',
    create_date datetime                     not null comment '创建时间',
    update_date datetime default (curtime()) not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '会员积分表';

create index idx_user_id
    on member_points (user_id);

create table member_points_config
(
    id          int auto_increment comment '主键'
        primary key,
    config_name varchar(64)                  null comment '配置名称',
    config_rule varchar(255)                 not null comment '配置规则',
    config_type int                          null comment '规则类型',
    status      int                          null comment '状态 1有效 0无效',
    create_date datetime                     not null comment '创建时间',
    update_date datetime default (curtime()) not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '会员积分配置表';

create table member_points_history
(
    id              int auto_increment comment '主键'
        primary key,
    user_id         int                          not null comment '用户ID',
    change_type     int                          not null comment '1:手动修改用户积分 2 :消费 3:兑奖 ',
    before_points   double(10, 2)                not null comment '变更前积分',
    after_points    double(10, 1)                not null comment '变更后积分',
    amount          double(10, 2)                null comment '消费金额',
    redeemed_points double(10, 1)                null comment '兑换积分',
    remark          varchar(255)                 null comment '备注',
    change_desc     varchar(255)                 null comment '变更描述',
    create_date     datetime                     not null comment '创建时间',
    update_date     datetime default (curtime()) not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '会员积分历史表';

create index idx_change_type
    on member_points_history (user_id, change_type);

create table member_points_history_detail
(
    id             int auto_increment
        primary key,
    history_id     int                          not null,
    product_id     varchar(64)                  null comment '商品ID',
    product_name   varchar(64)                  null comment '商品名称',
    product_num    int                          null comment '商品数量',
    product_points double(10, 1)                null comment '单个商品积分',
    product_money  double(10, 2)                null comment '单个商品金额',
    create_date    datetime                     null comment '创建时间',
    update_date    datetime default (curtime()) not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint un_idex_h_p_p
        unique (history_id, product_id, product_name)
)
    comment '会员积分历史明细表' collate = utf8mb4_bin;

create table prize
(
    id          int auto_increment comment '主键'
        primary key,
    prize_id    varchar(64)                  not null comment '奖品ID',
    prize_name  varchar(64)                  not null comment '奖品名称',
    prize_image varchar(255)                 not null comment '奖品图片',
    prize_desc  varchar(255)                 not null comment '奖品描述',
    prize_num   int                          not null comment '奖品数量',
    create_date datetime                     not null comment '创建时间',
    update_date datetime default (curtime()) not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint un_idx_prize_id
        unique (prize_id)
)
    comment '奖品表';

create index idx_prize_name
    on prize (prize_name);

create table prize_points_config
(
    id          int auto_increment comment '主键'
        primary key,
    prize_id    varchar(64)                  not null comment '奖品ID',
    points      int                          not null comment '消耗积分',
    status      int                          not null comment '奖品状态 1正常 0禁用',
    create_date datetime                     not null comment '创建时间',
    update_date datetime default (curtime()) not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '奖品积分配置表';

create table product
(
    id             int auto_increment comment '主键'
        primary key,
    product_id     varchar(64)                  not null comment '奖品ID',
    product_name   varchar(64)                  not null comment '奖品名称',
    product_status int                          not null comment '商品状态',
    product_image  varchar(255)                 null comment '奖品图片',
    product_desc   varchar(255)                 null comment '奖品描述',
    product_num    int                          null comment '奖品数量',
    product_money  double(10, 2)                null comment '产品金额',
    create_date    datetime                     not null comment '创建时间',
    update_date    datetime default (curtime()) not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint un_idx_poudct_id
        unique (product_id)
)
    comment '商品表';

create index idx_product_name
    on product (product_name);

create table role_info
(
    id          int auto_increment,
    role_id     varchar(16) not null,
    role_name   varchar(64) not null,
    role_status int         not null comment '角色状态 0 禁用 1 启用',
    create_date datetime    null,
    update_date datetime    null on update CURRENT_TIMESTAMP,
    primary key (id, role_status),
    constraint un_idx_role_id
        unique (role_id)
)
    comment '角色信息表';

create table user_info
(
    user_id     int auto_increment comment '用户ID'
        primary key,
    user_name   varchar(64)                  not null comment '用户名称',
    user_pwd    varchar(64)                  null comment '用户密码',
    avatar      varchar(255)                 null comment '头像',
    phone       varchar(32)                  null comment '手机号',
    address     varchar(64)                  null comment '用户地址',
    create_date datetime                     not null comment '创建时间',
    update_date datetime default (curtime()) not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint un_name_phone
        unique (user_name, phone)
)
    comment '用户信息表';

create index idx_phone
    on user_info (phone);

create table user_role
(
    id          int auto_increment
        primary key,
    user_id     int         not null comment '用户id',
    role_id     varchar(16) not null comment '角色id',
    create_date datetime    not null,
    update_date datetime    not null on update CURRENT_TIMESTAMP
)
    comment '用户角色表';

