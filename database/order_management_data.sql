USE natural_care;

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

-- 5. Thêm khách hàng (user_id = 2 -> 6)
INSERT INTO user (username, password, first_name, last_name, email, phone_number, role_id)
VALUES
('customer1', 'pass1', 'John', 'Doe', 'john1@example.com', '0123456789', 1),
('customer2', 'pass2', 'Jane', 'Doe', 'jane2@example.com', '0987654321', 1),
('customer3', 'pass3', 'Jim', 'Beam', 'jim3@example.com', '0112233445', 1),
('customer4', 'pass4', 'Jack', 'Daniels', 'jack4@example.com', '0998877665', 1),
('customer5', 'pass5', 'Jill', 'Valentine', 'jill5@example.com', '0223344556', 1);

-- 6. Thêm đơn hàng (dùng user_id từ 2 đến 6, shipper_id = 1)
INSERT INTO product_order (order_id, user_id, order_note, status_id, shipper_id, address_id, coupon_id, create_at)
VALUES
(3001, 5, 'Order #3001 by user 5', 2, 1, 1, 2, '2025-06-01 08:00:00'),
(3002, 5, 'Order #3002 by user 5', 5, 1, 1, 3, '2025-06-02 08:00:00'),
(3003, 5, 'Order #3003 by user 5', 4, 1, 1, 3, '2025-06-03 08:00:00'),
(3004, 5, 'Order #3004 by user 5', 5, 1, 1, 2, '2025-06-04 08:00:00'),
(3005, 5, 'Order #3005 by user 5', 3, 1, 1, NULL, '2025-06-05 08:00:00'),
(3006, 5, 'Order #3006 by user 5', 2, 1, 1, 2, '2025-06-06 08:00:00'),
(3007, 5, 'Order #3007 by user 5', 2, 1, 1, NULL, '2025-06-07 08:00:00'),
(3008, 5, 'Order #3008 by user 5', 5, 1, 1, 3, '2025-06-08 08:00:00'),
(3009, 5, 'Order #3009 by user 5', 5, 1, 1, 1, '2025-06-09 08:00:00'),
(3010, 5, 'Order #3010 by user 5', 1, 1, 1, 3, '2025-06-10 08:00:00'),
(3011, 5, 'Order #3011 by user 5', 2, 1, 1, 1, '2025-06-11 08:00:00'),
(3012, 5, 'Order #3012 by user 5', 2, 1, 1, NULL, '2025-06-12 08:00:00'),
(3013, 5, 'Order #3013 by user 5', 3, 1, 1, NULL, '2025-06-13 08:00:00'),
(3014, 5, 'Order #3014 by user 5', 3, 1, 1, 2, '2025-06-14 08:00:00'),
(3015, 5, 'Order #3015 by user 5', 2, 1, 1, 2, '2025-06-15 08:00:00');

INSERT INTO order_detail (order_id, variation_id, quantity, price)
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
