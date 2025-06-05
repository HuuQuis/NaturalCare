-- 1. Thêm địa chỉ (5 bản ghi mẫu)
INSERT INTO address (province_code, province_name, district_code, district_name, ward_code, ward_name, detail)
VALUES
(1, 'Hanoi', 101, 'Ba Dinh', 1001, 'Phuc Xa', '123 Main St'),
(1, 'Hanoi', 102, 'Dong Da', 1002, 'Lang Ha', '456 Second St'),
(1, 'Hanoi', 103, 'Cau Giay', 1003, 'Dich Vong', '789 Third St'),
(1, 'Hanoi', 104, 'Thanh Xuan', 1004, 'Khuong Thuong', '101 First Ave'),
(1, 'Hanoi', 105, 'Hoan Kiem', 1005, 'Hang Bac', '202 Second Ave');

-- 2. Thêm coupon (3 bản ghi mẫu)
INSERT INTO coupon (code, discount_percent, min_order_amount, valid_from, valid_to, is_active, usage_limit, times_used, is_user_specific)
VALUES
('DISCOUNT10', 10, 100000, '2025-01-01', '2025-12-31', TRUE, 100, 0, FALSE),
('DISCOUNT20', 20, 200000, '2025-01-01', '2025-12-31', TRUE, 50, 0, FALSE),
('DISCOUNT30', 30, 300000, '2025-06-01', '2025-12-31', TRUE, NULL, 0, TRUE);

-- 3. Thêm shipper (role_id = 7)
INSERT INTO user (username, password, first_name, last_name, email, phone_number, role_id, address_id)
VALUES
('shipper1', 'passship1', 'Shipper', 'One', 'shipper1@example.com', '0333444555', 7, 1);

-- 4. Thêm 5 khách hàng (role_id = 1)
INSERT INTO user (username, password, first_name, last_name, email, phone_number, role_id, address_id)
VALUES
('customer1', 'pass1', 'John', 'Doe', 'john1@example.com', '0123456789', 1, 1),
('customer2', 'pass2', 'Jane', 'Doe', 'jane2@example.com', '0987654321', 1, 2),
('customer3', 'pass3', 'Jim', 'Beam', 'jim3@example.com', '0112233445', 1, 3),
('customer4', 'pass4', 'Jack', 'Daniels', 'jack4@example.com', '0998877665', 1, 4),
('customer5', 'pass5', 'Jill', 'Valentine', 'jill5@example.com', '0223344556', 1, 5);

-- 5. Thêm đơn hàng với các trạng thái khác nhau (giả sử user_id khách là 6->10, shipper_id = 1)
INSERT INTO product_order (user_id, order_note, status_id, shipper_id, address_id, coupon_id)
VALUES
(6, 'Order note 1 - pending', 1, NULL, 1, NULL),
(6, 'Order note 2 - processing', 2, NULL, 1, NULL),
(6, 'Order note 3 - shipped', 4, 1, 1, NULL),

(7, 'Order note 4 - assigned to shipper', 3, 1, 2, 1),
(7, 'Order note 5 - delivered', 5, 1, 2, 2),

(8, 'Order note 6 - cancelled', 6, NULL, 3, NULL),
(8, 'Order note 7 - returned', 7, NULL, 3, NULL),

(9, 'Order note 8 - refunded', 8, NULL, 4, 3),

(10, 'Order note 9 - pending', 1, NULL, 5, NULL),
(10, 'Order note 10 - processing', 2, NULL, 5, NULL);
