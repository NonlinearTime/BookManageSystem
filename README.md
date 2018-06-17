# 图书管理系统
2018年春　华中科技大学计算机学院数据库实验

## 系统信息

- 开发环境: linux＋Intellij Idea+jdk-10.0.1+MySql 5.7
- 运行环境: JVM1.8
- 系统类型: C/S

## 角色类型

系统包含如下三种角色类型。

- 用户

  - 注册以及登录系统，找回密码
  - 查询书籍使用情况
  - 查询自己基本信息与借书记录
  - 借阅/退还/续借书籍

- 管理员

  - 借书：核实读者身份并检查是否存在下述情况：

    - 读者借书的数额超标
    - 读者所借书籍过期未还
    - 读者曾因借书过期被罚款而未交

    不存在以上情况，则登记借书信息。

  - 还书：检查所还图书是否损坏或者过期，是则登记罚单信息并打印罚单，在缴纳罚金之间，不允许该读者继续借书。若图书损坏，注销该图书信息，否则进行还书登记。

  - 罚款：根据罚单收取罚金，同时取消该读者的借书限制

  - 图书信息维护：新书上架、旧书下架以及图书信息查询

  - 读者信息维护：录入(用户自行注册)、注销、修改读者信息

- 超级管理员(后台)

  - 维护图书管理系统运行
  - 数据库运维
  - 查询所有用户、管理员在线情况，登录日志
  - 查询管理员信息，授予、撤销管理员权限

## 数据组成

- 用户

|  字段   |  信息  |
| :---: | :--: |
|  uID  | 用户ID |
| uName | 用户名  |
| uPwd  | 用户密码 |
| email | 用户邮箱 |
| uTele | 用户电话 |
| uType | 用户职业 |
| rTime | 注册时间 |



- 管理员

|  字段   |   信息   |
| :---: | :----: |
|  aID  | 管理员ID  |
| aName | 管理员账户名 |
| aPwd  | 管理员密码  |
| email | 管理员邮箱  |
| aTele | 管理员电话  |
| aType | 管理员职业  |
| rTime | 管理起始时间 |



- 图书信息

|   字段    |  信息  |
| :-----: | :--: |
|   bID   | 图书编号 |
|  bName  | 图书名称 |
|  bType  | 图书类型 |
|  aName  | 作者名称 |
| pubName | 出版社  |
|  uDate  | 上架日期 |
| totNum  | 总数量  |
|  rNum   | 在馆数量 |
|  price  | 图书价格 |
| bScore  |  评分  |
| reviews | 评论数  |

- 借书登记表


|   字段   |  信息  |
| :----: | :--: |
|  rID   | 借阅编号 |
|  uID   | 读者编号 |
|  bID   | 图书编号 |
| rDate  | 借阅日期 |
| rbDate | 应还日期 |
| isBack | 是否已还 |




- 罚款登记表


|    字段    |  信息  |
| :------: | :--: |
|   fID    | 罚单编号 |
|   uID    | 读者编号 |
|   rID    | 借阅编号 |
|  fMount  | 罚款金额 |
|  fDate   | 罚款日期 |
| isSolved | 是否交款 |



- 超级管理员（后台）


## 数据库权限管理

- 游客权限
  - 只能查询数据库图书信息表
- 读者权限
  - 可以查询数据库图书信息表，可见自身信息，以及自身借阅信息、罚款信息
  - 修改操作发送至服务器修改
- 管理员权限
  - 查询和修改图书信息表
  - 查询和修改读者信息表
  - 查询和修改借阅登记表
  - 查询和修改罚款登记表
  - 查询自身信息视图
- 超级管理员权限
  - 享有整个数据库的操作权限(root)

## 功能划分

系统功能模块划分如下：暂定

![功能模块](https://github.com/cckevincyh/LibrarySystem/raw/master/img/0.png)



## 界面设计



