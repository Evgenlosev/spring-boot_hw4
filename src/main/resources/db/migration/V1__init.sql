create table categories (
    id                      bigserial primary key,
    title                   varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into categories (title) values
('Bread'),
('Dairy'),
('Fruits'),
('Sweets'),
('Meat'),
('Vegetables'),
('Drinks');

create table if not exists products (
    id          bigserial primary key,
    title       varchar(255),
    price       int not null,
    category_id bigint not null references categories (id),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

insert into products (title, price, category_id) values
('Bread', 35, 1),
('Milk', 60, 2),
('Apples', 135, 3),
('Chocolate', 50, 4),
('Cheese', 150, 2),
('Orange', 170, 3),
('Butter', 140, 2),
('Baguette', 40, 1),
('Cookie', 70, 4),
('Ice cream', 60, 4),
('Bun', 40, 1),
('Bacon', 200, 5),
('Beef', 420, 5),
('Sausages', 380, 5),
('Potato', 37, 6),
('Cabbage', 70, 6),
('Carrot', 19, 6),
('Strawberry', 320, 3),
('Juice', 130, 7),
('Water', 25, 7),
('Coca-Cola', 80, 7);

create table users (
    id bigserial primary key,
    username varchar(36) not null,
    password varchar(80) not null,
    email varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table roles (
    id          bigserial primary key,
    name        varchar(50) not null,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table users_roles (
    user_id     bigint not null references users (id),
    role_id     bigint not null references roles (id),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp,
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
    phone           varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table order_items (
    id                      bigserial primary key,
    product_id              bigint not null references products(id),
    order_id                bigint not null references orders(id),
    quantity                int not null,
    price_per_product       int not null,
    price                   int not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into orders (user_id, total_price, address, phone) values
(1, 120, 'address', '12345');

insert into order_items (product_id, order_id, quantity, price_per_product, price) values
(2, 1, 2, 60, 120);
