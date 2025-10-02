import java.util.function.BiConsumer;
public class HelloFunctional {
 public static void main(String[] args) { BiConsumer<String,Integer> sapa =
 (nama, nim) -> System.out.println("Halo, " + nama + " nim " + nim);
 sapa.accept("Muhammad Nuur Fathan", 240202840);
 }
}