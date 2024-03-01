# 数据库初始化
-- 创建库
create database if not exists nowcode_db;

-- 切换库
use nowcode_db;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userEmail    varchar(256)                           null comment '邮箱',
    userPhone    varchar(256)                           null comment '手机号',
    accessKey    varchar(256)                           null comment 'accessKey',
    secretKey    varchar(256)                           null comment 'secretKey',
    balance      bigint       default 30                not null comment '钱包余额,注册送30币',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 图表表
create table if not exists chart
(
    id          bigint auto_increment comment 'id' primary key,
    goal        text                               null comment '分析目标',
    `name`      varchar(128)                       null comment '图表名称',
    chartData   text                               null comment '图表数据',
    chartType   varchar(128)                       null comment '图表类型',
    genChart    text                               null comment '生成的图表数据',
    genResult   text                               null comment '生成的分析结论',
    status      varchar(128)                       not null default 'wait' comment 'wait,running,succeed,failed',
    execMessage text                               null comment '执行信息',
    userId      bigint                             null comment '创建用户 id',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除'
) comment '图表信息表' collate = utf8mb4_unicode_ci;


create table if not exists interface_info
(
    id             bigint auto_increment comment 'id' primary key,
    name           varchar(256)                           not null comment '接口名称',
    url            varchar(256)                           not null comment '接口地址',
    userId         bigint                                 null comment '发布人',
    method         varchar(256)                           not null comment '请求方法',
    requestParams  text                                   null comment '接口请求参数',
    responseParams text                                   null comment '接口响应参数',
    reduceScore    bigint       default 0                 null comment '扣除积分数',
    requestExample text                                   null comment '请求示例',
    requestHeader  text                                   null comment '请求头',
    responseHeader text                                   null comment '响应头',
    returnFormat   varchar(512) default 'JSON'            null comment '返回格式(JSON等等)',
    description    varchar(256)                           null comment '描述信息',
    status         tinyint      default 0                 not null comment '接口状态（0- 默认下线 1- 上线）',
    totalInvokes   bigint       default 0                 not null comment '接口总调用次数',
    avatarUrl      varchar(1024)                          null comment '接口头像',
    createTime     datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete       tinyint      default 0                 not null comment '是否删除'
)
    comment '接口信息';

-- 用户接口调用表
create table if not exists user_interface_invoke
(
    id           bigint auto_increment comment 'id' primary key,
    userId       bigint                             not null comment '调用人id',
    interfaceId  bigint                             not null comment '接口id',
    totalInvokes bigint   default 0                 not null comment '总调用次数',
    status       tinyint  default 0                 not null comment '调用状态（0- 正常 1- 封号）',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除'
)
    comment '用户接口调用表';