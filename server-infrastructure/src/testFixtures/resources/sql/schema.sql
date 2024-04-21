CREATE TABLE member
(
    created_date  DATETIME,
    member_id     BIGINT AUTO_INCREMENT,
    modified_date DATETIME,
    city          VARCHAR(255),
    created_by    VARCHAR(255),
    grade         VARCHAR(255) COMMENT '[VIP,BASIC]',
    modified_by   VARCHAR(255),
    name          VARCHAR(255) not null,
    street        VARCHAR(255),
    zip_code      VARCHAR(255),
    PRIMARY KEY (member_id)
);


CREATE TABLE orders
(
    created_date   DATETIME,
    discount_price BIGINT,
    member_id      BIGINT,
    modified_date  DATETIME,
    order_date     DATETIME,
    order_id       BIGINT AUTO_INCREMENT,
    product_id     BIGINT UNIQUE,
    created_by     VARCHAR(255),
    modified_by    VARCHAR(255),
    status         VARCHAR(255) comment '[ORDER,CANCEL]',
    PRIMARY KEY (order_id)
);

CREATE TABLE product
(
    created_date  DATETIME,
    modified_date DATETIME,
    price         BIGINT,
    product_id    BIGINT AUTO_INCREMENT,
    created_by    VARCHAR(255),
    modified_by   VARCHAR(255),
    name          VARCHAR(255),
    PRIMARY KEY (product_id)
);

ALTER TABLE orders ADD CONSTRAINT fk_orders_01 FOREIGN KEY (member_id) REFERENCES member (member_id);

ALTER TABLE orders ADD CONSTRAINT fk_orders_02 FOREIGN KEY (product_id) REFERENCES product (product_id);