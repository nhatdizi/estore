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

create table comment(
	id int auto_increment primary key,
    comment varchar(255),
    rate int,
    productId int,
    accountId int,
    constraint FK_comment_product foreign key(productId) references product(id),
    constraint FK_comment_account foreign key(accountId) references account(id)
);

create table cart(
	stock int,
    productId int,
    accountId int,
    primary key(productId, accountId),
    constraint FK_cart_product foreign key(productId) references product(id),
    constraint FK_cart_account foreign key(accountId) references account(id)
);

create table shipping(
	id int auto_increment primary key,
    shipping_method varchar(255)
);

create table orders(
	id int auto_increment primary key,
    status varchar(255),
    create_at date,
    shippingId int,
    accountId int,
	constraint FK_shipping foreign key(shippingId) references shipping(id),
    constraint FK_account foreign key(accountId) references account(id)
);

create table order_items(
	quantity int,
    productId int,
    orderId int,
    primary key(productId, orderId),
    constraint FK_order_items_product foreign key(productId) references product(id),
    constraint FK_order_items_account foreign key(orderId) references orders(id)
);

show tables;

insert into category values
(1, 'New'),
(2, 'Featured'),
(3, 'Offer');


insert into product(id, image, product_name, cost_price, original_price, stock, description, categoryId) values
(1, 'product1.png', 'Green Dress with details', 40.00, 60.00, 100, 'Cervical pillow for airplane car office nap pillow', 1),
(2, 'product2.png', 'Green Dress with details', 40.00, 60.00, 100, 'Geometric striped flower home classy decor', 1),
(3, 'product3.png', 'Green Dress with details', 40.00, 60.00, 100, 'Foam filling cotton slow rebound pillows', 2),
(4, 'product4.png', 'Green Dress with details', 40.00, 60.00, 100, 'Sleeping orthopedic sciatica Back Hip Joint Pain relief', 2),
(5, 'product5.png', 'Green Dress with details', 40.00, 60.00, 100, 'Geometric striped flower home classy decor', 3),
(6, 'product6.png', 'Green Dress with details', 40.00, 60.00, 100, 'Foam filling cotton slow rebound pillows', 3);

select * from category;
select * from product;
    

