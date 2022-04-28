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

create table orders (
    id              bigserial primary key,
    username        varchar(255) not null,
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

insert into orders (username, total_price, address, phone) values
('user_1', 120, 'address', '12345');

insert into order_items (product_id, order_id, quantity, price_per_product, price) values
(2, 1, 2, 60, 120);
