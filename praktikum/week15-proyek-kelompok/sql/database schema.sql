-- ================================================
-- AGRI-POS DATABASE SCHEMA
-- PostgreSQL DDL Script
-- ================================================

-- Drop tables if exists (untuk reset)
DROP TABLE IF EXISTS transaction_items CASCADE;
DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- ================================================
-- TABLE: users
-- Menyimpan data pengguna (Kasir & Admin)
-- ================================================
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('CASHIER', 'ADMIN')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ================================================
-- TABLE: products
-- Menyimpan data produk pertanian
-- ================================================
CREATE TABLE products (
    code VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) DEFAULT 'Umum',
    price DECIMAL(12, 2) NOT NULL CHECK (price >= 0),
    stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ================================================
-- TABLE: transactions
-- Menyimpan header transaksi penjualan
-- ================================================
CREATE TABLE transactions (
    id VARCHAR(50) PRIMARY KEY,
    transaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cashier_id INT REFERENCES users(id),
    payment_method VARCHAR(20) NOT NULL CHECK (payment_method IN ('CASH', 'EWALLET')),
    payment_details TEXT, -- JSON untuk detail pembayaran (provider, no akun, dll)
    total_amount DECIMAL(12, 2) NOT NULL CHECK (total_amount >= 0),
    status VARCHAR(20) DEFAULT 'COMPLETED' CHECK (status IN ('COMPLETED', 'CANCELLED', 'REFUNDED')),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ================================================
-- TABLE: transaction_items
-- Menyimpan detail item dalam transaksi
-- ================================================
CREATE TABLE transaction_items (
    id SERIAL PRIMARY KEY,
    transaction_id VARCHAR(50) NOT NULL REFERENCES transactions(id) ON DELETE CASCADE,
    product_code VARCHAR(20) NOT NULL REFERENCES products(code),
    product_name VARCHAR(100) NOT NULL, -- Snapshot nama produk saat transaksi
    quantity INT NOT NULL CHECK (quantity > 0),
    price_at_sale DECIMAL(12, 2) NOT NULL CHECK (price_at_sale >= 0),
    subtotal DECIMAL(12, 2) NOT NULL CHECK (subtotal >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ================================================
-- INDEXES untuk performa
-- ================================================
CREATE INDEX idx_transactions_date ON transactions(transaction_date);
CREATE INDEX idx_transactions_cashier ON transactions(cashier_id);
CREATE INDEX idx_transaction_items_trans ON transaction_items(transaction_id);
CREATE INDEX idx_products_category ON products(category);

-- ================================================
-- SEED DATA - Users
-- ================================================
INSERT INTO users (username, password, name, role) VALUES
('admin', 'admin123', 'Administrator', 'ADMIN'),
('kasir1', 'kasir123', 'Budi Santoso', 'CASHIER'),
;

-- ================================================
-- SEED DATA - Products
-- ================================================
INSERT INTO products (code, name, category, price, stock) VALUES
('P001', 'Beras Premium 5kg', 'Pangan', 75000, 100),
('P002', 'Pupuk Urea 50kg', 'Pupuk', 150000, 50),
('P003', 'Pestisida Organik 1L', 'Pestisida', 85000, 30),
('P004', 'Bibit Padi Hibrida', 'Bibit', 45000, 200),
('P005', 'Jagung Pipilan 10kg', 'Pangan', 55000, 75),
('P006', 'Pupuk NPK 25kg', 'Pupuk', 120000, 40),
('P007', 'Insektisida Nabati 500ml', 'Pestisida', 65000, 60),
('P008', 'Bibit Cabai Rawit', 'Bibit', 25000, 150),
('P009', 'Kedelai Organik 5kg', 'Pangan', 60000, 80),
('P010', 'Pupuk Kompos 50kg', 'Pupuk', 75000, 90);

-- ================================================
-- SEED DATA - Sample Transactions
-- ================================================
INSERT INTO transactions (id, transaction_date, cashier_id, payment_method, total_amount, status) VALUES
('TRX20260125001', '2026-01-25 10:30:00', 2, 'CASH', 225000, 'COMPLETED'),
('TRX20260125002', '2026-01-25 14:15:00', 2, 'EWALLET', 340000, 'COMPLETED');

INSERT INTO transaction_items (transaction_id, product_code, product_name, quantity, price_at_sale, subtotal) VALUES
('TRX20260125001', 'P001', 'Beras Premium 5kg', 2, 75000, 150000),
('TRX20260125001', 'P001', 'Beras Premium 5kg', 1, 75000, 75000),
('TRX20260125002', 'P002', 'Pupuk Urea 50kg', 2, 150000, 300000),
('TRX20260125002', 'P007', 'Insektisida Nabati 500ml', 1, 65000, 65000);

-- ================================================
-- VIEWS untuk Reporting
-- ================================================

-- View untuk laporan harian
CREATE OR REPLACE VIEW daily_sales_summary AS
SELECT 
    DATE(transaction_date) as sales_date,
    COUNT(*) as total_transactions,
    SUM(total_amount) as total_revenue,
    payment_method,
    u.name as cashier_name
FROM transactions t
LEFT JOIN users u ON t.cashier_id = u.id
WHERE t.status = 'COMPLETED'
GROUP BY DATE(transaction_date), payment_method, u.name
ORDER BY sales_date DESC;

-- View untuk produk terlaris
CREATE OR REPLACE VIEW top_selling_products AS
SELECT 
    p.code,
    p.name,
    p.category,
    SUM(ti.quantity) as total_sold,
    SUM(ti.subtotal) as total_revenue
FROM transaction_items ti
JOIN products p ON ti.product_code = p.code
JOIN transactions t ON ti.transaction_id = t.id
WHERE t.status = 'COMPLETED'
GROUP BY p.code, p.name, p.category
ORDER BY total_sold DESC;

-- ================================================
-- FUNCTION untuk generate ID transaksi
-- ================================================
CREATE OR REPLACE FUNCTION generate_transaction_id()
RETURNS VARCHAR(50) AS $$
DECLARE
    new_id VARCHAR(50);
    date_part VARCHAR(8);
    seq_part VARCHAR(3);
BEGIN
    date_part := TO_CHAR(CURRENT_DATE, 'YYYYMMDD');
    
    SELECT LPAD(CAST(COUNT(*) + 1 AS VARCHAR), 3, '0')
    INTO seq_part
    FROM transactions
    WHERE DATE(transaction_date) = CURRENT_DATE;
    
    new_id := 'TRX' || date_part || seq_part;
    
    RETURN new_id;
END;
$$ LANGUAGE plpgsql;

-- ================================================
-- TRIGGER untuk update timestamp
-- ================================================
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER products_update_timestamp
BEFORE UPDATE ON products
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

-- ================================================
-- Verifikasi Data
-- ================================================
SELECT 'Users:', COUNT(*) FROM users;
SELECT 'Products:', COUNT(*) FROM products;
SELECT 'Transactions:', COUNT(*) FROM transactions;
SELECT 'Transaction Items:', COUNT(*) FROM transaction_items;