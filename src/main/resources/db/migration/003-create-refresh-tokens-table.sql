-- liquibase formatted sql

-- changeset bezrukov-p:initial-refresh-tokens
-- comment: Создание таблицы refresh_tokens для хранения refresh токенов
CREATE TABLE IF NOT EXISTS refresh_tokens (
                                token VARCHAR(60) PRIMARY KEY,
                                user_id uuid NOT NULL,
                                expires_at TIMESTAMP NOT NULL,
                                revoked BOOLEAN NOT NULL DEFAULT false,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- rollback DROP TABLE refresh_tokens;