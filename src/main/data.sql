-- Step 1: Insert into User table
INSERT INTO user (id,email, password, first_name, last_name, phone, date_of_birth, profile_pictureurl, role, status)
VALUES (1,'john.doe@example.com', 'password123', 'John', 'Doe', '1234567890', '1995-08-15', 'http://example.com/john.jpg', 'STUDENT', 'ACTIVATED');

select * from user;

-- Get the last inserted User ID
SET @userId = LAST_INSERT_ID();

-- Step 2: Insert into Student table using the User ID
INSERT INTO Student (id, major)
VALUES (@userId, 'Computer Science');

-- Get the last inserted Student ID
SET @studentId = LAST_INSERT_ID();

-- Step 3: Insert into AcademicAchievements table
INSERT INTO student_academic_achievements (student_id, academic_achievements)
VALUES
    (@studentId, 'Dean\'s List'),
(@studentId, 'Scholarship Award');

-- Step 4: Insert into Interests table
INSERT INTO Interests (studentId, interest)
VALUES
(@studentId, 'Coding'),
(@studentId, 'Robotics');

-- Step 5: Insert into ExtracurricularActivities table
INSERT INTO ExtracurricularActivities (studentId, activity)
VALUES
(@studentId, 'Robotics Club'),
(@studentId, 'Tech Enthusiasts');
