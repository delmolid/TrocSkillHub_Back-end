-- V12__create_password_reset_request_table.sql

CREATE TABLE password_reset_request (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    code_hash VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    attempts INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

ALTER TABLE password_reset_request
    ADD CONSTRAINT fk_prr_user
    FOREIGN KEY (user_id) REFERENCES "users"(id) ON DELETE CASCADE;

CREATE INDEX idx_prr_user_id ON password_reset_request(user_id);
CREATE INDEX idx_prr_expires_at ON password_reset_request(expires_at);
