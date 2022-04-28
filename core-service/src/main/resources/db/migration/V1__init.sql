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
    price       numeric(8, 2) not null,
    category_id bigint not null references categories (id),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

insert into products (title, price, category_id) values
('Bread', 35.50, 1),
('Milk', 60.00, 2),
('Apples', 135.00, 3),
('Chocolate', 50.00, 4),
('Cheese', 150.00, 2),
('Orange', 170.00, 3),
('Butter', 140.00, 2),
('Baguette', 40.00, 1),
('Cookie', 70.00, 4),
('Ice cream', 60.00, 4),
('Bun', 40.00, 1),
('Bacon', 200.00, 5),
('Beef', 420.00, 5),
('Sausages', 380.00, 5),
('Potato', 37.00, 6),
('Cabbage', 70.00, 6),
('Carrot', 19.00, 6),
('Strawberry', 320.00, 3),
('Juice', 130.00, 7),
('Water', 25.00, 7),
('Coca-Cola', 80.00, 7);

--create table adresses (
--    id                  bigserial primary key,
--    order_id            bigint not null references orders(id),
--    country             varchar(50) not null,
--    city                varchar(50) not null,
--    street              varchar(50) not null,
--    house_number        int not null,
--    floor               int not null,
--    apartment_number    int not null
--);

create table orders (
    id              bigserial primary key,
    username        varchar(255) not null,
    total_price     numeric(8, 2) not null,
    address         varchar(255),
    phone           varchar(255),
    status          varchar(255) default 'created',
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

create table order_items (
    id                      bigserial primary key,
    product_id              bigint not null references products(id),
    order_id                bigint not null references orders(id),
    quantity                int not null,
    price_per_product       numeric(8, 2) not null,
    price                   numeric(8, 2) not null,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

insert into orders (username, total_price, address, phone) values
('user_1', 120.00, 'address', '12345');

insert into order_items (product_id, order_id, quantity, price_per_product, price) values
(2, 1, 2, 60.00, 120.00);
