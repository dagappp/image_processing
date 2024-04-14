import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Sciemnianie {
    public static void main(String[] args) throws Exception {
        try {
            FileReader fr = new FileReader("/Users/dagapasek/Desktop/Semestr 4/PAU /projekt3/src/QuadTree/out/production/modelowanie1/src/Mapa_MD_no_terrain_low_res_Gray.txt");
            BufferedReader br = new BufferedReader(fr);

            Scanner sc = new Scanner(System.in);
            //System.out.println("Podaj wartość w % o jaką zmieni się składowa piksela podczas ściemniania - od 1 do 99");
            int b;
            do {
                System.out.println("Podaj wartość w % o jaką zmieni się składowa piksela podczas ściemniania - od 1 do 99:");
                b = sc.nextInt();

                if (b < 1 || b > 99) {
                    System.out.println("Wprowadź liczbę z zakresu od 1 do 99.");
                }
            } while (b < 1 || b > 99);

            FileWriter fw1 = new FileWriter("sciemniony.txt");
            BufferedWriter bw1 = new BufferedWriter(fw1);

            sciemnianie(br, bw1, b);

            br.close();
            fr.close();

            bw1.close();
            fw1.close();

            sc.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void sciemnianie(BufferedReader br, BufferedWriter bw, int b) throws IOException {
        String line;
        int imax = 255;
        while ((line = br.readLine()) != null) {
            String[] values = line.split("\\s+");
            for (String x : values) {
                int val = (Integer.parseInt(x));
                val -= (int) (val * b/100);

                if (val > imax) {
                    val = imax;
                } else if (val < 0) {
                    val = 0;
                }
                bw.write(val + " ");
            }
            bw.newLine();
        }
    }
}


