create table UserOrder (id bigint not null auto_increment, orderDate datetime ,orderDeliveredDate datetime, userId bigint default 0, primary key (id)) engine=InnoDB;

create table OrderItem (id bigint not null auto_increment, name varchar(255) ,description varchar(255),sellerName varchar(255), price int default 0,weight double precision  not null, primary key (id)) engine=InnoDB;

create table UserOrderItem(order_id bigint not null,item_id bigint not null, idx integer not null, primary key (order_id, item_id)) engine=InnoDB;

alter table UserOrderItem add constraint order_fk foreign key (order_id) references UserOrder(id);

alter table UserOrderItem add constraint item_fk foreign key (item_id) references OrderItem(id);

create table User (id bigint not null auto_increment, username varchar(255) ,email varchar(255),password varchar(255), address varchar(255) default 0, primary key (id)) engine=InnoDB;
insert into User values(0,'user1', 'user1@test.com', '$2a$10$R4vO7Sb3XFuYFB6V7IRAReyHBed4vwPYtvWplNEZ9WibDijHPBPm6',' Mumbai');

create table hibernate_sequence (next_val bigint) engine=InnoDB;
insert into hibernate_sequence values ( 1 );

