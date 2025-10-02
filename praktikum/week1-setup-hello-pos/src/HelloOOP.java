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
        Mahasiswa m = new Mahasiswa("Muhammad Nur Fathan", "20");
        m.sapa();
    }
}
