# Laporan Praktikum Minggu 1 
Topik: [WEEK 1 – Setup Hello POS]

## Identitas
- Nama  : [Muhamamd Nuur Fathan]
- NIM   : [240202840]
- Kelas : [3IKRA]

---

## Tujuan
1. Mahasiswa memahami konsep dasar 3 paradigma pemrograman: Prosedural, OOP, dan Fungsional.
2. Mahasiswa mampu membuat program sederhana untuk menampilkan output ke terminal menggunakan ketiga paradigma.
3. Mahasiswa memahami perbedaan pendekatan tiap paradigma dalam menyelesaikan masalah sederhana.
4. Mahasiswa mampu melakukan setup project Java awal untuk studi kasus Agri-POS.
---

## Dasar Teori
A. Prosedural adalah paradigma pemrograman yang menjalankan instruksi langkah demi langkah melalui fungsi utama.

B. OOP (Object-Oriented Programming) berorientasi pada object yang memiliki atribut (data) dan method (perilaku).

C. Fungsional menekankan penggunaan fungsi murni, lambda expression, dan immutability untuk menulis kode yang ringkas.

D. Class & Object: class adalah cetak biru, object adalah hasil nyata dari instansiasi class.

E. Enkapsulasi dan modularisasi dalam OOP membuat kode lebih terstruktur, maintainable, dan scalable.

---

## Langkah Praktikum
1. Menyiapkan lingkungan project Java untuk praktikum.
2. Membuat program sederhana menggunakan paradigma **prosedural**, **fungsional**, dan **OOP**.
3. Melakukan kompilasi dan menjalankan program untuk menampilkan output di terminal.
4. Membandingkan hasil eksekusi dari tiap paradigma.
5. Melakukan commit dengan pesan sesuai tahapan:

   * `Initial commit - setup Hello POS (procedural)`
   * `Add HelloFunctional.java (functional)`
   * `Add HelloOOP.java (object oriented)`

---

## Kode Program
### 1. Procedural

```java
public class HelloProcedural {
   public static void main(String[] args) {
      String nim = "240202840";
      String nama = "Muhammad Nuur Fathan";
      System.out.println("Hello world " + nama + ", nim: " + nim);
   }
}
```

### 2. Functional

```java
import java.util.function.BiConsumer;

public class HelloFunctional {
    public static void main(String[] args) {
        BiConsumer<String, Integer> sapa =
            (nama, nim) -> System.out.println("Hello World, " + nama + " nim " + nim);

        sapa.accept("Muhammad Nuur Fathan", 240202840);
    }
}
```

### 3. OOP

```java
public class HelloOOP {
    static class Mahasiswa {
        String nama;
        String umur;

        Mahasiswa(String n, String u) { 
            nama = n;
            umur = u;
        }

        void sapa() {
            System.out.println("Halo, " + nama + " umur " + umur);
        }
    }

    public static void main(String[] args) {
        Mahasiswa m = new Mahasiswa("Muhammad Nuur Fathan", "20");
        m.sapa();
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
Dari ketiga implementasi di atas terlihat perbedaan paradigma:

* **Procedural**: semua logika langsung ditulis di fungsi `main`. Sederhana, cepat dibuat, tetapi sulit diorganisir untuk program besar.
* **Functional**: menggunakan `BiConsumer` dengan lambda expression. Lebih ringkas, mengurangi boilerplate code, dan mudah digunakan dalam konteks data processing atau parallel computing. Namun, masih kurang intuitif jika dipakai untuk sistem besar yang punya banyak entitas.
* **OOP**: membagi program menjadi **class** dan **object**. Contoh class `Mahasiswa` menyimpan data dan method `sapa()`. Pendekatan ini sangat cocok untuk sistem skala besar seperti POS (Point of Sales), karena setiap entitas (Produk, Transaksi, Pelanggan) bisa dimodelkan sebagai object dengan atribut dan perilaku masing-masing.

---

## Kesimpulan
* Praktikum minggu ini berhasil memperlihatkan perbedaan **tiga paradigma pemrograman** dalam Java.
* **Prosedural** sederhana tapi sulit berkembang.
* **Functional** menawarkan cara ekspresif dan ringkas, cocok untuk data processing.
* **OOP** adalah pendekatan yang paling sesuai untuk sistem kompleks karena mendukung modularisasi, enkapsulasi, serta memungkinkan pengembangan berkelanjutan.
* Dengan memahami ketiga paradigma ini, mahasiswa dapat lebih fleksibel dalam memilih pendekatan yang sesuai dengan kebutuhan sistem yang akan dibangun.

---

## Quiz
1. **Apakah OOP selalu lebih baik dari prosedural?**
   Tidak selalu, OOP unggul untuk program kompleks, tapi prosedural cukup untuk aplikasi kecil.

2. **Kapan functional programming lebih cocok digunakan dibanding OOP atau prosedural?**
   Saat mengolah data besar, operasi matematis, atau sistem paralel/asynchronous.

3. **Bagaimana paradigma memengaruhi maintainability dan scalability aplikasi?**

   * Prosedural → sulit diperluas.
   * OOP → mudah di-maintain dan scalable.
   * Functional → ringkas dan minim bug.

4. **Mengapa OOP lebih cocok untuk aplikasi POS dibanding prosedural?**
   Karena POS punya banyak entitas (produk, transaksi, pelanggan) yang mudah dimodelkan sebagai object.

5. **Bagaimana paradigma fungsional membantu mengurangi boilerplate code?**
   Dengan memanfaatkan lambda express
