-- liquibase formatted sql

-- changeset bezrukov-p:create_orders_table
-- comment: Создание таблицы заказов
CREATE TABLE IF NOT EXISTS orders (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        user_id UUID NOT NULL,
                        description TEXT,
                        status VARCHAR(20) NOT NULL DEFAULT 'CREATED',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- rollback DROP TABLE orders;