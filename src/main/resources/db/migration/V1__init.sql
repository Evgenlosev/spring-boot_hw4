create table if not exists products (id bigserial primary key, title varchar(255), price int, creation_date timestamp default systimestamp);
insert into products (title, price) values
('Bread', 35),
('Milk', 60),
('Apples', 135),
('Chocolate', 50),
('Cheese', 150),
('Orange', 170),
('Butter', 140),
('Eggs', 85),
('Cookie', 70),
('Ice cream', 60),
('Rice', 40),
('Bacon', 200),
('Beef', 420),
('Sausages', 380),
('Potato', 37),
('Cabbage', 70),
('Carrot', 19),
('Strawberry', 320),
('Juice', 130),
('Water', 25);

create table users (
    id bigserial primary key,
    username varchar(36) not null,
    password varchar(80) not null,
    email varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table roles (
    id bigserial primary key,
    name varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table users_roles (
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
    primary key (user_id, role_id)
);

insert into roles (name)
values
    ('ROLE_USER'),
    ('ROLE_ADMIN');

insert into users (username, password, email)
values
    ('user_1', '$2y$10$QXvSn4z3ZN9TxzaZmHGQLOGN5MmTNn1ZKv176FacH6lTELixV8i5q', 'user_1@mail.ru'),
    ('user_2', '$2y$10$QXvSn4z3ZN9TxzaZmHGQLOGN5MmTNn1ZKv176FacH6lTELixV8i5q', 'user_2@mail.ru');

insert into users_roles (user_id, role_id)
values
    (1, 1),
    (2, 2);

create table orders (
    id              bigserial primary key,
    user_id         bigint not null references users(id),
    total_price     int not null,
    address         varchar(255),
    phone           varchar(255)
);

create table order_items (
    id                      bigserial primary key,
    product_id              bigint not null references products(id),
    order_id                bigint not null references orders(id),
    quantity                int not null,
    price_per_product       int not null,
    price                   int not null
);