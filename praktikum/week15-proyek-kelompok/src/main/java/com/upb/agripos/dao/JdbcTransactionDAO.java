package com.upb.agripos.dao;

import com.upb.agripos.model.*;
import com.upb.agripos.util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class JdbcTransactionDAO implements TransactionDAO {

    // Helper untuk koneksi
    private Connection getConnection() { 
        return DatabaseConnection.getInstance().getConnection(); 
    }

    // 1. SAVE (Sudah ada, tapi pastikan sama)
    @Override
    public void save(Transaction trx) throws Exception {
        Connection conn = getConnection();
        try {
            conn.setAutoCommit(false); // TRANSACTION START
            
            // Insert Header
            String sqlHead = "INSERT INTO transactions (id, cashier_id, payment_method, total_amount, status, transaction_date) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlHead)) {
                ps.setString(1, trx.getId());
                ps.setInt(2, trx.getCashier().getId());
                ps.setString(3, trx.getPaymentMethod().getMethodName());
                ps.setBigDecimal(4, trx.getTotalAmount());
                ps.setString(5, "COMPLETED");
                ps.setTimestamp(6, Timestamp.valueOf(trx.getTransactionDate()));
                ps.executeUpdate();
            }

            // Insert Items
            String sqlItem = "INSERT INTO transaction_items (transaction_id, product_code, product_name, quantity, price_at_sale, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlItem)) {
                for (TransactionItem item : trx.getItems()) {
                    ps.setString(1, trx.getId());
                    ps.setString(2, item.getProductCode());
                    ps.setString(3, item.getProductName());
                    ps.setInt(4, item.getQuantity());
                    ps.setBigDecimal(5, item.getPriceAtSale());
                    ps.setBigDecimal(6, item.getSubtotal());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            conn.commit(); // COMMIT
        } catch (Exception e) {
            conn.rollback(); // ROLLBACK
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // 2. FIND BY DATE (Untuk Laporan Harian)
    @Override
    public List<Transaction> findByDate(LocalDate date) throws Exception {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE DATE(transaction_date) = ?";
        
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToTransaction(rs));
                }
            }
        }
        return list;
    }

    // 3. FIND BY PERIOD (METHOD YANG HILANG - ERROR 1)
    @Override
    public List<Transaction> findByPeriod(LocalDate start, LocalDate end) throws Exception {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE DATE(transaction_date) BETWEEN ? AND ?";
        
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(start));
            ps.setDate(2, Date.valueOf(end));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToTransaction(rs));
                }
            }
        }
        return list;
    }

    // 4. FIND BY ID (METHOD YANG HILANG - ERROR 2)
    @Override
    public Transaction findById(String id) throws Exception {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Transaction t = mapRowToTransaction(rs);
                    // Load items detail jika perlu (opsional untuk struk)
                    t.setItems(getItems(t.getId())); 
                    return t;
                }
            }
        }
        return null;
    }

    // --- HELPER METHODS ---

    // Helper untuk mapping ResultSet ke Object Transaction
    private Transaction mapRowToTransaction(ResultSet rs) throws SQLException {
        Transaction t = new Transaction();
        t.setId(rs.getString("id"));
        t.setTotalAmount(rs.getBigDecimal("total_amount"));
        t.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());
        t.setStatus(rs.getString("status"));
        
        // Mock User & Payment (karena di DB cuma simpan ID/String)
        User dummyUser = new User();
        dummyUser.setId(rs.getInt("cashier_id"));
        dummyUser.setName("Kasir " + rs.getInt("cashier_id")); // Atau bisa JOIN table users
        t.setCashier(dummyUser);
        
        // Mock Payment Method
        String method = rs.getString("payment_method");
        if ("CASH".equals(method)) {
            t.setPaymentMethod(new CashPayment(BigDecimal.ZERO));
        } else {
            t.setPaymentMethod(new EWalletPayment(method, ""));
        }
        
        return t;
    }

    // Helper untuk ambil item detail
    private List<TransactionItem> getItems(String trxId) throws SQLException {
        List<TransactionItem> items = new ArrayList<>();
        String sql = "SELECT * FROM transaction_items WHERE transaction_id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, trxId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TransactionItem i = new TransactionItem();
                    i.setProductCode(rs.getString("product_code"));
                    i.setProductName(rs.getString("product_name"));
                    i.setQuantity(rs.getInt("quantity"));
                    i.setPriceAtSale(rs.getBigDecimal("price_at_sale"));
                    i.setSubtotal(rs.getBigDecimal("subtotal"));
                    items.add(i);
                }
            }
        }
        return items;
    }
}