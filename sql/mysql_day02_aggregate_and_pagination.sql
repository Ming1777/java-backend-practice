-- MySQL Day 2：聚合函数、分组查询与分页
USE java_backend_practice;

-- 1. 聚合函数：统计未删除用户
SELECT COUNT(*) AS user_count,
       SUM(age) AS total_age,
       AVG(age) AS average_age,
       MAX(age) AS maximum_age,
       MIN(age) AS minimum_age
FROM users
WHERE is_deleted = 0;

-- COUNT(*) 统计行数，COUNT(age) 只统计 age 不为 NULL 的行
SELECT COUNT(*) AS row_count,
       COUNT(age) AS non_null_age_count
FROM users
WHERE is_deleted = 0;

-- 2. GROUP BY：按照用户状态分组统计
SELECT status,
       COUNT(*) AS user_count
FROM users
WHERE is_deleted = 0
GROUP BY status
ORDER BY user_count DESC;

-- 3. HAVING：分组后，只保留用户数量不少于 2 的组
SELECT status,
       COUNT(*) AS user_count
FROM users
WHERE is_deleted = 0
GROUP BY status
HAVING COUNT(*) >= 2
ORDER BY user_count DESC;

-- 4. 按年龄分组统计
SELECT age,
       COUNT(*) AS user_count
FROM users
WHERE is_deleted = 0
GROUP BY age
ORDER BY age ASC;

-- 5. 分页查询：每页 2 条
-- 第 1 页：(1 - 1) * 2 = 0
SELECT id, username, age, status
FROM users
WHERE is_deleted = 0
ORDER BY id ASC
LIMIT 0, 2;

-- 第 2 页：(2 - 1) * 2 = 2
SELECT id, username, age, status
FROM users
WHERE is_deleted = 0
ORDER BY id ASC
LIMIT 2, 2;

-- 分页时通常还要查询总记录数，方便前端计算总页数
SELECT COUNT(*) AS total
FROM users
WHERE is_deleted = 0;

-- 分页公式：offset = (page_number - 1) * page_size
-- 通用写法：LIMIT offset, page_size
