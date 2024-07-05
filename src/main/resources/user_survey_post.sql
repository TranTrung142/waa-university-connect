-- Insert sample data into the user table
INSERT INTO user (id,date_of_birth, failed_login_attempts, created_at, lock_time, updated_at, email, first_name, last_name, password, phone, profile_pictureurl, role, status)
VALUES
    (1, '1990-01-01', 0, NOW(), NULL, NOW(), 'user1@example.com', 'First1', 'Last1', 'password1', '1234567890', NULL, 'ADMIN', 'ACTIVATED'),
    (2, '1991-02-02', 0, NOW(), NULL, NOW(), 'user2@example.com', 'First2', 'Last2', 'password2', '1234567891', NULL, 'STUDENT', 'ACTIVATED'),
    (3, '1992-03-03', 0, NOW(), NULL, NOW(), 'user3@example.com', 'First3', 'Last3', 'password3', '1234567892', NULL, 'STUDENT', 'ACTIVATED'),
    (4, '1993-04-04', 0, NOW(), NULL, NOW(), 'user4@example.com', 'First4', 'Last4', 'password4', '1234567893', NULL, 'STUDENT', 'ACTIVATED'),
    (5, '1994-05-05', 0, NOW(), NULL, NOW(), 'user5@example.com', 'First5', 'Last5', 'password5', '1234567894', NULL, 'STUDENT', 'ACTIVATED'),
    (6, '1995-06-06', 0, NOW(), NULL, NOW(), 'user6@example.com', 'First6', 'Last6', 'password6', '1234567895', NULL, 'STUDENT', 'ACTIVATED'),
    (7, '1996-07-07', 0, NOW(), NULL, NOW(), 'user7@example.com', 'First7', 'Last7', 'password7', '1234567896', NULL, 'STUDENT', 'ACTIVATED'),
    (8, '1997-08-08', 0, NOW(), NULL, NOW(), 'user8@example.com', 'First8', 'Last8', 'password8', '1234567897', NULL, 'STUDENT', 'ACTIVATED'),
    (9, '1998-09-09', 0, NOW(), NULL, NOW(), 'user9@example.com', 'First9', 'Last9', 'password9', '1234567898', NULL, 'STUDENT', 'ACTIVATED'),
    (10, '1999-10-10', 0, NOW(), NULL, NOW(), 'user10@example.com', 'First10', 'Last10', 'password10', '1234567899', NULL, 'STUDENT', 'ACTIVATED');

-- Insert sample data into the student table
INSERT INTO student (id, student_id, major)
VALUES
    (2, 1001, 'Computer Science'),
    (3, 1002, 'Mathematics'),
    (4, 1003, 'Physics'),
    (5, 1004, 'Chemistry'),
    (6, 1005, 'Biology'),
    (7, 1006, 'Engineering'),
    (8, 1007, 'History'),
    (9, 1008, 'Philosophy'),
    (10, 1009, 'Literature'),
    (1, 1010, 'Economics');

-- Insert sample data into the survey table
INSERT INTO survey (id, created_at, creator_id, updated_at, description, title, status)
VALUES
    (1, NOW(), 1, NOW(), 'Survey description 1', 'Survey Title 1', 'ACTIVE'),
    (2, NOW(), 2, NOW(), 'Survey description 2', 'Survey Title 2', 'ACTIVE'),
    (3, NOW(), 3, NOW(), 'Survey description 3', 'Survey Title 3', 'ACTIVE'),
    (4, NOW(), 4, NOW(), 'Survey description 4', 'Survey Title 4', 'ACTIVE'),
    (5, NOW(), 5, NOW(), 'Survey description 5', 'Survey Title 5', 'ACTIVE'),
    (6, NOW(), 6, NOW(), 'Survey description 6', 'Survey Title 6', 'ACTIVE'),
    (7, NOW(), 7, NOW(), 'Survey description 7', 'Survey Title 7', 'ACTIVE'),
    (8, NOW(), 8, NOW(), 'Survey description 8', 'Survey Title 8', 'ACTIVE'),
    (9, NOW(), 9, NOW(), 'Survey description 9', 'Survey Title 9', 'ACTIVE'),
    (10, NOW(), 10, NOW(), 'Survey description 10', 'Survey Title 10', 'ACTIVE');

INSERT INTO discussion_category (created_at, updated_at, name)
VALUES
    (NOW(), NOW(), 'General Discussion'),
    (NOW(), NOW(), 'Announcements'),
    (NOW(), NOW(), 'Feedback'),
    (NOW(), NOW(), 'Off-topic'),
    (NOW(), NOW(), 'News'),
    (NOW(), NOW(), 'Events'),
    (NOW(), NOW(), 'Support'),
    (NOW(), NOW(), 'Introductions'),
    (NOW(), NOW(), 'Jobs and Careers'),
    (NOW(), NOW(), 'Technology');

-- Insert sample data into the thread table
INSERT INTO thread (id, category_id, created_at, created_by, updated_at, title)
VALUES
    (1, 1, NOW(), 1, NOW(), 'Thread Title 1'),
    (2, 1, NOW(), 2, NOW(), 'Thread Title 2'),
    (3, 1, NOW(), 3, NOW(), 'Thread Title 3'),
    (4, 1, NOW(), 4, NOW(), 'Thread Title 4'),
    (5, 1, NOW(), 5, NOW(), 'Thread Title 5'),
    (6, 1, NOW(), 6, NOW(), 'Thread Title 6'),
    (7, 1, NOW(), 7, NOW(), 'Thread Title 7'),
    (8, 1, NOW(), 8, NOW(), 'Thread Title 8'),
    (9, 1, NOW(), 9, NOW(), 'Thread Title 9'),
    (10, 1, NOW(), 10, NOW(), 'Thread Title 10');

-- Insert sample data into the post table
INSERT INTO post (id, created_at, parent_post_id, thread_id, updated_at, user_id, content)
VALUES
    (1, NOW(), NULL, 1, NOW(), 2, 'Post content 1'),
    (2, NOW(), NULL, 1, NOW(), 3, 'Post content 2'),
    (3, NOW(), NULL, 1, NOW(), 4, 'Post content 3'),
    (4, NOW(), NULL, 1, NOW(), 5, 'Post content 4'),
    (5, NOW(), NULL, 1, NOW(), 6, 'Post content 5'),
    (6, NOW(), NULL, 2, NOW(), 7, 'Post content 6'),
    (7, NOW(), NULL, 2, NOW(), 8, 'Post content 7'),
    (8, NOW(), NULL, 2, NOW(), 9, 'Post content 8'),
    (9, NOW(), NULL, 2, NOW(), 10, 'Post content 9'),
    (10, NOW(), NULL, 2, NOW(), 2, 'Post content 10'),
    (11, NOW(), NULL, 3, NOW(), 3, 'Post content 11'),
    (12, NOW(), NULL, 3, NOW(), 4, 'Post content 12'),
    (13, NOW(), NULL, 3, NOW(), 5, 'Post content 13'),
    (14, NOW(), NULL, 3, NOW(), 6, 'Post content 14'),
    (15, NOW(), NULL, 3, NOW(), 7, 'Post content 15'),
    (16, NOW(), NULL, 4, NOW(), 8, 'Post content 16'),
    (17, NOW(), NULL, 4, NOW(), 9, 'Post content 17'),
    (18, NOW(), NULL, 4, NOW(), 10, 'Post content 18'),
    (19, NOW(), NULL, 4, NOW(), 2, 'Post content 19'),
    (20, NOW(), NULL, 4, NOW(), 3, 'Post content 20');
