-- V4__add_field_role_users.sql

ALTER TABLE "users" 
ADD COLUMN role VARCHAR(20) NOT NULL DEFAULT 'USER';

ALTER TABLE "users"
ADD CONSTRAINT chk_user_role CHECK (role IN ('USER', 'ADMIN'));