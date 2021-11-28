DROP TABLE products IF EXISTS;
CREATE TABLE IF NOT EXISTS products (id bigserial, title VARCHAR(255), price int, PRIMARY KEY (id));
INSERT INTO products (title, price) VALUES ('Bread', 35), ('Milk', 60), ('Apples', 135),('Chocolate', 50), ('Cheese', 150), ('Orange', 170);