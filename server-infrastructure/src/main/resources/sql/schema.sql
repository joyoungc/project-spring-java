-- DROP SCHEMA IF EXISTS app CASCADE;
-- CREATE SCHEMA app;
-- SET SCHEMA app;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS admin_user;
DROP TABLE IF EXISTS roles_permissions;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS permissions;
DROP TABLE IF EXISTS roles_secondary;

CREATE TABLE member
  (
     id          BIGINT auto_increment PRIMARY KEY,
     name        VARCHAR(255) NOT NULL,
     grade       VARCHAR(255) comment '[VIP,BASIC]',
     city        VARCHAR(255),
     street      VARCHAR(255),
     zip_code    VARCHAR(255),
     created_by  VARCHAR(255),
     created_at  DATETIME comment '생성일',
     modified_by VARCHAR(255),
     modified_at DATETIME comment '수정일'
  );

CREATE TABLE orders
  (
     id             BIGINT auto_increment PRIMARY KEY,
     discount_price BIGINT,
     order_date     DATETIME,
     status         VARCHAR(255) comment '[ORDER,CANCEL]',
     member_id      BIGINT,
     product_id     BIGINT UNIQUE,
     created_by     VARCHAR(255),
     created_at     DATETIME,
     modified_by    VARCHAR(255),
     modified_at    DATETIME
  );

CREATE TABLE product
  (
     id          BIGINT auto_increment PRIMARY KEY,
     price       BIGINT,
     created_by  VARCHAR(255),
     modified_by VARCHAR(255),
     name        VARCHAR(255),
     created_at  DATETIME,
     modified_at DATETIME
  );

ALTER TABLE orders
  ADD CONSTRAINT fk_orders_01 FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE orders
  ADD CONSTRAINT fk_orders_02 FOREIGN KEY (product_id) REFERENCES product (id);

CREATE TABLE admin_user
  (
     id             BIGINT auto_increment PRIMARY KEY,
     name           VARCHAR(255) NOT NULL,
     password       VARCHAR(255),
     email          VARCHAR(255),
     region_code    VARCHAR(2) DEFAULT 'KR' NOT NULL,
     status         VARCHAR(255) comment '[ACTIVE,INACTIVE]',
     last_access_at DATETIME comment '최종 접속일자',
     created_by     VARCHAR(255),
     created_at     DATETIME DEFAULT CURRENT_TIMESTAMP comment '생성일',
     modified_by    VARCHAR(255),
     modified_at    DATETIME comment '수정일'
  );

-- 1차 권한
CREATE TABLE roles
  (
     id            BIGINT auto_increment PRIMARY KEY,
     name          VARCHAR(100) NOT NULL comment '권한이름',
     status        VARCHAR(10) DEFAULT 'ACTIVE' NOT NULL comment '[ACTIVE,INACTIVE]',
     description   VARCHAR(1000),
     region_code   VARCHAR(2) NOT NULL,
     product_group VARCHAR(20) comment '[BASIC_PRODUCT,SPECIAL_PRODUCT]',
     role_type     VARCHAR(20) comment '[ADMIN,PARTNER]',
     created_at    DATETIME DEFAULT CURRENT_TIMESTAMP comment '생성일자',
     modified_at   DATETIME comment '수정일자'
  );

CREATE TABLE permissions
  (
     id                 BIGINT auto_increment PRIMARY KEY,
     `order`            INT NOT NULL comment 'Menu order',
     menu1_id           INT NOT NULL comment '1Depth Menu ID',
     menu2_id           INT NOT NULL comment '2Depth Menu ID',
     action_id          INT NOT NULL comment 'Action ID in menu',
     menu1_locale       VARCHAR(128) NOT NULL,
     menu2_locale       VARCHAR(128) NOT NULL,
     action_locale      VARCHAR(128) NULL,
     basic_product      TINYINT DEFAULT 0 NULL,
     special_product    TINYINT DEFAULT 0 NULL,
     region_kr          TINYINT DEFAULT 0 NOT NULL,
     region_us          TINYINT DEFAULT 0 NOT NULL,
     region_jp          TINYINT DEFAULT 0 NOT NULL,
     role_admin         TINYINT DEFAULT 0 NOT NULL,
     role_partner       TINYINT DEFAULT 0 NOT NULL,
     description_locale VARCHAR(200) NULL,
     urls               VARCHAR(1024) NULL,
     method             VARCHAR(10) DEFAULT 'GET' NOT NULL
  );

-- 2차 권한
CREATE TABLE roles_secondary (
    id                          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(100) NOT NULL COMMENT '이름',
    roles_id                    INT NOT NULL,
    management_account_type     VARCHAR(20) NOT NULL COMMENT '[ALL, BILLING, PARTNER, PROMOTION] 관리계정 ',
    log_view_policy_type        VARCHAR(20) NULL COMMENT '[ALL_ACCOUNT, MY_ACCOUNT] 운영로그 조회범위  ALL_ACCOUNT:전체사용자 MY_ACCOUNT:내 권한계정 사용자',
    mail_domain                 VARCHAR(100) NOT NULL,
    region_code                 VARCHAR(2) NOT NULL,
    status                      VARCHAR(10) NOT NULL COMMENT '[ACTIVE,INACTIVE]',
    created_at                  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at                 DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
    deleted_at                  DATETIME NULL,
    created_by                  INT NOT NULL,
    modified_by                 INT NULL,
    deleted_by                  INT NULL
);

CREATE index ix_roles_secondary_01
    ON roles_secondary (roles_id);


-- 1차 권한과 permission 매핑 테이블
CREATE TABLE roles_permissions (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    roles_id        INT NOT NULL,
    permissions_id  INT NOT NULL,
    CONSTRAINT fk_roles_permissions_01 FOREIGN KEY (roles_id) REFERENCES ROLES (id),
    CONSTRAINT fk_roles_permissions_02 FOREIGN KEY (permissions_id) REFERENCES permissions (id)
);