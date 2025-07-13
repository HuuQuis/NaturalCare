DROP DATABASE IF EXISTS natural_care;

CREATE DATABASE natural_care;

USE natural_care;

CREATE TABLE order_status 
(
    status_id INT PRIMARY KEY,
    status_name VARCHAR(50) NOT NULL,
    description TEXT
);

CREATE TABLE payment_status (
    payment_status_id INT PRIMARY KEY,
    status_name VARCHAR(50) NOT NULL,
    description TEXT
);

CREATE TABLE blog_category (
                               blog_category_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                               blog_category_name VARCHAR(255) NOT NULL,
                               status BOOLEAN DEFAULT TRUE
);

CREATE TABLE blog (
                      blog_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                      blog_title VARCHAR(255) NOT NULL,
                      blog_description MEDIUMTEXT NOT NULL,
                      date_published TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      blog_category_id INT NOT NULL,
                      view_count INT DEFAULT 0,
                      status BOOLEAN DEFAULT TRUE,
                      FOREIGN KEY (blog_category_id) REFERENCES blog_category(blog_category_id)
);

CREATE TABLE blog_image
(
    blog_image_id INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    blog_id       INT  NOT NULL,
    blog_image    TEXT NOT NULL,
    FOREIGN KEY (blog_id)
        REFERENCES blog (blog_id)
        ON DELETE CASCADE
);

CREATE TABLE role
(
    role_id   INT          NOT NULL PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL
);

CREATE TABLE province (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    full_name VARCHAR(100)
);

CREATE TABLE district (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    full_name VARCHAR(100),
    province_code VARCHAR(10) NOT NULL,
    FOREIGN KEY (province_code) REFERENCES province(code)
);

CREATE TABLE ward (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    full_name VARCHAR(100),
    district_code VARCHAR(10) NOT NULL,
    latitude DOUBLE,
    longitude DOUBLE,
    FOREIGN KEY (district_code) REFERENCES district(code)
);

CREATE TABLE address (
    address_id     INT AUTO_INCREMENT PRIMARY KEY,
    province_code  VARCHAR(10),
    district_code  VARCHAR(10),
    ward_code      VARCHAR(10),
    first_name     VARCHAR(255) NOT NULL,
    last_name      VARCHAR(255) NOT NULL,
    email          VARCHAR(255) NOT NULL,
    phone_number   VARCHAR(20)  NOT NULL,
    detail         TEXT,
    distance_km DOUBLE,
    FOREIGN KEY (province_code) REFERENCES province(code),
    FOREIGN KEY (district_code) REFERENCES district(code),
    FOREIGN KEY (ward_code) REFERENCES ward(code)
);

CREATE TABLE skill
(
    skill_id   INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    skill_name VARCHAR(255)
);

create table contract
(
	contract_id int not null,
    contract_name varchar(255) not null,
    start_date date not null,
    end_date date not null
);

CREATE TABLE user
(
    user_id            INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username           VARCHAR(255) NOT NULL,
    password           VARCHAR(255) NOT NULL,
    first_name         VARCHAR(255) NOT NULL,
    last_name          VARCHAR(255) NOT NULL,
    email              VARCHAR(255) NOT NULL,
    phone_number       VARCHAR(20)  NOT NULL,
    role_id            INT          NOT NULL,
    user_image         TEXT,
    reset_token        VARCHAR(255),
    reset_token_expiry DATETIME,
    assign_staff_id INT NULL,
    contract_id int null,
    foreign key (assign_staff_id) references user(user_id),
    foreign key (contract_id) references contract(contract_id),
    FOREIGN KEY (role_id)
        REFERENCES role (role_id)
);

CREATE TABLE userAddress
(
    user_id    INT NOT NULL,
    address_id INT NOT NULL,
    is_default boolean default false,
    PRIMARY KEY (user_id, address_id),
    FOREIGN KEY (user_id)
        REFERENCES user (user_id),
    FOREIGN KEY (address_id)
        REFERENCES address (address_id)
);

CREATE TABLE blog_comment
(
    blog_comment_id INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    comment         VARCHAR(255) NOT NULL,
    user_id         INT          NOT NULL,
    blog_id         INT          NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES user (user_id),
    FOREIGN KEY (blog_id)
        REFERENCES blog (blog_id)
);



CREATE TABLE expertSkill
(
    user_id  INT NOT NULL,
    skill_id INT NOT NULL,
    PRIMARY KEY (user_id, skill_id),
    FOREIGN KEY (user_id)
        REFERENCES user (user_id)
        ON DELETE CASCADE,
    FOREIGN KEY (skill_id)
        REFERENCES skill (skill_id)
        ON DELETE CASCADE
);

CREATE TABLE product_category
(
    product_category_id   INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_category_name VARCHAR(255),
    status BOOLEAN DEFAULT TRUE
);

CREATE TABLE sub_product_category
(
    sub_product_category_id   INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sub_product_category_name VARCHAR(255),
    product_category_id       INT,
    status BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (product_category_id)
        REFERENCES product_category (product_category_id)
);

CREATE TABLE product
(
    product_id                INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_name              VARCHAR(255) NOT NULL,
    product_short_description VARCHAR(255),
    product_information       MEDIUMTEXT,
    product_guideline         TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    sub_product_category_id   INT          NOT NULL,
    FOREIGN KEY (sub_product_category_id)
        REFERENCES sub_product_category (sub_product_category_id)
);

create table color(
	color_id int not null primary key,
    color_name varchar(255)
);

create table size(
	size_id int not null primary key,
    size_name varchar(255)
);

CREATE TABLE product_variation
(
    variation_id  INT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_id    INT    NOT NULL,
    product_image TEXT,
    color_id int,
    size_id int,
    price         BIGINT NOT NULL,
    sell_price BIGINT NOT NULL,
    qty_in_stock  INT    NOT NULL,
    sold          INT DEFAULT 0,
    FOREIGN KEY (product_id) REFERENCES product (product_id),
    foreign key (color_id) references color(color_id),
    foreign key (size_id) references size(size_id)
);

CREATE TABLE product_feedback
(
    product_feedback_id INT     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    star_rate           TINYINT NOT NULL,
    product_comment     VARCHAR(255),
    create_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id             INT,
    product_id          INT,
    FOREIGN KEY (user_id)
        REFERENCES user (user_id),
    FOREIGN KEY (product_id)
        REFERENCES product (product_id)
);

CREATE TABLE coupon
(
    coupon_id        INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code             VARCHAR(50) NOT NULL UNIQUE,
    discount_percent INT         NOT NULL CHECK (discount_percent BETWEEN 1 AND 100),
    min_order_amount BIGINT  DEFAULT 0,
    valid_from       DATE,
    valid_to         DATE,
    is_active        BOOLEAN DEFAULT TRUE,
    usage_limit      INT     DEFAULT NULL,
    times_used       INT     DEFAULT 0,
    is_user_specific BOOLEAN DEFAULT FALSE
);

CREATE TABLE product_order (
    order_id           INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id            INT NOT NULL,
    order_note         VARCHAR(255),
    status_id          INT NOT NULL DEFAULT 1, -- from `order_status`
    payment_status_id  INT NOT NULL DEFAULT 1, -- from `payment_status`
    create_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    shipper_id         INT NULL,
    address_id         INT NOT NULL,
    coupon_id          INT,
    payment_method ENUM('cod', 'vnpay') NOT NULL,
    payment_gateway_txn_ref VARCHAR(100),            -- Mã đơn hàng gửi sang cổng
    payment_gateway_transaction_no VARCHAR(100),     -- Mã giao dịch trả về từ cổng
    payment_time TIMESTAMP NULL,                    -- Thời điểm thanh toán
    FOREIGN KEY (user_id)
        REFERENCES user (user_id),
    FOREIGN KEY (shipper_id)
        REFERENCES user (user_id),
    FOREIGN KEY (address_id)
        REFERENCES address (address_id),
    FOREIGN KEY (coupon_id)
        REFERENCES coupon (coupon_id),
    FOREIGN KEY (status_id)
        REFERENCES order_status (status_id),
    FOREIGN KEY (payment_status_id)
        REFERENCES payment_status (payment_status_id)
);


CREATE TABLE order_detail (
    order_detail_id INT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id        INT    NOT NULL,
    variation_id    INT    NOT NULL,
    quantity        INT    NOT NULL,
    price           BIGINT NOT NULL,

    FOREIGN KEY (order_id)
        REFERENCES product_order (order_id)
        ON DELETE CASCADE,
    FOREIGN KEY (variation_id)
        REFERENCES product_variation (variation_id)
);


CREATE TABLE delivery (
    delivery_id     INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id        INT NOT NULL,
    shipper_id      INT NOT NULL,
    delivery_status_id INT NOT NULL DEFAULT 1, -- rõ hơn
    delivery_date   TIMESTAMP,

    FOREIGN KEY (order_id)
        REFERENCES product_order (order_id)
        ON DELETE CASCADE,
    FOREIGN KEY (shipper_id)
        REFERENCES user (user_id),
    FOREIGN KEY (delivery_status_id)
        REFERENCES order_status (status_id) -- dùng lại order_status hoặc tạo bảng delivery_status riêng nếu cần
);

CREATE TABLE return_request (
    return_request_id INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id          INT  NOT NULL,
    user_id           INT  NOT NULL,
    reason            TEXT NOT NULL,
    request_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approved_by       INT NULL, -- NULL nếu chưa duyệt

    FOREIGN KEY (order_id)
        REFERENCES product_order (order_id)
        ON DELETE CASCADE,
    FOREIGN KEY (user_id)
        REFERENCES user (user_id),
    FOREIGN KEY (approved_by)
        REFERENCES user (user_id)
);