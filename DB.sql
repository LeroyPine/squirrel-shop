create table admin_user_info
(
    id          int auto_increment comment '主键'
        primary key,
    user_id     varchar(64) not null comment '用户ID',
    user_name   varchar(64) not null comment '用户名称',
    user_pwd    varchar(64) not null comment '用户密码',
    avatar      varchar(64) null comment '头像',
    update_date datetime    not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '管理员信息表';

create table member_points
(
    id          int auto_increment comment '主键'
        primary key,
    user_id     decimal(10, 2) not null comment '金额',
    points      int            not null comment '积分',
    create_date datetime       not null comment '创建时间',
    update_date datetime       not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '会员积分表';

create table member_points_config
(
    id          int auto_increment comment '主键'
        primary key,
    money       decimal(10, 2) not null comment '金额',
    points      int            not null comment '积分',
    status      int            null comment '状态 1有效 0无效',
    create_date datetime       not null comment '创建时间',
    update_date datetime       not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '会员积分配置表';

create table member_points_history
(
    id            int auto_increment comment '主键'
        primary key,
    user_id       decimal(10, 2) not null comment '用户ID',
    change_type   int            not null comment '1:消费 2:兑奖',
    before_points int            not null comment '变更前积分',
    after_points  int            not null comment '变更后积分',
    change_desc   int            not null comment '变更描述',
    create_date   datetime       not null comment '创建时间',
    update_date   datetime       not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '会员积分历史表';

create table prize
(
    id          int auto_increment comment '主键'
        primary key,
    prize_id    varchar(64)  not null comment '奖品ID',
    prize_name  varchar(64)  not null comment '奖品名称',
    prize_image text         not null comment '奖品图片',
    prize_desc  varchar(255) not null comment '奖品描述',
    prize_num   int          not null comment '奖品数量',
    create_date datetime     not null comment '创建时间',
    update_date datetime     not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '奖品表';

create table prize_points_config
(
    id          int auto_increment comment '主键'
        primary key,
    prize_id    varchar(64) not null comment '奖品ID',
    points      int         not null comment '消耗积分',
    status      int         not null comment '奖品状态 1正常 0禁用',
    create_date datetime    not null comment '创建时间',
    update_date datetime    not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '奖品积分配置表';

create table role_info
(
    role_id     int auto_increment,
    role_name   varchar(64) not null,
    role_status int         not null comment '角色状态 0 禁用 1 启用',
    create_date datetime    null,
    update_date datetime    null on update CURRENT_TIMESTAMP,
    primary key (role_id, role_status)
)
    comment '角色信息表';

create table user_info
(
    id          int auto_increment comment '主键'
        primary key,
    user_id     varchar(64) not null comment '用户ID',
    user_name   varchar(64) not null comment '用户名称',
    user_pwd    varchar(64) null comment '用户密码',
    avatar      varchar(64) null comment '头像',
    phone       varchar(32) null comment '手机号',
    create_date datetime    not null comment '创建时间',
    update_date datetime    not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '用户信息表';

create table user_role
(
    id          int auto_increment
        primary key,
    user_id     varchar(64) not null comment '用户id',
    role_id     int         not null comment '角色id',
    create_date datetime    not null,
    update_date datetime    not null on update CURRENT_TIMESTAMP
)
    comment '用户角色表';

