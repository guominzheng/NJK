
Drop TABLE if EXISTS `wx_classroom_user`;
create table `wx_classroom_user`(
cr_userid BIGINT PRIMARY KEY auto_increment,
cr_userName VARCHAR(20) not null comment '用户名',
cr_userImg VARCHAR(50) comment '头像',
cr_userLoginPass VARCHAR(25) not null comment '登录名',
cr_userLoginName VARCHAR(25) not null comment '登录密码',
cr_userBalance DOUBLE DEFAULT 0 comment '余额',
cr_teacherStatus INT DEFAULT 0 comment '是否是老师',
cr_userPhone VARCHAR(20) COMMENT '用户手机',
cr_createTime DATETIME COMMENT '时间戳',
cr_updataTime DATETIME
) COMMENT='小程序课程用户表';
-- 直播表
Drop TABLE if EXISTS `wx_classroom_live`;
create table `wx_classroom_live`(
live_id BIGINT PRIMARY KEY auto_increment,
live_name  VARCHAR(20),
live_text  VARCHAR(255),
live_img   VARCHAR(100),
live_roomId BIGINT comment '管理 房间 ',
createTime datetime,
beginTime  datetime,
updateTime datetime
);
--  直播内容保存 表
Drop TABLE if EXISTS `wx_classroom_record`;
create table `wx_classroom_record`(
record_id BIGINT PRIMARY KEY auto_increment,
record_message VARCHAR(255),
record_userId  BIGINT,
createTime datetime,
updateTime datetime,
audio_text INT,
record_liveId BIGINT comment'关联某一次直播 '
);

-- 房间表
Drop TABLE if EXISTS `wx_classroom_room`;
create table `wx_classroom_room`(
room_id BIGINT  PRIMARY KEY auto_increment,
room_name VARCHAR(50),
room_discribe VARCHAR(255),
room_userId  BIGINT  comment '关联某个讲师',
createTime datetime,
updateTime datetime
);

-- 关注表
Drop TABLE if EXISTS `wx_classroom_attention`;
create table `wx_classroom_attention`(
att_id  BIGINT PRIMARY KEY auto_increment,
userId BIGINT,
roomId BIGINT,
createTime datetime
);


-- 购买课程表
Drop TABLE if EXISTS `wx_classroom_pay`;
create table `wx_classroom_pay`(
pay_id  BIGINT PRIMARY KEY auto_increment,
userId BIGINT,
liveId BIGINT,
createTime datetime
);
--  最近浏览
Drop TABLE if EXISTS `wx_classroom_attend`;
create table `wx_classroom_attend`(
attend_id  BIGINT PRIMARY KEY auto_increment,
userId BIGINT,
liveId BIGINT,
createTime datetime
);

-- 房间评论
Drop TABLE if EXISTS `wx_classroom_critical`;
create table `wx_classroom_critical`(
cal_id  BIGINT PRIMARY KEY auto_increment,
cal_message  VARCHAR(255),
cal_roomId BIGINT,
cal_userId BIGINT,
createTime datetime
);