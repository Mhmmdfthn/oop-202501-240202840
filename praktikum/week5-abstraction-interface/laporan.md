# Laporan Praktikum Minggu 5
Topik: Abstraction (Abstract Class & Interface)

## Identitas
- Nama  : Muhammad Nuur Fathan
- NIM   : 240202840
- Kelas : 3IKRA

---

## Tujuan
* Memahami konsep **abstraksi** pada OOP.
* Membedakan penggunaan **abstract class** dan **interface**.
* Mendesain class `Pembayaran` sebagai abstract class dengan method abstrak.
* Mengimplementasikan class konkrit: `Cash`, `EWallet`, dan `TransferBank`.
* Menerapkan **multiple inheritance via interface** (`Validatable`, `Receiptable`).
* Menjalankan program dan menghasilkan output berupa **struk pembayaran**.

---

## Dasar Teori
1. **Abstraksi** menyembunyikan detail implementasi dan menampilkan fitur penting dari sebuah objek.
2. **Abstract class** dapat memiliki method abstrak dan konkrit, serta menyimpan state (field).
3. **Interface** berisi kontrak perilaku yang harus diimplementasikan class; mendukung multiple inheritance.
4. **Polimorfisme** memungkinkan objek dari subclass diperlakukan sebagai objek superclass-nya.
5. Java menggunakan interface untuk menghindari masalah **diamond problem** pada multiple inheritance.

---

## Langkah Praktikum
1. Membuat **abstract class** `Pembayaran` berisi field dasar (`invoiceNo`, `total`) beserta method abstrak `biaya()` dan `prosesPembayaran()`.
2. Membuat dua interface:

   * `Validatable` → memiliki method `validasi()`
   * `Receiptable` → memiliki method `cetakStruk()`
3. Mengimplementasikan pembayaran:

   * `Cash` → tanpa fee.
   * `EWallet` → fee 1.5%, membutuhkan validasi OTP.
   * `TransferBank` → biaya tetap Rp3.500 dan membutuhkan validasi.
4. Membuat class `MainAbstraction` untuk menjalankan seluruh metode pembayaran secara polimorfik.
5. Menampilkan struk hasil pembayaran menggunakan interface `Receiptable`.
6. Mencetak identitas melalui `CreditBy.print()`.
---

## Kode Program
### **1. Receiptable.java**

```java
package main.java.com.upb.agripos.model.kontrak;

public interface Receiptable {
    String cetakStruk();
}
```

### **2. Validatable.java**

```java
package main.java.com.upb.agripos.model.kontrak;

public interface Validatable {
    boolean validasi(); 
}
```

### **Pembayaran.java**

```java
package main.java.com.upb.agripos.model.pembayaran;

public abstract class Pembayaran {
    protected String invoiceNo;
    protected double total;

    public Pembayaran(String invoiceNo, double total) {
        this.invoiceNo = invoiceNo;
        this.total = total;
    }

    public abstract double biaya();
    public abstract boolean prosesPembayaran();

    public double totalBayar() {
        return total + biaya();
    }

    public String getInvoiceNo() { return invoiceNo; }
    public double getTotal() { return total; }
}
```

### **4. Cash.java**

```java
package main.java.com.upb.agripos.model.pembayaran;

import main.java.com.upb.agripos.model.kontrak.Receiptable;

public class Cash extends Pembayaran implements Receiptable {

    private final double tunai;

    public Cash(String invoiceNo, double total, double tunai) {
        super(invoiceNo, total);
        this.tunai = tunai;
    }

    @Override
    public double biaya() {
        return 0.0;
    }

    @Override
    public boolean prosesPembayaran() {
        return tunai >= totalBayar();
    }

    @Override
    public String cetakStruk() {
        return String.format(
            "INVOICE %s | TOTAL: %.2f | BAYAR CASH: %.2f | KEMBALI: %.2f",
            invoiceNo,
            totalBayar(),
            tunai,
            Math.max(0, tunai - totalBayar())
        );
    }
}
```

### **5. EWallet.java**

```java
package main.java.com.upb.agripos.model.pembayaran;

import main.java.com.upb.agripos.model.kontrak.Validatable;
import main.java.com.upb.agripos.model.kontrak.Receiptable;

public class EWallet extends Pembayaran implements Validatable, Receiptable {

    private String akun;
    private String otp;

    public EWallet(String invoiceNo, double total, String akun, String otp) {
        super(invoiceNo, total);
        this.akun = akun;
        this.otp = otp;
    }

    @Override
    public double biaya() {
        return total * 0.015;  
    }

    @Override
    public boolean validasi() {
        return otp != null && otp.length() == 6;
    }

    @Override
    public boolean prosesPembayaran() {
        return validasi();
    }

    @Override
    public String cetakStruk() {
        return String.format(
            "INVOICE %s | TOTAL+FEE: %.2f | E-WALLET: %s | STATUS: %s",
            invoiceNo,
            totalBayar(),
            akun,
            prosesPembayaran() ? "BERHASIL" : "GAGAL"
        );
    }
}
```

### **6. TransferBank.java**

```java
package main.java.com.upb.agripos.model.pembayaran;

import main.java.com.upb.agripos.model.kontrak.Validatable;
import main.java.com.upb.agripos.model.kontrak.Receiptable;

public class TransferBank extends Pembayaran implements Validatable, Receiptable {

    private String norek;
    private String kodeVerifikasi;

    public TransferBank(String invoiceNo, double total, String norek, String kodeVerifikasi) {
        super(invoiceNo, total);
        this.norek = norek;
        this.kodeVerifikasi = kodeVerifikasi;
    }

    @Override
    public double biaya() {
        return 3500; 
    }

    @Override
    public boolean validasi() {
        return kodeVerifikasi != null && kodeVerifikasi.length() == 6;
    }

    @Override
    public boolean prosesPembayaran() {
        return validasi();
    }

    @Override
    public String cetakStruk() {
        return String.format(
            "INVOICE %s | TOTAL+FEE: %.2f | TRANSFER KE: %s | STATUS: %s",
            invoiceNo,
            totalBayar(),
            norek,
            prosesPembayaran() ? "BERHASIL" : "GAGAL"
        );
    }
}
```

### **7. CreditBy.java**

```java
package main.java.com.upb.agripos.util;

public class CreditBy {
   
     public static void print(String nim, String nama) {
        System.out.println("\n=== Credit By ===");
        System.out.println("Nama: " + nama);
        System.out.println("NIM: " + nim);
        System.out.println("=================\n");
    }
}
```

### **8. MainAbstraction.java**

```java
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
```

---

## Hasil Eksekusi
(Sertakan screenshot hasil eksekusi program.  
![Screenshot hasil](screenshots/hasil.png)
)
---

## Analisis
* Program memanfaatkan **abstract class** `Pembayaran` sebagai dasar semua metode pembayaran.
* Setiap subclass mengimplementasikan logika `biaya()` dan `prosesPembayaran()` sesuai karakter metode pembayaran masing-masing.
* Interface `Receiptable` memastikan semua metode pembayaran dapat mencetak struk.
* Interface `Validatable` digunakan untuk metode yang membutuhkan proses verifikasi tambahan, seperti OTP atau kode bank.
* **Multiple inheritance** diterapkan pada `EWallet` dan `TransferBank` karena mereka mengimplementasikan lebih dari satu interface.
* Program berjalan secara **polimorfik** karena semua objek diperlakukan sebagai `Pembayaran`.
---

## Kesimpulan
* Penerapan abstraksi menggunakan abstract class dan interface.
* Cara mendesain class turunan dengan implementasi method spesifik.
* Cara menggunakan interface untuk kontrak perilaku dan mendukung multiple inheritance.
* Polimorfisme mempermudah pemanggilan objek secara seragam.

---

## Quiz
1. **Jelaskan perbedaan konsep dan penggunaan abstract class dan interface.**
   **Jawaban:**
   Abstract class mengizinkan method abstrak dan konkrit serta menyimpan state; digunakan bila ada struktur dasar yang shared.
   Interface hanya berisi kontrak perilaku tanpa implementasi (kecuali default); digunakan untuk kemampuan lintas hierarki.

2. **Mengapa multiple inheritance lebih aman dilakukan dengan interface pada Java?**
   **Jawaban:**
   Karena interface tidak membawa state sehingga tidak menyebabkan conflict seperti diamond problem pada pewarisan class.

3. **Pada contoh Agri-POS, bagian mana yang tepat menjadi abstract class dan interface?**
   **Jawaban:**
   `Pembayaran` → abstract class karena memiliki struktur dasar (invoice, total) dan perilaku umum.
   `Validatable` & `Receiptable` → interface karena hanya mendefinisikan kemampuan tambahan yang dapat diterapkan ke berbagai jenis pembayaran.
