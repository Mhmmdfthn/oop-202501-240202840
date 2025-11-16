package main.java.com.upb.agripos;

import main.java.com.upb.agripos.model.pembayaran.*;
import main.java.com.upb.agripos.model.kontrak.*;
import main.java.com.upb.agripos.util.CreditBy;

public class MainAbstraction {

    public static void main(String[] args) {

        Pembayaran cash = new Cash("INV-001", 100000, 150000);
        Pembayaran ewallet = new EWallet("INV-002", 170000, "user@ewallet", "123456");
        Pembayaran transfer = new TransferBank("INV-003", 200000, "188910909", "654321");

        System.out.println(((Receiptable) cash).cetakStruk());
        System.out.println(((Receiptable) ewallet).cetakStruk());
        System.out.println(((Receiptable) transfer).cetakStruk());

        CreditBy.print("240202840", "Muhammad Nuur Fathan");
    }
}
