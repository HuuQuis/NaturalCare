USE natural_care;

INSERT INTO province (code, name, full_name) VALUES 
('01', 'Hanoi', 'Thành phố Hà Nội');

INSERT INTO district (code, name, full_name, province_code) VALUES
('001', 'Ba Dinh', 'Quận Ba Đình', '01'),
('002', 'Dong Da', 'Quận Đống Đa', '01'),
('003', 'Cau Giay', 'Quận Cầu Giấy', '01'),
('004', 'Thanh Xuan', 'Quận Thanh Xuân', '01'),
('005', 'Hoan Kiem', 'Quận Hoàn Kiếm', '01');

INSERT INTO ward (code, name, full_name, district_code) VALUES
('0001', 'Phuc Xa', 'Phường Phúc Xá', '001'),
('0002', 'Lang Ha', 'Phường Láng Hạ', '002'),
('0003', 'Dich Vong', 'Phường Dịch Vọng', '003'),
('0004', 'Khuong Thuong', 'Phường Khương Thượng', '004'),
('0005', 'Hang Bac', 'Phường Hàng Bạc', '005');

-- 2. Thêm địa chỉ (address_id = 1 -> 5)
INSERT INTO address (province_code, district_code, ward_code, detail, distance_km)
VALUES
('01', '001', '0001', '123 Main St', 2.5),
('01', '002', '0002', '456 Second St', 3.2),
('01', '003', '0003', '789 Third St', 1.8),
('01', '004', '0004', '101 First Ave', 2.1),
('01', '005', '0005', '202 Second Ave', 2.9);

-- 3. Thêm coupon
INSERT INTO coupon (code, discount_percent, min_order_amount, valid_from, valid_to, is_active, usage_limit, times_used, is_user_specific)
VALUES
('DISCOUNT10', 10, 100000, '2025-01-01', '2025-12-31', TRUE, 100, 0, FALSE),
('DISCOUNT20', 20, 200000, '2025-01-01', '2025-12-31', TRUE, 50, 0, FALSE),
('DISCOUNT30', 30, 300000, '2025-06-01', '2025-12-31', TRUE, NULL, 0, TRUE);

-- 4. Thêm shipper (user_id = 1)
INSERT INTO user (username, password, first_name, last_name, email, phone_number, role_id)
VALUES
('shipper1', 'passship1', 'Shipper', 'One', 'shipper1@example.com', '0333444555', 7);

-- Gán địa chỉ cho shipper
INSERT INTO userAddress (user_id, address_id, is_default) VALUES (1, 1, TRUE);

-- 5. Thêm khách hàng (user_id = 2 -> 6)
INSERT INTO user (username, password, first_name, last_name, email, phone_number, role_id)
VALUES
('customer1', 'pass1', 'John', 'Doe', 'john1@example.com', '0123456789', 1),
('customer2', 'pass2', 'Jane', 'Doe', 'jane2@example.com', '0987654321', 1),
('customer3', 'pass3', 'Jim', 'Beam', 'jim3@example.com', '0112233445', 1),
('customer4', 'pass4', 'Jack', 'Daniels', 'jack4@example.com', '0998877665', 1),
('customer5', 'pass5', 'Jill', 'Valentine', 'jill5@example.com', '0223344556', 1);

-- Gán địa chỉ cho từng khách
INSERT INTO userAddress (user_id, address_id, is_default) VALUES
(2, 1, TRUE),
(3, 2, TRUE),
(4, 3, TRUE),
(5, 4, TRUE),
(6, 5, TRUE);

-- 6. Thêm đơn hàng (dùng user_id từ 2 đến 6, shipper_id = 1)
INSERT INTO product_order (user_id, order_note, status_id, shipper_id, address_id, coupon_id)
VALUES
(2, 'Order note 1 - pending', 1, NULL, 1, NULL),
(2, 'Order note 2 - processing', 2, NULL, 1, NULL),
(2, 'Order note 3 - shipped', 4, 1, 1, NULL),

(3, 'Order note 4 - assigned to shipper', 3, 1, 2, 1),
(3, 'Order note 5 - delivered', 5, 1, 2, 2),

(4, 'Order note 6 - cancelled', 6, NULL, 3, NULL),
(4, 'Order note 7 - returned', 7, NULL, 3, NULL),

(5, 'Order note 8 - refunded', 8, NULL, 4, 3),

(6, 'Order note 9 - pending', 1, NULL, 5, NULL),
(6, 'Order note 10 - processing', 2, NULL, 5, NULL);