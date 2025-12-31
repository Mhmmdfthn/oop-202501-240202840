# Laporan Praktikum Minggu 9
Topik:  **Exception Handling, Custom Exception, dan Penerapan Design Pattern**

## Identitas
- Nama  : [Muhammad Nuur Fathan]
- NIM   : [240202840]
- Kelas : [3IKRA]
---

## Tujuan
1. Mahasiswa mampu membedakan antara Error dan Exception dalam Java.
2. Mahasiswa dapat mengimplementasikan blok `try–catch–finally` untuk menangani kesalahan program.
3. Mahasiswa mampu membuat dan menggunakan **Custom Exception** untuk validasi spesifik pada aplikasi Agri-POS.
4. Mahasiswa memahami integrasi penanganan kesalahan dalam logika bisnis keranjang belanja.


---

## Dasar Teori
1. **Error vs Exception:** Error adalah masalah fatal yang tidak bisa ditangani program (seperti `OutOfMemoryError`), sedangkan Exception adalah kondisi tidak normal yang masih bisa diantisipasi dan ditangani.
2. **Try-Catch-Finally:** Struktur untuk mencoba kode (`try`), menangkap kesalahan (`catch`), dan menjalankan kode pembersihan yang selalu dieksekusi (`finally`).
3. **Custom Exception:** Class exception yang dibuat sendiri dengan mewarisi (extends) class `Exception` untuk memberikan pesan kesalahan yang lebih spesifik bagi domain bisnis.
4. **Throwing Exception:** Menggunakan kata kunci `throw` untuk melempar exception secara eksplisit ketika validasi gagal.

---

## Langkah Praktikum
1. **Setup Package:** Membuat package `com.upb.agripos`.
2. **Pembuatan Custom Exception:** Membuat tiga class exception: `InvalidQuantityException`, `ProductNotFoundException`, dan `InsufficientStockException`.
3. **Pembuatan Model:** Membuat class `Product` untuk menyimpan data barang dan stok.
4. **Logika Bisnis:** Mengimplementasikan class `ShoppingCart` yang memiliki metode dengan validasi `throws`.
5. **Demo Program:** Membuat class `MainExceptionDemo` untuk menguji berbagai skenario kesalahan menggunakan blok `try-catch`.
6. **Commit:** Melakukan commit dengan pesan `week9-exception: [fitur] [deskripsi]`.

---

## Kode Program
(Tuliskan kode utama yang dibuat, contoh:  

```java
// Contoh
Produk p1 = new Produk("BNH-001", "Benih Padi", 25000, 100);
System.out.println(p1.getNama());
```
)
---

## Hasil Eksekusi
(Sertakan screenshot hasil eksekusi program.  
![Screenshot hasil](screenshots/hasil.png)
)
---

## Analisis
* **Alur Program:** Program berjalan dengan mencoba memasukkan data. Saat program menemui perintah `throw`, alur normal dihentikan dan langsung melompat ke blok `catch` yang sesuai.
* **Pendekatan:** Berbeda dengan minggu sebelumnya yang mungkin menggunakan `if-else` sederhana untuk mencetak error, minggu ini kita menggunakan mekanisme Exception yang memungkinkan pemisahan antara logika bisnis dan logika penanganan kesalahan.
* **Kendala:** Memastikan semua exception yang bertipe *checked exception* sudah dideklarasikan dengan kata kunci `throws` pada signature method. Solusinya adalah selalu menambahkan `throws` atau membungkusnya dengan `try-catch`.

---

## Kesimpulan
Dengan menerapkan *Exception Handling*, aplikasi Agri-POS menjadi lebih robust (tangguh). Program tidak langsung *crash* saat terjadi kesalahan input, melainkan memberikan pesan informatif yang dapat dimengerti oleh pengguna.

---

## Quiz
1. **Jelaskan perbedaan error dan exception.**
* **Jawaban:** Error bersifat fatal dan berhubungan dengan lingkungan runtime (JVM), biasanya tidak disarankan untuk ditangkap. Exception berhubungan dengan logika program dan sangat disarankan untuk ditangani agar program tetap berjalan.


2. **Apa fungsi finally dalam blok try–catch–finally?**
* **Jawaban:** `finally` digunakan untuk mengeksekusi kode yang harus tetap berjalan baik terjadi exception maupun tidak, contohnya seperti menutup koneksi database atau membersihkan cache.


3. **Mengapa custom exception diperlukan?**
* **Jawaban:** Agar pesan kesalahan lebih spesifik terhadap kasus bisnis tertentu (misal: "Stok Kurang") daripada hanya menggunakan exception umum seperti `Exception` atau `RuntimeException`.


4. **Berikan contoh kasus bisnis dalam POS yang membutuhkan custom exception.**
* **Jawaban:** Validasi limit hutang pelanggan, validasi diskon yang sudah kadaluwarsa, atau validasi hak akses kasir saat mencoba melakukan void transaksi.
