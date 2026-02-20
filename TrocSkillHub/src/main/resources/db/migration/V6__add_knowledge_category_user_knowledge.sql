-- V6__add_knowledge_category_user_knowledge.sql

-- 1. Create category table
CREATE TABLE "category" (
    "id" BIGSERIAL PRIMARY KEY,
    "name" VARCHAR(255)
);

-- 2. Create knowledge table
CREATE TABLE "knowledge" (
    "id" BIGSERIAL PRIMARY KEY,
    "name" VARCHAR(255),
    "level" VARCHAR(255),
    "category_id" BIGINT,
    CONSTRAINT fk_knowledge_category
        FOREIGN KEY ("category_id") REFERENCES "category"("id") ON DELETE SET NULL
);

-- 3. Create user_knowledge join table
CREATE TABLE "user_knowledge" (
    "id" BIGSERIAL PRIMARY KEY,
    "user_id" BIGINT NOT NULL,
    "knowledge_id" BIGINT NOT NULL,
    CONSTRAINT fk_user_knowledge_user
        FOREIGN KEY ("user_id") REFERENCES "users"("id") ON DELETE CASCADE,
    CONSTRAINT fk_user_knowledge_knowledge
        FOREIGN KEY ("knowledge_id") REFERENCES "knowledge"("id") ON DELETE CASCADE
);