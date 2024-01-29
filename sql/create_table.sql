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

-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 帖子点赞表（硬删除）
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子点赞';

-- 帖子收藏表（硬删除）
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子收藏';

insert into interface_info(`id`, `name`, `url`, `userId`, `method`, `requestParams`, `reduceScore`, `requestExample`,
                                  `requestHeader`, `responseHeader`, `description`, `status`, `totalInvokes`, `avatarUrl`,
                                  `returnFormat`, `responseParams`, `createTime`, `updateTime`, `isDelete`)
values (1705234447153963010, '随机毒鸡汤', 'https://gateway.qimuu.icu/api/poisonousChickenSoup', 1698354419367571457, 'GET',
        NULL, 1, 'https://gateway.qimuu.icu/api/poisonousChickenSoup', NULL, NULL, '随机毒鸡汤', 1, 228, '', 'JSON',
        '[{\"id\":\"1695051685885\",\"fieldName\":\"code\",\"type\":\"int\",\"desc\":\"响应码\"},{\"id\":\"1695052930602\",\"fieldName\":\"data.text\",\"type\":\"string\",\"desc\":\"随机毒鸡汤\"},{\"id\":\"1695052955781\",\"fieldName\":\"message\",\"type\":\"string\",\"desc\":\"响应描述\"}]',
        '2023-09-22 22:55:48', '2023-09-23 11:52:06', 0),
       (1705237104270712833, '获取输入的名称', 'https://gateway.qimuu.icu/api/name', 1698354419367571457, 'GET',
        '[{\"id\":\"1695031845159\",\"fieldName\":\"name\",\"type\":\"string\",\"desc\":\"输入的名称\",\"required\":\"是\"}]',
        1, 'https://gateway.qimuu.icu/api/name?name=zhangshan', NULL, NULL, '获取输入的名称', 1, 36,
        'https://img.qimuu.icu/interface_avatar/1699981437456797697/XqT3Nsto-psc.jfif', 'JSON',
        '[{\"id\":\"1695105888173\",\"fieldName\":\"data.name\",\"type\":\"object\",\"desc\":\"输入的参数\"},{\"id\":\"1695382937817\",\"fieldName\":\"code\",\"type\":\"int\",\"desc\":\"响应码\"},{\"id\":\"1695382949291\",\"fieldName\":\"message\",\"type\":\"string\",\"desc\":\"响应信息描述\"}]',
        '2023-09-22 23:06:22', '2023-09-23 11:52:06', 0),
       (1705237990061580289, '随机壁纸', 'https://gateway.qimuu.icu/api/randomWallpaper', 1698354419367571457, 'GET',
        '[{\"id\":\"1695032007961\",\"fieldName\":\"method\",\"type\":\"string\",\"desc\":\"输出壁纸端[mobile|pc|zsy]默认为pc\",\"required\":\"否\"},{\"id\":\"1695032018924\",\"fieldName\":\"lx\",\"type\":\"string\",\"desc\":\"选择输出分类[meizi|dongman|fengjing|suiji]，为空随机输出\",\"required\":\"否\"}]',
        1, 'https://gateway.qimuu.icu/api/randomWallpaper?lx=dongman', NULL, NULL, '获取随机壁纸', 1, 97,
        'https://img.qimuu.icu/typory/logo.jpg', 'JSON',
        '[{\"id\":\"1695051751595\",\"fieldName\":\"code\",\"type\":\"string\",\"desc\":\"响应码\"},{\"id\":\"1695051832571\",\"fieldName\":\"data.imgurl\",\"type\":\"string\",\"desc\":\"返回的壁纸地址\"},{\"id\":\"1695051861456\",\"fieldName\":\"message\",\"type\":\"string\",\"desc\":\"响应消息\"}]',
        '2023-09-22 23:09:53', '2023-09-23 11:52:06', 0),
       (1705238841173942274, '每日星座运势', 'https://gateway.qimuu.icu/api/horoscope', 1698354419367571457, 'GET',
        '[{\"id\":\"1695382662346\",\"fieldName\":\"type\",\"type\":\"string\",\"desc\":\"十二星座对应英文小写，aries, taurus, gemini, cancer, leo, virgo, libra, scorpio, sagittarius, capricorn, aquarius, pisces\",\"required\":\"是\"},{\"id\":\"1695382692472\",\"fieldName\":\"time\",\"type\":\"string\",\"desc\":\"今日明日一周等运势,today, nextday, week, month, year, love\",\"required\":\"是\"}]',
        1, 'https://gateway.qimuu.icu/api/horoscope?type=scorpio&time=nextday', NULL, NULL, '获取每日星座运势', 1, 23,
        'https://img.qimuu.icu/interface_avatar/1698354419367571457/r2X9jsoT-horoscope2.png', 'JSON',
        '[{\"id\":\"1695382701088\",\"fieldName\":\"code\",\"type\":\"int\",\"desc\":\"响应码\"},{\"id\":\"1695382720309\",\"fieldName\":\"data.todo.yi\",\"type\":\"string\",\"desc\":\"宜做\"},{\"id\":\"1695382741895\",\"fieldName\":\"data.todo.ji\",\"type\":\"string\",\"desc\":\"忌做\"},{\"id\":\"1695382783855\",\"fieldName\":\"data.fortunetext.all\",\"type\":\"string\",\"desc\":\"整体运势\"},{\"id\":\"1695382810906\",\"fieldName\":\"data.fortunetext.love\",\"type\":\"string\",\"desc\":\"爱情运势\"},{\"id\":\"1695382836727\",\"fieldName\":\"data.fortunetext.work\",\"type\":\"string\",\"desc\":\"工作运势\"},{\"id\":\"1695382864966\",\"fieldName\":\"data.fortunetext.money\",\"type\":\"string\",\"desc\":\"财运运势\"},{\"id\":\"1695382888169\",\"fieldName\":\"data.fortunetext.health\",\"type\":\"string\",\"desc\":\"健康运势\"},{\"id\":\"1695382912920\",\"fieldName\":\"data.fortune.all\",\"type\":\"int\",\"desc\":\"整体运势评分\"},{\"id\":\"1695382931057\",\"fieldName\":\"data.fortune.love\",\"type\":\"int\",\"desc\":\"爱情运势评分\"},{\"id\":\"1695382952540\",\"fieldName\":\"data.fortune.work\",\"type\":\"int\",\"desc\":\"工作运势评分\"},{\"id\":\"1695382975321\",\"fieldName\":\"data.fortune.money\",\"type\":\"int\",\"desc\":\"财运运势评分\"},{\"id\":\"1695382999222\",\"fieldName\":\"data.fortune.health\",\"type\":\"int\",\"desc\":\"健康运势评分\"},{\"id\":\"1695383027074\",\"fieldName\":\"data.shortcomment\",\"type\":\"string\",\"desc\":\"简评\"},{\"id\":\"1695383057554\",\"fieldName\":\"data.luckycolor\",\"type\":\"string\",\"desc\":\"幸运颜色\"},{\"id\":\"1695383079261\",\"fieldName\":\"data.index.all\",\"type\":\"string\",\"desc\":\"整体指数\"},{\"id\":\"1695383102324\",\"fieldName\":\"data.index.love\",\"type\":\"string\",\"desc\":\"爱情指数\"},{\"id\":\"1695383125487\",\"fieldName\":\"data.index.work\",\"type\":\"string\",\"desc\":\"工作指数\"},{\"id\":\"1695383149310\",\"fieldName\":\"data.index.money\",\"type\":\"string\",\"desc\":\"财运指数\"},{\"id\":\"1695383173441\",\"fieldName\":\"data.index.health\",\"type\":\"string\",\"desc\":\"健康指数\"},{\"id\":\"1695383203920\",\"fieldName\":\"data.luckynumber\",\"type\":\"string\",\"desc\":\"幸运数字\"},{\"id\":\"1695383227323\",\"fieldName\":\"data.time\",\"type\":\"string\",\"desc\":\"日期\"},{\"id\":\"1695383247152\",\"fieldName\":\"data.title\",\"type\":\"string\",\"desc\":\"星座名称\"},{\"id\":\"1695383269015\",\"fieldName\":\"data.type\",\"type\":\"string\",\"desc\":\"运势类型\"},{\"id\":\"1695383292088\",\"fieldName\":\"data.luckyconstellation\",\"type\":\"string\",\"desc\":\"幸运星座\"},{\"id\":\"1695383314942\",\"fieldName\":\"message\",\"type\":\"string\",\"desc\":\"响应描述\"}]',
        '2023-09-22 23:13:16', '2023-09-23 11:52:06', 0),
       (1705239469589733378, '随机土味情话', 'https://gateway.qimuu.icu/api/loveTalk', 1698354419367571457, 'GET', NULL, 1,
        'https://gateway.qimuu.icu/api/loveTalk', NULL, NULL, '获取土味情话', 1, 413,
        'https://img.qimuu.icu/interface_avatar/1698354419367571457/g8FTal0P-love.png', 'JSON',
        '[{\"id\":\"1695382126371\",\"fieldName\":\"code\",\"type\":\"int\",\"desc\":\"响应码\"},{\"id\":\"1695382173816\",\"fieldName\":\"data.value\",\"type\":\"string\",\"desc\":\"随机土味情话\"},{\"id\":\"1695382205869\",\"fieldName\":\"message\",\"type\":\"string\",\"desc\":\"返回信息描述\"}]',
        '2023-09-22 23:15:46', '2023-09-23 11:52:06', 0),
       (1705239928861827073, '获取IP信息归属地', 'https://gateway.qimuu.icu/api/ipInfo', 1698354419367571457, 'GET',
        '[{\"id\":\"1695388304868\",\"fieldName\":\"ip\",\"type\":\"string\",\"desc\":\"输入IP地址\",\"required\":\"是\"}]',
        1, 'https://gateway.qimuu.icu/api/ipInfo?ip=58.154.0.0', NULL, NULL, '获取IP信息归属地详细版', 1, 59,
        'https://img.qimuu.icu/interface_avatar/1698354419367571457/6DPoYYZe-ipInfo.png', 'JSON',
        '[{\"id\":\"1695382701088\",\"fieldName\":\"code\",\"type\":\"int\",\"desc\":\"响应码\"},{\"id\":\"1695382720309\",\"fieldName\":\"data.ip\",\"type\":\"string\",\"desc\":\"IP地址\"},{\"id\":\"1695382741895\",\"fieldName\":\"data.info.country\",\"type\":\"string\",\"desc\":\"国家\"},{\"id\":\"1695382783855\",\"fieldName\":\"data.info.prov\",\"type\":\"string\",\"desc\":\"省份\"},{\"id\":\"1695382810906\",\"fieldName\":\"data.info.city\",\"type\":\"string\",\"desc\":\"城市\"},{\"id\":\"1695382836727\",\"fieldName\":\"data.info.lsp\",\"type\":\"string\",\"desc\":\"运营商\"},{\"id\":\"1695382864966\",\"fieldName\":\"message\",\"type\":\"string\",\"desc\":\"响应描述\"}]',
        '2023-09-22 23:17:35', '2023-09-23 12:18:29', 0),
       (1705240565347459073, '获取天气信息', 'https://gateway.qimuu.icu/api/weather', 1698354419367571457, 'GET',
        '[{\"id\":\"1695392098947\",\"fieldName\":\"city\",\"type\":\"string\",\"desc\":\"输入城市或县区\",\"required\":\"否\"},{\"id\":\"1695392118560\",\"fieldName\":\"ip\",\"type\":\"string\",\"desc\":\"输入IP\",\"required\":\"否\"},{\"id\":\"1695392127763\",\"fieldName\":\"type\",\"type\":\"string\",\"desc\":\"默认一天，可配置 week获取周\",\"required\":\"否\"}]',
        1, 'https://gateway.qimuu.icu/api/weather?ip=180.149.130.16', NULL, NULL, '获取每日每周的天气信息', 1, 109,
        'https://img.qimuu.icu/interface_avatar/1698354419367571457/gYNay1Y0-weather.png', 'JSON',
        '[\n  {\"id\":\"1695382701088\",\"fieldName\":\"code\",\"type\":\"int\",\"desc\":\"响应码\"},\n  {\"id\":\"1695382720309\",\"fieldName\":\"data.city\",\"type\":\"string\",\"desc\":\"城市\"},\n  {\"id\":\"1695382741895\",\"fieldName\":\"data.info.date\",\"type\":\"string\",\"desc\":\"日期\"},\n  {\"id\":\"1695382783855\",\"fieldName\":\"data.info.week\",\"type\":\"string\",\"desc\":\"星期几\"},\n  {\"id\":\"1695382810906\",\"fieldName\":\"data.info.type\",\"type\":\"string\",\"desc\":\"天气类型\"},\n  {\"id\":\"1695382836727\",\"fieldName\":\"data.info.low\",\"type\":\"string\",\"desc\":\"最低温度\"},\n  {\"id\":\"1695382864966\",\"fieldName\":\"data.info.high\",\"type\":\"string\",\"desc\":\"最高温度\"},\n  {\"id\":\"1695382892007\",\"fieldName\":\"data.info.fengxiang\",\"type\":\"string\",\"desc\":\"风向\"},\n  {\"id\":\"1695382918740\",\"fieldName\":\"data.info.fengli\",\"type\":\"string\",\"desc\":\"风力\"},\n  {\"id\":\"1695382951685\",\"fieldName\":\"data.info.night.type\",\"type\":\"string\",\"desc\":\"夜间天气类型\"},\n  {\"id\":\"1695382977506\",\"fieldName\":\"data.info.night.fengxiang\",\"type\":\"string\",\"desc\":\"夜间风向\"},\n  {\"id\":\"1695383004749\",\"fieldName\":\"data.info.night.fengli\",\"type\":\"string\",\"desc\":\"夜间风力\"},\n  {\"id\":\"1695383032988\",\"fieldName\":\"data.info.air.aqi\",\"type\":\"int\",\"desc\":\"空气质量指数\"},\n  {\"id\":\"1695383052210\",\"fieldName\":\"data.info.air.aqi_level\",\"type\":\"int\",\"desc\":\"空气质量指数级别\"},\n  {\"id\":\"1695383072789\",\"fieldName\":\"data.info.air.aqi_name\",\"type\":\"string\",\"desc\":\"空气质量指数名称\"},\n  {\"id\":\"1695383098609\",\"fieldName\":\"data.info.air.co\",\"type\":\"string\",\"desc\":\"一氧化碳浓度\"},\n  {\"id\":\"1695383124245\",\"fieldName\":\"data.info.air.no2\",\"type\":\"string\",\"desc\":\"二氧化氮浓度\"},\n  {\"id\":\"1695383149267\",\"fieldName\":\"data.info.air.o3\",\"type\":\"string\",\"desc\":\"臭氧浓度\"},\n  {\"id\":\"1695383173716\",\"fieldName\":\"data.info.air.pm10\",\"type\":\"string\",\"desc\":\"PM10浓度\"},\n  {\"id\":\"1695383198557\",\"fieldName\":\"data.info.air.pm25\",\"type\":\"string\",\"desc\":\"PM2.5浓度\"},\n  {\"id\":\"1695383222398\",\"fieldName\":\"data.info.air.so2\",\"type\":\"string\",\"desc\":\"二氧化硫浓度\"},\n  {\"id\":\"1695383249835\",\"fieldName\":\"data.info.tip\",\"type\":\"string\",\"desc\":\"提示信息\"},\n  {\"id\":\"1695383275472\",\"fieldName\":\"message\",\"type\":\"string\",\"desc\":\"响应描述\"}\n]',
        '2023-09-22 23:20:07', '2023-09-23 11:52:06', 0);