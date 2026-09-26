-- =========================================================
-- Employee Management & Payroll System - Database Schema
-- =========================================================

CREATE DATABASE IF NOT EXISTS employee_payroll_db;
USE employee_payroll_db;

-- 1. users (admin login)
CREATE TABLE IF NOT EXISTS users (
    user_id       INT AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(50)  NOT NULL UNIQUE,
    password_hash VARCHAR(128) NOT NULL,
    role          VARCHAR(20)  NOT NULL DEFAULT 'ADMIN',
    last_login    DATETIME NULL
);

-- 2. departments
CREATE TABLE IF NOT EXISTS departments (
    department_id   INT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL UNIQUE,
    description     VARCHAR(255)
);

-- 3. employees
CREATE TABLE IF NOT EXISTS employees (
    employee_id   INT AUTO_INCREMENT PRIMARY KEY,
    first_name    VARCHAR(50)  NOT NULL,
    last_name     VARCHAR(50)  NOT NULL,
    email         VARCHAR(100) NOT NULL UNIQUE,
    phone         VARCHAR(10)  NOT NULL,
    address       VARCHAR(255),
    gender        VARCHAR(10)  NOT NULL,
    department_id INT NOT NULL,
    designation   VARCHAR(100) NOT NULL,
    joining_date  DATE NOT NULL,
    basic_salary  DECIMAL(12,2) NOT NULL,
    status        VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    CONSTRAINT fk_employee_department FOREIGN KEY (department_id)
        REFERENCES departments(department_id)
        ON DELETE RESTRICT
);

-- 4. attendance
CREATE TABLE IF NOT EXISTS attendance (
    attendance_id   INT AUTO_INCREMENT PRIMARY KEY,
    employee_id     INT NOT NULL,
    attendance_date DATE NOT NULL,
    status          VARCHAR(20) NOT NULL,
    CONSTRAINT fk_attendance_employee FOREIGN KEY (employee_id)
        REFERENCES employees(employee_id)
        ON DELETE CASCADE,
    CONSTRAINT uq_attendance_emp_date UNIQUE (employee_id, attendance_date)
);

-- 5. salary  (detailed breakdown per employee per payroll month)
CREATE TABLE IF NOT EXISTS salary (
    salary_id        INT AUTO_INCREMENT PRIMARY KEY,
    employee_id      INT NOT NULL,
    basic_salary     DECIMAL(12,2) NOT NULL,
    hra              DECIMAL(12,2) NOT NULL,
    da               DECIMAL(12,2) NOT NULL,
    allowance        DECIMAL(12,2) NOT NULL DEFAULT 0,
    pf               DECIMAL(12,2) NOT NULL,
    professional_tax DECIMAL(12,2) NOT NULL,
    other_deduction  DECIMAL(12,2) NOT NULL DEFAULT 0,
    gross_salary     DECIMAL(12,2) NOT NULL,
    total_deduction  DECIMAL(12,2) NOT NULL,
    net_salary       DECIMAL(12,2) NOT NULL,
    payroll_month    VARCHAR(7) NOT NULL, -- format: yyyy-MM
    CONSTRAINT fk_salary_employee FOREIGN KEY (employee_id)
        REFERENCES employees(employee_id)
        ON DELETE CASCADE,
    CONSTRAINT uq_salary_emp_month UNIQUE (employee_id, payroll_month)
);


-- 6. payroll  (processing summary/status per employee per month)
CREATE TABLE IF NOT EXISTS payroll (
    payroll_id     INT AUTO_INCREMENT PRIMARY KEY,
    employee_id    INT NOT NULL,
    payroll_month  VARCHAR(7) NOT NULL,
    present_days   INT NOT NULL DEFAULT 0,
    absent_days    INT NOT NULL DEFAULT 0,
    leave_days     INT NOT NULL DEFAULT 0,
    net_salary     DECIMAL(12,2) NOT NULL,
    processed_on   DATETIME NOT NULL,
    status         VARCHAR(20) NOT NULL DEFAULT 'GENERATED',
    CONSTRAINT fk_payroll_employee FOREIGN KEY (employee_id)
        REFERENCES employees(employee_id)
        ON DELETE CASCADE,
    CONSTRAINT uq_payroll_emp_month UNIQUE (employee_id, payroll_month)
);

-- =========================================================
-- Seed data: one admin user + a couple of departments
-- Default login: username = admin, password = admin123
-- (password_hash below is SHA-256 of "admin123" -
--  matches com.employee.util.PasswordUtil.hash())
-- =========================================================
INSERT INTO users (username, password_hash, role)
VALUES ('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'ADMIN')
ON DUPLICATE KEY UPDATE username = username;

INSERT INTO departments (department_name, description) VALUES
('Human Resources', 'Handles hiring, payroll policy and employee relations'),
('Engineering', 'Product development and technical operations'),
('Finance', 'Accounts, budgeting and financial reporting')
ON DUPLICATE KEY UPDATE department_name = department_name;

select * from departments;
select * from users;
