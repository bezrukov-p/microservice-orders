-- liquibase formatted sql

-- changeset bezrukov-p:create_orders_table
-- comment: Создание таблицы заказов
CREATE TABLE orders (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        user_id UUID NOT NULL,
                        description TEXT,
                        status VARCHAR(20) NOT NULL DEFAULT 'CREATED'
                            CHECK (status IN ('CREATED', 'IN_PROGRESS', 'COMPLETED')),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- rollback DROP TABLE orders;