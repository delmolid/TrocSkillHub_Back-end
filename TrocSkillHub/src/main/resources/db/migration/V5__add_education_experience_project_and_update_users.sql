-- V5__add_education_experience_project_and_foreign_keys.sql

-- 1. Create education table
CREATE TABLE "education" (
    "id" BIGSERIAL PRIMARY KEY,
    "name" VARCHAR(255),
    "school" VARCHAR(255),
    "date_start" DATE,
    "date_end" DATE
);

-- 2. Create experience table
CREATE TABLE "experience" (
    "id" BIGSERIAL PRIMARY KEY,
    "company" VARCHAR(255),
    "job" VARCHAR(255),
    "date_start" DATE,
    "date_end" DATE
);

-- 3. Create project table
CREATE TABLE "project" (
    "id" BIGSERIAL PRIMARY KEY,
    "name" VARCHAR(255),
    "description" TEXT,
    "links" TEXT,
    "date_start" DATE,
    "date_end" DATE
);

-- 4. Add foreign key columns to users table
ALTER TABLE "users"
    ADD COLUMN "education_id" BIGINT,
    ADD COLUMN "experience_id" BIGINT,
    ADD COLUMN "projet_id" BIGINT;

-- 5. Add foreign key constraints to users table
ALTER TABLE "users"
    ADD CONSTRAINT fk_users_education
        FOREIGN KEY ("education_id") REFERENCES "education"("id") ON DELETE SET NULL,
    ADD CONSTRAINT fk_users_experience
        FOREIGN KEY ("experience_id") REFERENCES "experience"("id") ON DELETE SET NULL,
    ADD CONSTRAINT fk_users_project
        FOREIGN KEY ("projet_id") REFERENCES "project"("id") ON DELETE SET NULL;