package com.upb.agripos.model;

public class Receipt {
    private Transaction t;
    public Receipt(Transaction t) { this.t = t; }
    
    public String formatReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== STRUK BELANJA ===\n");
        sb.append("ID: ").append(t.getId()).append("\n");
        sb.append("Tgl: ").append(t.getTransactionDate()).append("\n");
        sb.append("Kasir: ").append(t.getCashier().getName()).append("\n");
        sb.append("---------------------\n");
        for(TransactionItem i : t.getItems()) {
            sb.append(i.getProductName()).append(" x").append(i.getQuantity())
              .append(" = ").append(i.getSubtotal()).append("\n");
        }
        sb.append("---------------------\n");
        sb.append("TOTAL: ").append(t.getTotalAmount()).append("\n");
        sb.append("Metode: ").append(t.getPaymentMethod().getMethodName()).append("\n");
        return sb.toString();
    }
}