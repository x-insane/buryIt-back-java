-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- 主机： 127.0.0.1:3306
-- 生成日期： 2018-11-24 08:37:53
-- 服务器版本： 5.7.19
-- PHP 版本： 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `buryit`
--

-- --------------------------------------------------------

--
-- 表的结构 `data`
--

DROP TABLE IF EXISTS `data`;
CREATE TABLE IF NOT EXISTS `data` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '素材id',
  `user_id` int(10) UNSIGNED NOT NULL COMMENT '创建者的用户id',
  `type` tinytext COLLATE utf8_bin NOT NULL COMMENT '素材类型，确定后将不能修改，text纯文本，voice录音，image图片',
  `package_id` int(10) UNSIGNED NOT NULL COMMENT '所属包的id，创建后不允许修改',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `delete_time` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- 表的结构 `data_image`
--

DROP TABLE IF EXISTS `data_image`;
CREATE TABLE IF NOT EXISTS `data_image` (
  `id` int(10) UNSIGNED NOT NULL COMMENT '与data表中的id对应',
  `image_url` text COLLATE utf8_bin NOT NULL COMMENT '图片url，最多65535字',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- 表的结构 `data_text`
--

DROP TABLE IF EXISTS `data_text`;
CREATE TABLE IF NOT EXISTS `data_text` (
  `id` int(11) UNSIGNED NOT NULL COMMENT '与data表中的id对应',
  `content` text COLLATE utf8_bin NOT NULL COMMENT '纯文本内容，可换行，最多不超过65535字',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- 表的结构 `data_voice`
--

DROP TABLE IF EXISTS `data_voice`;
CREATE TABLE IF NOT EXISTS `data_voice` (
  `id` int(10) UNSIGNED NOT NULL COMMENT '与data表中的id对应',
  `voice_url` text COLLATE utf8_bin NOT NULL COMMENT '录音url，最多65535字',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- 表的结构 `event`
--

DROP TABLE IF EXISTS `event`;
CREATE TABLE IF NOT EXISTS `event` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `relatedUserId` int(10) UNSIGNED NOT NULL,
  `packageId` int(10) UNSIGNED NOT NULL,
  `type` tinytext COLLATE utf8_bin NOT NULL,
  `data` json DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `package_id` (`packageId`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- 表的结构 `event_notification`
--

DROP TABLE IF EXISTS `event_notification`;
CREATE TABLE IF NOT EXISTS `event_notification` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `userId` int(10) UNSIGNED NOT NULL COMMENT '要通知的用户id',
  `eventId` int(11) UNSIGNED NOT NULL COMMENT '与之关联的事件id',
  `isRead` tinyint(1) DEFAULT '0' COMMENT '是否已读',
  `deleteTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `event_id` (`eventId`),
  KEY `user_id` (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- 表的结构 `file_upload`
--

DROP TABLE IF EXISTS `file_upload`;
CREATE TABLE IF NOT EXISTS `file_upload` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `userId` int(10) UNSIGNED NOT NULL,
  `path` tinytext COLLATE utf8_bin NOT NULL,
  `uploadTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- 表的结构 `package`
--

DROP TABLE IF EXISTS `package`;
CREATE TABLE IF NOT EXISTS `package` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `uuid` char(36) COLLATE utf8_bin NOT NULL COMMENT '包裹唯一识别码，防止被非法用户猜出id',
  `user_id` int(11) UNSIGNED NOT NULL COMMENT '创建者的用户id',
  `owner_id` int(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '所有者id',
  `title` tinytext COLLATE utf8_bin NOT NULL COMMENT '必须，255字以内标题，拆开前可见',
  `data_cache` json DEFAULT NULL COMMENT '打包后对内容的缓存，以免每次查很多表',
  `real_mode` tinyint(1) NOT NULL DEFAULT '1' COMMENT '真实模式，默认开启，真实模式下，只有在现实中走到对应位置附近才能取包裹',
  `type` tinytext COLLATE utf8_bin COMMENT '包裹类型，即循环方式，仅限free、private、fixed中的一种',
  `package_total_num` int(11) UNSIGNED DEFAULT NULL COMMENT '定点模式下包裹总数量，该值为0时不限制数量，为n表示可以被除创建者外的n人挖取',
  `package_open_num` int(11) UNSIGNED DEFAULT NULL COMMENT '定点模式下被挖取的数量',
  `cycle_time` int(11) DEFAULT NULL COMMENT '自由模式下的循环周期，单位分，挖到者必须在规定时间内重新埋下，否则回到上个埋下位置，其他人可以再次挖出',
  `description` tinytext COLLATE utf8_bin COMMENT '可选，255字以内简单描述，拆开前可见',
  `sender` tinytext COLLATE utf8_bin COMMENT '可选额外信息，展示的发方名字，255字以内',
  `receiver` tinytext COLLATE utf8_bin COMMENT '可选额外信息，展示的收方名字，255字以内',
  `longitude` double DEFAULT NULL COMMENT '当前位置经度',
  `latitude` double DEFAULT NULL COMMENT '当前位置纬度',
  `deep` int(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '可选深度，默认为0，可输入0-1000，正确填写深度才能找到这个包',
  `password` tinytext COLLATE utf8_bin COMMENT '可选密码，默认为NULL，加密之后，在用户拿到这个包后，只有输入正确的密码才能看到包内的数据',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '第一次创建的时间，此时包内还没有数据',
  `publish_time` timestamp NULL DEFAULT NULL COMMENT '打包的时间，一旦该值不为NULL，将不允许再修改',
  `can_open_time` timestamp NULL DEFAULT NULL COMMENT '能够打开的时间，若设置了该值，在此时间之前，不可以挖出',
  `free_deadline` timestamp NULL DEFAULT NULL COMMENT 'free型包裹在被其他人挖出后的失效时间',
  `delete_time` timestamp NULL DEFAULT NULL COMMENT '删除时间，一旦该值不为NULL，将对所有用户不可见，只有创建者能够删除',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- 表的结构 `package_user`
--

DROP TABLE IF EXISTS `package_user`;
CREATE TABLE IF NOT EXISTS `package_user` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `package_id` int(10) UNSIGNED NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `access` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否能访问包裹内容',
  PRIMARY KEY (`id`),
  UNIQUE KEY `package_user` (`package_id`,`user_id`) USING BTREE,
  KEY `package_id` (`package_id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- 表的结构 `token_openid`
--

DROP TABLE IF EXISTS `token_openid`;
CREATE TABLE IF NOT EXISTS `token_openid` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `token` char(32) COLLATE utf8_bin NOT NULL,
  `openid` tinytext COLLATE utf8_bin NOT NULL,
  `session_key` tinytext COLLATE utf8_bin NOT NULL,
  `data` json DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='openid用户所使用的token表，含有用户openid和session_key和session数据等信息';

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户名，微信用户初始为16位随机字符串',
  `password` tinytext COLLATE utf8_bin COMMENT '用户密码，openid用户无需设置',
  `nickname` tinytext COLLATE utf8_bin COMMENT '用户昵称',
  `avatar` tinytext COLLATE utf8_bin COMMENT '用户头像地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户注册时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '用户信息更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- 表的结构 `user_openid`
--

DROP TABLE IF EXISTS `user_openid`;
CREATE TABLE IF NOT EXISTS `user_openid` (
  `id` int(10) UNSIGNED NOT NULL COMMENT '与user表中用户id对应',
  `openid` tinytext COLLATE utf8_bin NOT NULL COMMENT '微信用户openid',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='使用openid的用户与用户id的对应表';

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
