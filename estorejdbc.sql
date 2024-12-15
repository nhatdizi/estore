create database if not exists estorejdbc;

use estorejdbc;

create table category(
	id int auto_increment primary key,
    name varchar(255)
);

create table product(
	id int auto_increment primary key,
    image varchar(255),
    product_name varchar(255),
    cost_price decimal(10,2),
    original_price decimal(10,2),
    stock int,
    description text,
    categoryId int,
    constraint FK_category foreign key(categoryId) references category(id)
);

create table role(
	id int auto_increment primary key,
    position varchar(50)
);

create table account(
	id int auto_increment primary key,
    user_name varchar(255),
    password varchar(255),
    first_name varchar(50),
    last_name varchar(50),
    phone_number varchar(20),
    email varchar(255),
    address varchar(255),
    roleId int, 
    constraint FK_role foreign key(roleId) references role(id)
);

create table favorite(
	productId int,
    accountId int,
    primary key(productId, accountId),
    constraint FK_favorite_product foreign key(productId) references product(id),
    constraint FK_favorite_account foreign key(accountId) references account(id)
);

create table cart(
	stock int,
    productId int,
    accountId int,
    primary key(productId, accountId),
    constraint FK_cart_product foreign key(productId) references product(id),
    constraint FK_cart_account foreign key(accountId) references account(id)
);

show tables;

insert into category values
(1, 'New'),
(2, 'Featured'),
(3, 'Offer');

insert into product(id, image, product_name, cost_price, original_price, stock, description, categoryId) values
(1, 'product1.png', 'Green Dress with details', 40.00, 60.00, 100, 'Cervical pillow for airplane car office nap pillow', 1),
(2, 'product2.png', 'Green Dress with details', 30.00, 70.00, 100, 'Geometric striped flower home classy decor', 1),
(3, 'product3.png', 'Green Dress with details', 50.00, 80.00, 100, 'Foam filling cotton slow rebound pillows', 2),
(4, 'product4.png', 'Green Dress with details', 70.00, 65.00, 100, 'Sleeping orthopedic sciatica Back Hip Joint Pain relief', 2),
(5, 'product5.png', 'Green Dress with details', 20.00, 40.00, 100, 'Geometric striped flower home classy decor', 3),
(6, 'product6.png', 'Green Dress with details', 80.00, 90.00, 100, 'Foam filling cotton slow rebound pillows', 3);

insert into role(id, position) values
(1, 'admin'),
(2, 'user');

insert into account(id, user_name, password, email, roleId) values
(1, 'nhat', '123456', 'nhat@gmail.com', 1),
(2, 'the anh', '123456', 'theanh@gmail.com', 2),
(3, 'quang', '123456', 'quang@gmail.com', 2);
 
select * from category;
select * from product;
select * from role;
select * from account;
select * from cart where accountId = 3;
delete from cart where accountId = 3;

insert into cart(productId, accountId, stock) value (2, 2, 3);

select * from cart;
delete from cart where productId = 2 and accountId = 3;
update cart
set stock = 6 where productId = 2 and accountId = 2;

SELECT a.id, a.user_name, a.password, a.roleId, r.position FROM account a JOIN role r ON a.roleId = r.id where a.user_name = "quang";
select p.image, p.product_name, p.cost_price, c.stock from product p inner join cart c 
on p.id = c.productId where c.accountId = 3; 

insert into account(user_name, password, email, roleId) value ('chuyen', '12345678', 'chuyen@gmail.com', 2);

select * from product where product_name like '%Green%';

select p.image, p.product_name, p.cost_price, c.name from product p inner join favorite f 
on p.id = f.productId
inner join category c on p.categoryId = c.id where f.accountId = 3; 

select * from product order by cost_price desc;

