public class HelloOOP {
    static class Mahasiswa {
        String nama;
        String NIM;

        Mahasiswa(String n, String u) { 
            nama = n;
            NIM = u;
        }

        void sapa() {
            System.out.println("Hello World, " + nama + " NIM " + NIM);
        }
    }

    public static void main(String[] args) {
        Mahasiswa m = new Mahasiswa("Muhammad Nuur Fathan", "240202840");
        m.sapa();
    }
}
