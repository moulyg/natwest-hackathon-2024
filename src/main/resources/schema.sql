CREATE TABLE cashback (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_no VARCHAR(255) NOT NULL,
    payment_id VARCHAR(255) NOT NULL,
    cashback_value DECIMAL(10, 2) NOT NULL,
    transaction_amount DECIMAL(10, 2) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_account_payment UNIQUE (account_no, payment_id)
);
