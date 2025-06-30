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
INSERT INTO product_order (order_id, user_id, order_note, status_id, shipper_id, address_id, coupon_id, create_at)
VALUES
(3001, 5, 'Order #3001 by user 5', 2, 1, 4, 2, '2025-06-01 08:00:00'),
(3002, 5, 'Order #3002 by user 5', 5, 1, 4, 3, '2025-06-02 08:00:00'),
(3003, 5, 'Order #3003 by user 5', 4, 1, 4, 3, '2025-06-03 08:00:00'),
(3004, 5, 'Order #3004 by user 5', 5, 1, 4, 2, '2025-06-04 08:00:00'),
(3005, 5, 'Order #3005 by user 5', 3, 1, 4, NULL, '2025-06-05 08:00:00'),
(3006, 5, 'Order #3006 by user 5', 2, 1, 4, 2, '2025-06-06 08:00:00'),
(3007, 5, 'Order #3007 by user 5', 2, 1, 4, NULL, '2025-06-07 08:00:00'),
(3008, 5, 'Order #3008 by user 5', 5, 1, 4, 3, '2025-06-08 08:00:00'),
(3009, 5, 'Order #3009 by user 5', 5, 1, 4, 1, '2025-06-09 08:00:00'),
(3010, 5, 'Order #3010 by user 5', 1, 1, 4, 3, '2025-06-10 08:00:00'),
(3011, 5, 'Order #3011 by user 5', 2, 1, 4, 1, '2025-06-11 08:00:00'),
(3012, 5, 'Order #3012 by user 5', 2, 1, 4, NULL, '2025-06-12 08:00:00'),
(3013, 5, 'Order #3013 by user 5', 3, 1, 4, NULL, '2025-06-13 08:00:00'),
(3014, 5, 'Order #3014 by user 5', 3, 1, 4, 2, '2025-06-14 08:00:00'),
(3015, 5, 'Order #3015 by user 5', 2, 1, 4, 2, '2025-06-15 08:00:00'),
(3016, 5, 'Order #3016 by user 5', 5, 1, 4, 3, '2025-06-16 08:00:00'),
(3017, 5, 'Order #3017 by user 5', 4, 1, 4, 1, '2025-06-17 08:00:00'),
(3018, 5, 'Order #3018 by user 5', 4, 1, 4, NULL, '2025-06-18 08:00:00'),
(3019, 5, 'Order #3019 by user 5', 3, 1, 4, 3, '2025-06-19 08:00:00'),
(3020, 5, 'Order #3020 by user 5', 3, 1, 4, 2, '2025-06-20 08:00:00');

INSERT INTO order_detail (order_id, variation_id, total_quantity, total_price)
VALUES
(3001, 2, 1, 250000),
(3002, 2, 1, 200000),
(3003, 1, 3, 600000),
(3004, 11, 3, 570000),
(3004, 2, 1, 250000),
(3005, 6, 3, 600000),
(3005, 4, 3, 630000),
(3005, 9, 2, 420000),
(3006, 12, 3, 570000),
(3006, 1, 2, 400000),
(3006, 9, 1, 210000),
(3007, 1, 3, 600000),
(3007, 2, 3, 570000),
(3008, 6, 2, 380000),
(3008, 5, 1, 250000),
(3009, 6, 1, 190000),
(3009, 8, 3, 570000),
(3010, 4, 2, 420000),
(3010, 11, 2, 400000),
(3011, 3, 2, 420000),
(3012, 2, 2, 500000),
(3012, 6, 1, 190000),
(3012, 8, 3, 630000),
(3013, 3, 1, 210000),
(3013, 4, 3, 570000),
(3013, 5, 2, 500000),
(3014, 10, 3, 600000),
(3015, 5, 2, 500000),
(3015, 7, 2, 400000),
(3016, 1, 2, 400000),
(3016, 3, 1, 210000),
(3017, 8, 2, 420000),
(3018, 1, 2, 400000),
(3018, 2, 2, 380000),
(3019, 10, 2, 400000),
(3019, 3, 2, 420000),
(3020, 4, 1, 210000),
(3020, 12, 2, 380000);
