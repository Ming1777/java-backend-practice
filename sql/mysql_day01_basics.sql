-- MySQL Day 01: database, table, CRUD and basic queries
-- Date: 2026-08-02
-- Convention: SQL keywords are uppercase; database, table and column names use lowercase snake_case.

-- 1. Create and select the database.
CREATE DATABASE IF NOT EXISTS java_backend_practice
    DEFAULT CHARACTER SET utf8mb4;

USE java_backend_practice;

-- 2. Create the users table.
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '加密后的密码',
    age INT COMMENT '年龄',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1正常，0禁用',
    is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0未删除，1已删除',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '用户表';

SHOW TABLES;
DESC users;

-- 3. Sample data. Run this block only once; rerunning it will trigger the username UNIQUE constraint.
-- INSERT INTO users (
--     username,
--     password,
--     age
-- ) VALUES
--     ('xiaoming', 'hashed_password_1', 20),
--     ('xiaohong', 'hashed_password_2', 19),
--     ('xiaogang', 'hashed_password_3', 21);

-- 4. Query fields explicitly. Do not return password unless the business truly requires it.
SELECT
    id,
    username,
    age,
    status,
    is_deleted,
    created_at
FROM users;

SELECT COUNT(*) AS user_count
FROM users
WHERE is_deleted = 0;

-- 5. Conditional queries.
SELECT
    id,
    username,
    age
FROM users
WHERE status = 1
  AND is_deleted = 0
  AND age >= 20
ORDER BY age DESC
LIMIT 2;

SELECT
    id,
    username,
    age
FROM users
WHERE username LIKE 'xiao%';

SELECT
    id,
    username,
    age
FROM users
WHERE age BETWEEN 19 AND 20;

SELECT
    id,
    username,
    age
FROM users
WHERE username IN ('xiaoming', 'xiaohong');

SELECT
    id,
    username,
    age
FROM users
WHERE age IS NULL;

-- 6. Modification examples. Confirm the target with SELECT before removing the comment markers.
-- SELECT id, username, age, status
-- FROM users
-- WHERE id = 1;
--
-- UPDATE users
-- SET
--     age = 22,
--     status = 0
-- WHERE id = 1;

-- 7. Logical deletion. Prefer this for important business data such as users and orders.
-- UPDATE users
-- SET is_deleted = 1
-- WHERE id = 1;
--
-- SELECT id, username, age, status
-- FROM users
-- WHERE is_deleted = 0;

-- 8. Physical deletion example. Only use it for disposable data and always include WHERE.
-- DELETE FROM users
-- WHERE username = 'temp_user';

-- High-risk commands for interview review only. Do not execute them in this practice database.
-- TRUNCATE TABLE users; -- Clears all rows and usually resets AUTO_INCREMENT.
-- DROP TABLE users;     -- Deletes both the table structure and all data.
