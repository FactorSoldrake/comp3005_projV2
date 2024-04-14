

-- Data insert
INSERT INTO Member (name, pass) VALUES
('Kili Smith', 'kili'),
('Kale Tyson', 'kale'),
('Barry Fink', 'barry');

INSERT INTO Trainer (name, pass) VALUES
('Kyber Ptolemy','kyber'),
('Rohit Khanna','rohit'),
('Vishan Khanna','Vishan');


INSERT INTO Admin (name, pass) VALUES
('Chris Ptolemy','chris'),
('Gina Star', 'gina');

INSERT INTO Room_Track(name, capacity, room_status, last_maintain, next_maintain) VALUES
('Yoga Room', 10, 'Available', '2024-01-01', '2024-04-15'),
('HIIT Room', 8, 'Available', '2024-03-01', '2024-04-15'),
('Running Room', 4, 'Available', '2024-03-01', '2024-04-15');


INSERT INTO Fitness_Goal (member_ID, describe, goal_comp) VALUES
(1, 'Get enough stamina for a marathon', FALSE),
(2, 'Lose 15 pounds',FALSE),
(3, 'Increase grip strength',FALSE);

INSERT INTO Member_Routine (member_ID, routine_name, description) VALUES
(1, 'Marathon running', 'Run 2 kilometres'),
(2, 'HIIT routine', 'HIIT based exercises'),
(3, 'Grip Training', 'grip related exercises');


INSERT INTO Health_Metrics (member_ID, height, weight, age, updated_info) VALUES
(1, 160, 80, 27, CURRENT_DATE),
(2, 170, 65, 28, CURRENT_DATE),
(3, 175, 70, 29, CURRENT_DATE);



INSERT INTO Trainer_Availability (trainer_ID, avail_from, avail_to) VALUES
(1, '08:00:00', '17:00:00'),
(2, '08:00:00', '14:00:00'),
(3, '15:00:00', '20:00:00');


INSERT INTO Equipment_Track(name, maintain_last, maintain_next, condition) VALUES
('Weight set', '2024-01-01', '2024-04-15', 'Good'),
('Pull-up bars', '2024-01-01', '2024-04-15', 'Great'),
('Cycling machine', '2024-03-01', '2024-04-30', 'Best');

INSERT INTO Private_Class (trainer_ID, room_ID, member_ID, start_time, end_time, cost, comments)
VALUES (1, 1, 2, '2024-04-30 14:00:00', '2024-04-30 15:00:00', 80.00, 'One-on-one yoga session');

INSERT INTO Billing (fees, issue_date, due_date, payment_status, class_type, product_descript, member_ID)
VALUES (80.00, CURRENT_DATE, CURRENT_DATE + INTERVAL '14 days', 'issued', 'Private','One-on-one yoga session', 2);





