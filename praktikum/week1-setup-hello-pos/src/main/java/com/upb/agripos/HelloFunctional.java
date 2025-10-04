import java.util.function.BiConsumer;
public class HelloFunctional {
 public static void main(String[] args) { BiConsumer<String,Integer> sapa =
 (nama, nim) -> System.out.println("Hello World , Aku " + nama + " NIM " + nim);
 sapa.accept("Muhammad Nuur Fathan", 240202840);
 }
}