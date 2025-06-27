USE natural_care;

INSERT INTO order_status (status_id, status_name)
VALUES (1, 'Pending'),
       (2, 'Processing'),
       (3, 'Assigned to Shipper'),
       (4, 'Shipped'),
       (5, 'Delivered'),
       (6, 'Cancelled'),
       (7, 'Returned'),
       (8, 'Refunded');

INSERT INTO blog_category (blog_category_name)
VALUES ('Lip Care'),
       ('Facial Care'),
       ('Hair and Scalp Care'),
       ('Body Care');

INSERT INTO role (role_id, role_name)
VALUES (1, 'Customer'),
       (2, 'Staff'),
       (3, 'Admin'),
       (4, 'Manager'),
       (5, 'Marketer'),
       (6, 'Expert'),
       (7, 'Shipper');

INSERT INTO skill (skill_name)
VALUES ('Makeup Expert'),
       ('Skin Expert'),
       ('Hair Care Experts'),
       ('Perfume Expert');

INSERT INTO product_category (product_category_name, status)
VALUES ('Sale', TRUE),
       ('Makeup', TRUE),
       ('Skin', TRUE),
       ('Hair', TRUE),
       ('Beauty Drink', TRUE),
       ('Body Care', TRUE),
       ('Baby Care', TRUE),
       ('Fragrance', TRUE),
       ('Gifts', TRUE),
       ('Product Sets', TRUE);

INSERT INTO sub_product_category (sub_product_category_id, sub_product_category_name, product_category_id, status)
VALUES (1, 'Skin Care Combo', 1, TRUE),
       (2, 'Hair Care Combo', 1, TRUE),
       (3, 'Lip Care Combo', 1, TRUE),
       (4, 'Other Combos', 1, TRUE),
       (5, 'Black Green Day', 1, TRUE),
       (6, 'Lip Balm', 2, TRUE),
       (7, 'Colored Lipstick', 2, TRUE),
       (8, 'Lip Scrub', 2, TRUE),
       (9, 'Foundation', 2, TRUE),
       (10, 'Blush', 2, TRUE),
       (11, 'Shampoo', 4, TRUE),
       (12, 'Hair Conditioner', 4, TRUE),
       (13, 'Beauty Drink', 5, TRUE),
       (14, 'Natural Soap', 6, TRUE),
       (15, 'Natural Shower Gel', 6, TRUE),
       (16, 'Body Lotion', 6, TRUE),
       (17, 'Body Scrub', 6, TRUE),
       (18, 'Feminine Hygiene', 6, TRUE),
       (19, 'Oral Care', 6, TRUE),
       (20, 'Baby Bath', 7, TRUE),
       (21, 'Baby Care', 7, TRUE),
       (22, 'Pure Essential Oil', 8, TRUE),
       (23, 'Therapeutic Essential Oil', 8, TRUE),
       (24, 'Smart Hanging Oil', 8, TRUE),
       (25, 'Solid Perfume', 8, TRUE),
       (26, 'Spray Perfume', 8, TRUE),
       (27, 'Under 300k', 9, TRUE),
       (28, 'Under 500k', 9, TRUE),
       (29, 'Under 800k', 9, TRUE),
       (30, 'Anti-aging Ginseng Skin Care Set', 10, TRUE),
       (31, 'Centella Skin Care Set', 10, TRUE),
       (32, 'Silkworm Skin Care Set', 10, TRUE),
       (33, 'Acerola Skin Care Set', 10, TRUE);

INSERT INTO user (username, password, first_name, last_name, email, phone_number, role_id)
VALUES ('staff', 'staff', 'test', 'staff', 'test@gmail.com', '0123456789', 2);

INSERT INTO user (username, password, first_name, last_name, email, phone_number, role_id)
VALUES ('shipper', 'shipper', 'test', 'shipper', 'test@gmail.com', '0123456789', 7);

INSERT INTO user (username, password, first_name, last_name, email, phone_number, role_id)
VALUES ('admin', 'admin', 'test', 'admin', 'test@gmail.com', '0123456789', 3);

INSERT INTO user (username, password, first_name, last_name, email, phone_number, role_id)
VALUES ('manager', 'manager', 'test', 'manager', 'test@gmail.com', '0123456789', 4);

INSERT INTO user (username, password, first_name, last_name, email, phone_number, role_id)
VALUES ('lamtung', 'lamtung', 'lamtung', 'test', 'kojkon02@gmail.com', '0989785184', 1);

INSERT INTO product (product_name, product_short_description, product_information, product_guideline,
                     sub_product_category_id)
VALUES ('Ginseng Skin Care Combo', 'A luxurious set for radiant skin',
        'This combo includes cleanser, toner, and moisturizer infused with ginseng extract.',
        'Use daily for best results. Cleanse, tone, then moisturize.', 1),
       ('Hair Repair Combo', 'Revitalize damaged hair',
        'Contains shampoo, conditioner, and hair mask for deep nourishment.', 'Apply weekly for optimal hair health.',
        2),
       ('Lip Care Essentials', 'Soft and smooth lips', 'Includes lip balm and scrub for daily lip care.',
        'Exfoliate with scrub, then apply balm.', 3),
       ('Organic Skin Combo', 'Natural glow in one set', 'A complete skincare routine with organic ingredients.',
        'Follow the included guide for daily use.', 4),
       ('Green Tea Detox Set', 'Detoxify your skin', 'Green tea-based products for a refreshed complexion.',
        'Use morning and night.', 5),
       ('Hydrating Lip Balm', 'Long-lasting moisture', 'Made with shea butter and natural oils.',
        'Apply as needed throughout the day.', 6),
       ('Matte Red Lipstick', 'Bold and vibrant color', 'Long-wear lipstick with a matte finish.',
        'Apply evenly on lips.', 7),
       ('Sugar Lip Scrub', 'Gentle exfoliation', 'Removes dead skin for smoother lips.', 'Use 2-3 times per week.', 8),
       ('Lightweight Foundation', 'Natural coverage', 'Breathable formula for all-day wear.',
        'Apply with a sponge or brush.', 9),
       ('Peach Blush', 'Natural flush', 'Blends seamlessly for a radiant glow.', 'Apply to cheeks with a brush.', 10),
       ('Herbal Shampoo', 'Gentle cleansing', 'Sulfate-free shampoo for all hair types.',
        'Massage into wet hair, then rinse.', 11),
       ('Nourishing Conditioner', 'Softens and strengthens', 'Infused with argan oil for shiny hair.',
        'Apply after shampoo, leave for 2 minutes.', 12),
       ('Collagen Beauty Drink', 'Boost skin elasticity', 'Contains collagen and vitamins for skin health.',
        'Drink one bottle daily.', 13),
       ('Lavender Natural Soap', 'Soothing and cleansing', 'Made with pure lavender essential oil.',
        'Use daily in shower or bath.', 14),
       ('Citrus Shower Gel', 'Refreshing cleanse', 'Energizing shower gel with citrus extracts.',
        'Lather and rinse in the shower.', 15),
       ('Hydrating Body Lotion', 'Deep moisturization', 'Non-greasy formula for soft skin.', 'Apply after showering.',
        16),
       ('Coffee Body Scrub', 'Exfoliates and smooths', 'Removes dead skin with natural coffee grounds.',
        'Use 2-3 times per week.', 17),
       ('Feminine Wash', 'Gentle and pH-balanced', 'Designed for daily intimate care.', 'Use during bathing.', 18),
       ('Herbal Toothpaste', 'Natural oral care', 'Fluoride-free with mint and herbal extracts.', 'Brush twice daily.',
        19),
       ('Baby Shampoo', 'Tear-free formula', 'Gentle cleansing for baby’s delicate hair.', 'Use during bath time.', 20),
       ('Baby Lotion', 'Softens baby skin', 'Hypoallergenic and fragrance-free.', 'Apply after bathing.', 21),
       ('Lavender Essential Oil', 'Calming aroma', '100% pure essential oil for aromatherapy.', 'Use in a diffuser.',
        22),
       ('Eucalyptus Oil', 'Therapeutic relief', 'Supports respiratory health and relaxation.',
        'Dilute before applying to skin.', 23),
       ('Car Hanging Oil', 'Freshens car interior', 'Long-lasting scent for your vehicle.', 'Hang in car.', 24),
       ('Rose Solid Perfume', 'Portable fragrance', 'Natural perfume in a compact tin.', 'Apply to pulse points.', 25),
       ('Ocean Breeze Spray', 'Refreshing mist', 'Light perfume for everyday use.', 'Spray lightly on skin.', 26),
       ('Mini Gift Set', 'Perfect small gift', 'Includes lip balm and mini lotion.', 'Gift for any occasion.', 27),
       ('Deluxe Gift Set', 'Luxury skincare gift', 'Contains cleanser, moisturizer, and mask.',
        'Gift for special occasions.', 28),
       ('Premium Gift Box', 'Ultimate pampering', 'Full-size products in an elegant box.', 'Ideal for gifting.', 29),
       ('Ginseng Anti-Aging Cream', 'Reduces wrinkles', 'Infused with ginseng for youthful skin.', 'Apply nightly.',
        30),
       ('Centella Calming Mask', 'Soothes irritation', 'Hydrating mask for sensitive skin.', 'Use weekly.', 31),
       ('Silkworm Essence Serum', 'Brightens complexion', 'Lightweight serum for radiant skin.',
        'Apply after cleansing.', 32),
       ('Acerola Vitamin C Cream', 'Brightens skin', 'Rich in vitamin C for a glowing complexion.', 'Use daily.', 33),
       ('Moisturizing Lip Balm', 'Intense hydration', 'Infused with honey and aloe vera.', 'Apply as needed.', 6),
       ('Glossy Pink Lipstick', 'Shiny finish', 'Vibrant color with a glossy look.', 'Apply evenly on lips.', 7),
       ('Mint Lip Scrub', 'Refreshes lips', 'Gentle exfoliation with mint extract.', 'Use 2-3 times per week.', 8),
       ('Medium Coverage Foundation', 'Flawless finish', 'Buildable coverage for all skin types.',
        'Blend with a sponge.', 9),
       ('Coral Blush', 'Vibrant glow', 'Adds a pop of color to cheeks.', 'Apply lightly to cheeks.', 10),
       ('Anti-Dandruff Shampoo', 'Scalp care', 'Soothes itchy scalp and reduces flakes.', 'Use twice weekly.', 11),
       ('Repairing Conditioner', 'Restores damaged hair', 'Strengthens hair with keratin.', 'Apply after shampoo.', 12),
       ('Vitamin Beauty Drink', 'Supports skin health', 'Packed with antioxidants and vitamins.', 'Drink daily.', 13),
       ('Rose Natural Soap', 'Gentle cleansing', 'Made with rose petals and essential oil.', 'Use in shower.', 14),
       ('Aloe Shower Gel', 'Soothing cleanse', 'Hydrates skin with aloe vera.', 'Lather and rinse.', 15),
       ('Shea Butter Lotion', 'Nourishes skin', 'Rich lotion for dry skin.', 'Apply daily.', 16),
       ('Sugar Body Scrub', 'Smooths skin', 'Exfoliates with natural sugar crystals.', 'Use weekly.', 17),
       ('Herbal Feminine Wash', 'Natural care', 'Gentle formula for daily use.', 'Use during bathing.', 18),
       ('Charcoal Toothpaste', 'Whitens teeth', 'Natural whitening with activated charcoal.', 'Brush twice daily.', 19),
       ('Baby Bubble Bath', 'Fun and gentle', 'Creates soft bubbles for baby’s bath.', 'Use during bath time.', 20),
       ('Baby Diaper Cream', 'Protects sensitive skin', 'Soothes and prevents diaper rash.',
        'Apply during diaper changes.', 21),
       ('Peppermint Essential Oil', 'Invigorating scent', 'Pure oil for aromatherapy and massage.',
        'Dilute before use.', 22);

INSERT INTO color (color_id, color_name)
VALUES (1, 'Red'),
       (2, 'Pink'),
       (3, 'Nude'),
       (4, 'Coral'),
       (5, 'Berry'),
       (6, 'Plum'),
       (7, 'Brown'),
       (8, 'Mauve'),
       (9, 'Orange'),
       (10, 'Wine');

INSERT INTO size (size_id, size_name)
VALUES (1, '50ml'),
       (2, '100ml'),
       (3, '200ml'),
       (4, '300ml'),
       (5, '400ml'),
       (6, '500ml'),
       (7, '600ml'),
       (8, '700ml'),
       (9, '800ml'),
       (10, '900ml'),
       (11, '1000ml');

INSERT INTO product_variation (product_id, product_image, color_id, size_id, price, sell_price, qty_in_stock)
VALUES (7, '/images/product/lipstick-pink.jpg', 2, null, 100000, 200000, 100),
       (7, '/images/product/lipstick-red.jpg', 1, null, 90000, 190000, 100),
       (9, '/images/product/foundation-diro.jpg', 3, null, 110000, 210000, 100),
       (9, '/images/product/foundation-este.jpg', 2, null, 150000, 250000, 100);


INSERT INTO blog (blog_title, blog_description, blog_category_id)
VALUES ('08 Son Dưỡng Môi Sau Khi Xăm',
        'Son dưỡng giúp môi lên màu đẹp sau xăm. Dưới đây là những dòng được chuyên gia khuyên dùng.', 1),
       ('Top 5 Son Dưỡng Không Màu',
        'Danh sách son không màu giúp môi mềm, không gây kích ứng, thích hợp dùng mỗi ngày.', 1),
       ('Cách Tẩy Tế Bào Chết Môi', 'Tẩy tế bào chết giúp môi hồng hào, hỗ trợ son lên màu mượt hơn.', 1),
       ('Dưỡng Môi Ban Đêm Với Dầu Thiên Nhiên', 'Dầu dừa, argan giúp dưỡng ẩm và giảm thâm môi hiệu quả.', 1),
       ('So Sánh Các Dòng Son Dưỡng Có Màu', 'Tư vấn chọn son dưỡng có màu vừa làm đẹp vừa bảo vệ môi tốt.', 1);


INSERT INTO blog (blog_title, blog_description, blog_category_id)
VALUES ('Nên Uống Collagen Hay Viên Trắng Da?',
        'So sánh ưu nhược điểm của collagen và viên trắng da để bạn chọn sản phẩm phù hợp.', 2),
       ('Chăm Sóc Da Mùa Hè Đúng Cách',
        'Nắng nóng khiến da dễ tổn thương, hãy áp dụng các bước sau để bảo vệ làn da của bạn.', 2),
       ('Quy Trình Dưỡng Da Ban Đêm', 'Hướng dẫn chăm sóc da đúng cách vào ban đêm để da phục hồi tốt hơn.', 2),
       ('Top 5 Mặt Nạ Cho Da Nhạy Cảm', 'Gợi ý mặt nạ thiên nhiên dịu nhẹ cho da dễ kích ứng.', 2),
       ('Tẩy Trang Cho Da Dầu Không Mụn', 'Tẩy trang sạch là bước quan trọng để ngăn ngừa mụn. Đừng bỏ qua!', 2);


INSERT INTO blog (blog_title, blog_description, blog_category_id)
VALUES ('Cách Gội Đầu Giúp Tóc Chắc Khỏe', 'Gội sai cách khiến tóc rụng nhiều, đây là cách đúng để cải thiện.', 3),
       ('Dưỡng Tóc Với Tinh Dầu Tự Nhiên', 'Dầu bưởi, argan và olive giúp tóc phục hồi rõ rệt.', 3),
       ('Dấu Hiệu Nấm Da Đầu & Cách Trị', 'Ngứa da đầu có thể do nấm, hãy nhận biết và xử lý kịp thời.', 3),
       ('Cách Làm Tóc Bóng Mượt Tự Nhiên', 'Không cần hấp – chỉ cần 5 phút mỗi ngày với mẹo này.', 3);


INSERT INTO blog (blog_title, blog_description, blog_category_id)
VALUES ('Body Lotion Cho Da Khô', 'Chọn sản phẩm dưỡng thể có độ ẩm cao, chứa thành phần thiên nhiên.', 4),
       ('Dưỡng Trắng Body Tại Nhà', 'Các bước dưỡng trắng toàn thân từ nguyên liệu dễ tìm.', 4),
       ('Tẩy Da Chết Body Với Cà Phê', 'Công thức cực dễ mà cực kỳ hiệu quả!', 4);

INSERT INTO blog_image (blog_id, blog_image)
VALUES (1, 'images/blog/son-duong.jpg'),
       (2, 'images/blog/son-duong-khong-mau.jpg'),
       (3, 'images/blog/tay-te-bao-chet-moi.jpg'),
       (4, 'images/blog/duong-moi-ban-dem.jpg'),
       (5, 'images/blog/son-duong-co-mau.jpg'),
       (6, 'images/blog/colagen.jpg'),
       (7, 'images/blog/cham-soc-da-mua-he.jpg'),
       (8, 'images/blog/duong-da-ban-dem.jpg'),
       (9, 'images/blog/mat-na-cho-da-nhay-cam.jpg'),
       (10, 'images/blog/tay-trang.jpg'),
       (11, 'images/blog/goi-dau.jpg'),
       (12, 'images/blog/tinh-dau-duong-toc.jpg'),
       (13, 'images/blog/cach-tri-nam-da-dau-tai-nha.jpg'),
       (14, 'images/blog/mem-muot-toc.jpg'),
       (15, 'images/blog/body-lotion.jpg'),
       (16, 'images/blog/duong-trang-body.jpg'),
       (17, 'images/blog/tay-da-chet-body.jpg');

INSERT INTO user (username, password, first_name, last_name, email, phone_number, role_id)
VALUES ('expert1', 'pass123', 'Nguyen', 'Anh', 'expert1@example.com', '0900000001', 6),
       ('expert2', 'pass123', 'Tran', 'Binh', 'expert2@example.com', '0900000002', 6),
       ('expert3', 'pass123', 'Le', 'Chi', 'expert3@example.com', '0900000003', 6),
       ('expert4', 'pass123', 'Pham', 'Duc', 'expert4@example.com', '0900000004', 6),
       ('expert5', 'pass123', 'Hoang', 'Em', 'expert5@example.com', '0900000005', 6),
       ('expert6', 'pass123', 'Do', 'Hung', 'expert6@example.com', '0900000006', 6),
       ('expert7', 'pass123', 'Vu', 'Khanh', 'expert7@example.com', '0900000007', 6),
       ('expert8', 'pass123', 'Bui', 'Lam', 'expert8@example.com', '0900000008', 6),
       ('expert9', 'pass123', 'Ngo', 'Minh', 'expert9@example.com', '0900000009', 6),
       ('expert10', 'pass123', 'Dinh', 'Nam', 'expert10@example.com', '0900000010', 6),
       ('expert11', 'pass123', 'Tran', 'Oanh', 'expert11@example.com', '0900000011', 6),
       ('expert12', 'pass123', 'Le', 'Phuc', 'expert12@example.com', '0900000012', 6),
       ('expert13', 'pass123', 'Pham', 'Quang', 'expert13@example.com', '0900000013', 6),
       ('expert14', 'pass123', 'Hoang', 'Sang', 'expert14@example.com', '0900000014', 6),
       ('expert15', 'pass123', 'Do', 'Thao', 'expert15@example.com', '0900000015', 6),
       ('expert16', 'pass123', 'Vu', 'Uyen', 'expert16@example.com', '0900000016', 6),
       ('expert17', 'pass123', 'Bui', 'Van', 'expert17@example.com', '0900000017', 6),
       ('expert18', 'pass123', 'Ngo', 'Xuan', 'expert18@example.com', '0900000018', 6),
       ('expert19', 'pass123', 'Dinh', 'Yen', 'expert19@example.com', '0900000019', 6),
       ('expert20', 'pass123', 'Tran', 'Zan', 'expert20@example.com', '0900000020', 6);

INSERT INTO expertSkill (user_id, skill_id)
VALUES ((SELECT user_id FROM user WHERE username = 'expert1'), 1), -- Makeup

       ((SELECT user_id FROM user WHERE username = 'expert2'), 2), -- Skin

       ((SELECT user_id FROM user WHERE username = 'expert3'), 3), -- Hair Care

       ((SELECT user_id FROM user WHERE username = 'expert4'), 4), -- Perfume

       ((SELECT user_id FROM user WHERE username = 'expert5'), 1),

       ((SELECT user_id FROM user WHERE username = 'expert6'), 3),

       ((SELECT user_id FROM user WHERE username = 'expert7'), 1),
       ((SELECT user_id FROM user WHERE username = 'expert7'), 4),

       ((SELECT user_id FROM user WHERE username = 'expert8'), 2),

       ((SELECT user_id FROM user WHERE username = 'expert9'), 3),

       ((SELECT user_id FROM user WHERE username = 'expert10'), 4),

       ((SELECT user_id FROM user WHERE username = 'expert11'), 1),

       ((SELECT user_id FROM user WHERE username = 'expert12'), 2),

       ((SELECT user_id FROM user WHERE username = 'expert13'), 3),

       ((SELECT user_id FROM user WHERE username = 'expert14'), 4),

       ((SELECT user_id FROM user WHERE username = 'expert15'), 1),

       ((SELECT user_id FROM user WHERE username = 'expert16'), 2),

       ((SELECT user_id FROM user WHERE username = 'expert17'), 3),

       ((SELECT user_id FROM user WHERE username = 'expert18'), 4),

       ((SELECT user_id FROM user WHERE username = 'expert19'), 1),

       ((SELECT user_id FROM user WHERE username = 'expert20'), 2);

INSERT INTO province (code, name, full_name)
VALUES ('01', 'Hanoi', 'Thành phố Hà Nội');

INSERT INTO district (code, name, full_name, province_code)
VALUES ('001', 'Ba Dinh', 'Quận Ba Đình', '01'),
       ('002', 'Dong Da', 'Quận Đống Đa', '01'),
       ('003', 'Cau Giay', 'Quận Cầu Giấy', '01'),
       ('004', 'Thanh Xuan', 'Quận Thanh Xuân', '01'),
       ('005', 'Hoan Kiem', 'Quận Hoàn Kiếm', '01');

INSERT INTO ward (code, name, full_name, district_code)
VALUES ('0001', 'Phuc Xa', 'Phường Phúc Xá', '001'),
       ('0002', 'Lang Ha', 'Phường Láng Hạ', '002'),
       ('0003', 'Dich Vong', 'Phường Dịch Vọng', '003'),
       ('0004', 'Khuong Thuong', 'Phường Khương Thượng', '004'),
       ('0005', 'Hang Bac', 'Phường Hàng Bạc', '005');

INSERT INTO address (province_code, district_code, ward_code, detail, distance_km)
VALUES ('01', '001', '0001', '123 Main St', 2.5),
       ('01', '002', '0002', '456 Second St', 3.2),
       ('01', '003', '0003', '789 Third St', 1.8),
       ('01', '004', '0004', '101 First Ave', 2.1),
       ('01', '005', '0005', '202 Second Ave', 2.9);

INSERT INTO coupon (code, discount_percent, min_order_amount, valid_from, valid_to, is_active, usage_limit, times_used,
                    is_user_specific)
VALUES ('DISCOUNT10', 10, 100000, '2025-01-01', '2025-12-31', TRUE, 100, 0, FALSE),
       ('DISCOUNT20', 20, 200000, '2025-01-01', '2025-12-31', TRUE, 50, 0, FALSE),
       ('DISCOUNT30', 30, 300000, '2025-06-01', '2025-12-31', TRUE, NULL, 0, TRUE);

INSERT INTO user (username, password, first_name, last_name, email, phone_number, role_id)
VALUES ('shipper1', 'passship1', 'Shipper', 'One', 'shipper1@example.com', '0333444555', 7);

INSERT INTO userAddress (user_id, address_id, is_default)
VALUES (1, 1, TRUE);

INSERT INTO user (username, password, first_name, last_name, email, phone_number, role_id)
VALUES ('customer1', 'pass1', 'John', 'Doe', 'john1@example.com', '0123456789', 1),
       ('customer2', 'pass2', 'Jane', 'Doe', 'jane2@example.com', '0987654321', 1),
       ('customer3', 'pass3', 'Jim', 'Beam', 'jim3@example.com', '0112233445', 1),
       ('customer4', 'pass4', 'Jack', 'Daniels', 'jack4@example.com', '0998877665', 1),
       ('customer5', 'pass5', 'Jill', 'Valentine', 'jill5@example.com', '0223344556', 1);

INSERT INTO userAddress (user_id, address_id, is_default)
VALUES (2, 1, TRUE),
       (3, 2, TRUE),
       (4, 3, TRUE),
       (5, 4, TRUE),
       (6, 5, TRUE);

INSERT INTO product_order (user_id, order_note, status_id, shipper_id, address_id, coupon_id)
VALUES (2, 'Order note 1 - pending', 1, NULL, 1, NULL),
       (2, 'Order note 2 - processing', 2, NULL, 1, NULL),
       (2, 'Order note 3 - shipped', 4, 1, 1, NULL),

       (3, 'Order note 4 - assigned to shipper', 3, 1, 2, 1),
       (3, 'Order note 5 - delivered', 5, 1, 2, 2),

       (4, 'Order note 6 - cancelled', 6, NULL, 3, NULL),
       (4, 'Order note 7 - returned', 7, NULL, 3, NULL),

       (5, 'Order note 8 - refunded', 8, NULL, 4, 3),

       (6, 'Order note 9 - pending', 1, NULL, 5, NULL),
       (6, 'Order note 10 - processing', 2, NULL, 5, NULL);