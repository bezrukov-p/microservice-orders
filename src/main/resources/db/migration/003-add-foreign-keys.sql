-- liquibase formatted sql

-- changeset bezrukov-p:add_foreign_key_orders_users
-- comment: Добавление внешнего ключа для связи orders->users
ALTER TABLE orders
    ADD CONSTRAINT fk_orders_users
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

-- rollback ALTER TABLE orders DROP CONSTRAINT fk_orders_users;