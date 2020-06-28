# 基础介绍

| 说明     | 内容 |
| :------- | ---- |
| 项目名   | elf_url |
| 作者     | quan666 |
| 数据库IP | 127.0.0.1 |
| 数据库名 | elf_url |

## cipher表结构说明
| 代码字段名 | 字段名 | 数据类型（代码） | 数据类型 | 长度 | NullAble | 注释 |
| :--------- | ------ | ---------------- | -------- | ---- | -------------- | ---- |
| id | id | Long | bigint | 19 | NO | ID |
| message | message | String | text | 65535 | NO | 密语 |
| passwd | passwd | String | text | 65535 | NO | 查看密码 |
| changekey | changekey | String | text | 65535 | NO | 管理key |

## oneread表结构说明
| 代码字段名 | 字段名 | 数据类型（代码） | 数据类型 | 长度 | NullAble | 注释 |
| :--------- | ------ | ---------------- | -------- | ---- | -------------- | ---- |
| id | id | Long | bigint | 19 | NO |  |
| message | message | String | text | 65535 | NO | 信息 |
| status | status | Integer | int | 10 | NO | 状态 |

## url表结构说明
| 代码字段名 | 字段名 | 数据类型（代码） | 数据类型 | 长度 | NullAble | 注释 |
| :--------- | ------ | ---------------- | -------- | ---- | -------------- | ---- |
| id | id | Long | bigint | 19 | NO | ID |
| url | url | String | text | 65535 | NO | 链接 |
| shortUrl | short_url | String | text | 65535 | NO | 短链 |
| MD5 | MD5 | String | text | 65535 | NO | MD5 |








