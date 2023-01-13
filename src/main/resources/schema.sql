create schema if not exists store;
set schema 'store';

create table if not exists products (
    id bigserial primary key,
    category varchar(30) not null,
    article varchar(7) unique not null,
    model varchar(50) unique not null,
    amount int not null,
    description text not null,
    cost real not null
);
create table if not exists users (
    id bigserial primary key,
    name varchar(30) not null,
    surname varchar(30) not null,
    email varchar(30) unique not null,
    phone varchar(12) not null,
    password varchar(30) not null,
    role varchar(10) default('ROLE_USER') not null,
    registration_time timestamp not null
);
create table if not exists baskets (
    id bigserial primary key,
    product_id bigint null,
    user_id bigint null,
    constraint fk_user foreign key(user_id) references users(id) on delete cascade on update cascade,
    constraint fk_product foreign key(product_id) references products(id) on delete cascade on update cascade
);
create table if not exists orders (
    id bigserial primary key,
    user_id bigint null,
    amount real default(10) null,
    delivery_method varchar(30) not null,
    payment_method varchar(30) not null,
    order_date timestamp not null,
    status varchar(30) default('false') not null,
    address varchar(80) not null,
    delivery_date date not null,
    constraint fk_user foreign key(user_id) references users(id) on delete cascade on update cascade
);
create table if not exists order_points (
    id bigserial primary key,
    order_id bigint not null,
    product_id bigint not null,
    constraint fk_user foreign key(product_id) references products(id) on delete cascade on update cascade,
    constraint fk_order foreign key(order_id) references orders(id) on delete cascade on update cascade
);
create table if not exists warehouses (
    id bigserial primary key,
    product_id bigint null,
    amount int not null,
    constraint fk_product foreign key(product_id) references products(id) on delete cascade on update cascade
);