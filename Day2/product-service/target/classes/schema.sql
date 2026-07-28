CREATE DATABASE IF NOT EXISTS productdb;

USE productdb;

CREATE TABLE IF NOT EXISTS products
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_code VARCHAR(30) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    category VARCHAR(50),
    price DECIMAL(10,2),
    stock_quantity INT
);