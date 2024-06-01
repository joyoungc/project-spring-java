CREATE TABLE member
(
    id     BIGINT AUTO_INCREMENT,
    name          VARCHAR(255) not null,
    grade         VARCHAR(255) COMMENT '[VIP,BASIC]',
    city          VARCHAR(255),
    street        VARCHAR(255),
    zip_code      VARCHAR(255),
    created_by    VARCHAR(255),
    created_date  DATETIME COMMENT '생성일',
    modified_by   VARCHAR(255),
    modified_date DATETIME COMMENT '수정일',
    PRIMARY KEY (id)
);


CREATE TABLE orders
(
    id       BIGINT AUTO_INCREMENT,
    discount_price BIGINT,
    order_date     DATETIME,
    status         VARCHAR(255) comment '[ORDER,CANCEL]',
    member_id      BIGINT,
    product_id     BIGINT UNIQUE,
    created_by     VARCHAR(255),
    created_date   DATETIME,
    modified_by    VARCHAR(255),
    modified_date  DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE product
(
    id    BIGINT AUTO_INCREMENT,
    created_date  DATETIME,
    modified_date DATETIME,
    price         BIGINT,
    created_by    VARCHAR(255),
    modified_by   VARCHAR(255),
    name          VARCHAR(255),
    PRIMARY KEY (id)
);

ALTER TABLE orders ADD CONSTRAINT fk_orders_01 FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE orders ADD CONSTRAINT fk_orders_02 FOREIGN KEY (product_id) REFERENCES product (id);