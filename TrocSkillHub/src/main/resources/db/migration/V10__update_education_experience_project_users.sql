-- DROP COLUMNS education_id, experience_id and projet_id
ALTER TABLE users DROP COLUMN education_id;
ALTER TABLE users DROP COLUMN experience_id;
ALTER TABLE users DROP COLUMN projet_id;

-- ADD NEW COLUMN users_id into Education table
ALTER TABLE education ADD COLUMN users_id BIGINT;

-- ADD NEW COLUMN users_id into Experience table
ALTER TABLE experience ADD COLUMN users_id BIGINT;

-- ADD NEW COLUMN users_id into Projet table
ALTER TABLE project ADD COLUMN users_id BIGINT;