-- liquibase formatted sql

-- changeset bezrukov-p:initial-refresh-tokens
-- comment: Создание таблицы refresh_tokens для хранения refresh токенов
CREATE TABLE refresh_tokens (
                                token VARCHAR(60) PRIMARY KEY,
                                user_id uuid NOT NULL,
                                expires_at TIMESTAMP NOT NULL,
                                revoked BOOLEAN NOT NULL DEFAULT false,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- changeset bezrukov-p:add-refresh-tokens-foreign-key
-- comment: Добавление внешнего ключа на таблицу users
ALTER TABLE refresh_tokens
    ADD CONSTRAINT fk_refresh_token_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE;

-- rollback ALTER TABLE refresh_tokens DROP CONSTRAINT fk_refresh_token_user;
-- rollback DROP TABLE refresh_tokens;