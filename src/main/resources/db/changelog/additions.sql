-- Create product table
CREATE TABLE product (
                         id BIGSERIAL PRIMARY KEY,
                         description VARCHAR(255) NOT NULL
);

-- Create order_product table
CREATE TABLE order_product (
                         order_id BIGSERIAL,
                         product_id BIGSERIAL,
                         PRIMARY KEY (order_id, product_id),
                         CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES "order"(id),
                         CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id)
);

-- Create sequences for tables
CREATE SEQUENCE customer_sequence start with 101 increment by 1;
CREATE SEQUENCE order_sequence start with 10001 increment by 1;
CREATE SEQUENCE product_sequence start with 1 increment by 1;