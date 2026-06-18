-- liquibase formatted sql

-- changeset bezrukov-p:add-refresh-tokens-foreign-key
-- comment: Добавление внешнего ключа на таблицу users
ALTER TABLE refresh_tokens DROP CONSTRAINT IF EXISTS fk_refresh_token_user;

ALTER TABLE refresh_tokens
    ADD CONSTRAINT fk_refresh_token_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE;

-- rollback ALTER TABLE refresh_tokens DROP CONSTRAINT fk_refresh_token_user;