Create database natural_care;

use natural_care;

create table order_status(
	status_id int not null primary key,
    status_name varchar(255)
);

INSERT INTO order_status (status_id, status_name) VALUES
(1, 'Pending'),
(2, 'Processing'),
(3, 'Assigned to Shipper'),
(4, 'Shipped'),
(5, 'Delivered'),
(6, 'Cancelled'),
(7, 'Returned'),
(8, 'Refunded');

create table blog_category(
	blog_category_id int not null auto_increment primary key,
    blog_category_name varchar(255)
);

insert into blog_category( blog_category_name ) values
( 'Lip Care'),
('Facial Care'),
('Hair and Scalp Care'),
('Body Care');



create table blog(
	blog_id int not null auto_increment primary key,
    blog_title varchar(255),
    blog_description mediumtext,
    date_published TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    blog_category_id int not null,
    foreign key (blog_category_id) references blog_category(blog_category_id)
);

create table blog_image(
	blog_image_id int not null auto_increment primary key,
    blog_id int not null,
    blog_image text not null,
    foreign key (blog_id) references blog(blog_id) on delete cascade
);

create table role(
	role_id int not null primary key,
    role_name varchar(255) not null
);

insert into role( role_id, role_name) values
(1,'Customer'),
(2, 'Staff'),
(3, 'Admin'),
(4, 'Manager'),
(5, 'Marketer'),
(6, 'Expert'),
(7, 'Shipper');

create table address(
	address_id INT AUTO_INCREMENT primary key, 
    province_code INT,
    province_name VARCHAR(100),
    district_code INT,
    district_name VARCHAR(100),
    ward_code INT,
    ward_name VARCHAR(100),
    detail TEXT
);

create table skill(
	skill_id int not null auto_increment primary key,
    skill_name varchar(255)
);

insert into skill ( skill_name ) values
('Makeup Expert'),
('Skin Expert'),
('Hair Care Experts'),
('Perfume Expert'); 

create table user(
	user_id int not null auto_increment primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    email varchar(255) not null,
    phone_number varchar(20) not null,
    role_id int not null,
    address_id int,
    skill_id int,
    user_image text,
    foreign key(role_id) references role(role_id),
    foreign key(address_id) references address(address_id),
    foreign key(skill_id) references skill(skill_id)
);

create table blog_comment(
	blog_comment_id int not null auto_increment primary key,
    comment varchar(255) not null, 
    user_id int not null,
    blog_id int not null,
    foreign key (user_id) references user(user_id),
    foreign key (blog_id) references blog(blog_id)
);


CREATE TABLE userAddress (
    user_id INT NOT NULL,
    address_id INT NOT NULL,
    PRIMARY KEY (user_id, address_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (address_id) REFERENCES address(address_id)
);

create table expertSkill(
	user_id int not null,
    skill_id int not null,
    PRIMARY KEY (user_id, skill_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id) on delete cascade,
    foreign key (skill_id) references skill(skill_id) on delete cascade
);

create table product_category(
	product_category_id int not null auto_increment primary key,
    product_category_name varchar(255)
);

insert into product_category ( product_category_name) values
('Make Up'),
('Skin'),
('Hair'),
('Oral Beauty'),
('Body'),
('Organic Perfume'),
('Gift Set');


create table sub_product_category(
	sub_product_category_id int not null primary key,
    sub_product_category_name varchar(255),
    product_category_id int,
    foreign key(product_category_id) references product_category(product_category_id)
);


create table product(
	product_id int not null auto_increment primary key,
    product_name varchar(255) not null,
	product_short_description varchar(255),
    product_information mediumtext,
    product_guildline text,
    sub_product_category_id int not null,
    foreign key (sub_product_category_id) references sub_product_category(sub_product_category_id)
);

CREATE TABLE product_variation (
    variation_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    product_image TEXT,
    color VARCHAR(50),
    size VARCHAR(50),
    price BIGINT NOT NULL,
    qty_in_stock INT NOT NULL,
    solded int default 0,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

create table product_feedback(
	product_feedback_id int not null auto_increment primary key,
    star_rate TINYINT NOT NULL,
	product_comment varchar(255),
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id int,
    product_id int,
    foreign key (user_id) references user(user_id),
    foreign key	(product_id) references product(product_id)
);


CREATE TABLE coupon (
    coupon_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    discount_percent INT NOT NULL CHECK (discount_percent BETWEEN 1 AND 100),
    min_order_amount BIGINT DEFAULT 0,         -- Optional: minimum order value
    valid_from DATE,
    valid_to DATE,
    is_active BOOLEAN DEFAULT TRUE,
    usage_limit INT DEFAULT NULL,             -- NULL = unlimited
    times_used INT DEFAULT 0,
    is_user_specific BOOLEAN DEFAULT FALSE
);

create table product_order(
	order_id int not null auto_increment primary key,
    user_id int not null,
    order_note varchar(255),
    status_id int not null default 1,
    create_at TimeStamp DEFAULT CURRENT_TIMESTAMP,
    shipper_id int null,
    address_id int not null,
    coupon_id int,
    foreign key (user_id) references user(user_id),
    foreign key (shipper_id) references user(user_id),
    foreign key (address_id) references address(address_id),
    foreign key (product_id) references product(product_id),
    foreign key (coupon_id) references coupon(coupon_id)
);


CREATE TABLE order_detail (
    order_detail_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    variation_id INT NOT NULL,
    total_quantity INT NOT NULL,
    total_price BIGINT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES product_order(order_id) ON DELETE CASCADE,
    FOREIGN KEY (variation_id) REFERENCES product_variation(variation_id)
);


create table delivery(
	delivery_id int not null auto_increment primary key,
	order_id int not null,
    shipper_id int not null,
    status_id int not null default 1,
    delivery_date timestamp,
    foreign key (status_id) references order_status(status_id),
    foreign key (order_id) references product_order(order_id) on delete cascade,
    foreign key (shipper_id) references user(user_id)
);

create table return_request(
	return_request_id int not null auto_increment primary key,
    order_id int not null,
    user_id int not null,
    reason text not null,
    request_at timestamp default current_timestamp,
    approved_by int not null,
    foreign key (order_id) references product_order(order_id),
    foreign key (user_id) references user(user_id),
    foreign key (approved_by) references user(user_id)
);

