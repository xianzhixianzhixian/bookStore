create schema shop;

use shop;

create table users
(
id int primary key auto_increment, -- 用户id
username varchar(50) not null, -- 用户名
userpassword varchar(50) not null, -- 密码
email varchar(100) not null, -- 邮箱
phonenumber varchar(20), -- 手机号
grade int(2) default 1 not null -- 用户等级
);

drop table books;

create table books
(
id int primary key auto_increment, -- 商品id
bookname varchar(50) not null, -- 书本名字
author varchar(50) not null, -- 书本作者
publishhouse varchar(100) not null, -- 出版社
price double not null, -- 价格
nums int default 1000 not null -- 书本库存
);

insert into users values(100,"樊钰丰","123456","2360316295@qq.com","15671562979",1);
insert into users values(101,"樊钰丰","123456","2360316295@qq.com","15671562979",1);

insert into books(bookname,author,publishhouse,price) values('笑傲江湖','金庸','清华出版社',35);
insert into books(bookname,author,publishhouse,price) values('神雕侠侣','金庸','清华出版社',35);
insert into books(bookname,author,publishhouse,price) values('射雕英雄传','金庸','清华出版社',35);
insert into books(bookname,author,publishhouse,price) values('射雕英雄传','金庸','清华出版社',35);
insert into books(bookname,author,publishhouse,price) values('JSP编程指南','王芳','电子工业出版社',10);
insert into books(bookname,author,publishhouse,price) values('Java Web服务开发','谭美君','电子工业出版社',95);
insert into books(bookname,author,publishhouse,price,nums) values('JavaEE 参考手册','Sun公司','清华出版社',55,10000);
insert into books(bookname,author,publishhouse,price,nums) values('JavaEE 企业级应用开发','毕向东','清华出版社',55,3000);
insert into books(bookname,author,publishhouse,price,nums) values('JavaWeb 整合开发王者归来','刘京华','清华出版社',55,100);

select * from users;
select * from books; 

create table orders
(
id int primary key auto_increment, -- 订单id
userId int, -- 用户id
totalPrice double default 0 not null, -- 订单总价格
orderDate datetime default NOW() not null, -- 订单日期
constraint fk_userId foreign key (userId) references users(id) on update cascade on delete restrict
);

drop table ordersItem

create table ordersItem -- 订单细节表，订单到底包含什么
(
id int primary key auto_increment,-- 订单细节表id
orderdId int, -- 外键，指向orders表的id
bookId int, -- 外键，指向books表的id
bookNum int default 0 not null, -- 商品购买数量
constraint fk_orderdId foreign key (orderdId) references orders(id) on update cascade on delete restrict,
constraint fk_bookId foreign key (bookId) references books(id) on update cascade on delete restrict
);