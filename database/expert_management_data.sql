-- 1. Thêm 20 chuyên gia vào bảng user
INSERT INTO user (username, password, first_name, last_name, email, phone_number, role_id)
VALUES
('expert1', 'pass123', 'Nguyen', 'Anh', 'expert1@example.com', '0900000001', 6),
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

-- 2. Gán kỹ năng cho từng chuyên gia dựa vào username để lấy user_id chính xác

INSERT INTO expertSkill (user_id, skill_id) VALUES
((SELECT user_id FROM user WHERE username='expert1'), 1),  -- Makeup

((SELECT user_id FROM user WHERE username='expert2'), 2),  -- Skin

((SELECT user_id FROM user WHERE username='expert3'), 3),  -- Hair Care

((SELECT user_id FROM user WHERE username='expert4'), 4),  -- Perfume

((SELECT user_id FROM user WHERE username='expert5'), 1),

((SELECT user_id FROM user WHERE username='expert6'), 3),

((SELECT user_id FROM user WHERE username='expert7'), 1),
((SELECT user_id FROM user WHERE username='expert7'), 4),

((SELECT user_id FROM user WHERE username='expert8'), 2),

((SELECT user_id FROM user WHERE username='expert9'), 3),

((SELECT user_id FROM user WHERE username='expert10'), 4),

((SELECT user_id FROM user WHERE username='expert11'), 1),

((SELECT user_id FROM user WHERE username='expert12'), 2),

((SELECT user_id FROM user WHERE username='expert13'), 3),

((SELECT user_id FROM user WHERE username='expert14'), 4),

((SELECT user_id FROM user WHERE username='expert15'), 1),

((SELECT user_id FROM user WHERE username='expert16'), 2),

((SELECT user_id FROM user WHERE username='expert17'), 3),

((SELECT user_id FROM user WHERE username='expert18'), 4),

((SELECT user_id FROM user WHERE username='expert19'), 1),

((SELECT user_id FROM user WHERE username='expert20'), 2);
