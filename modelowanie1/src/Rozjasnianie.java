import java.io.*;
import java.util.Scanner;

public class Rozjasnianie {
    public static void main(String[] args) throws Exception {
        Scanner sc1 = new Scanner(System.in);

        for (int i = 0; i < 3; i++) {
            int b1;

            do {
                System.out.println("Podaj wartość w % o jaką zmieni się składowa piksela podczas rozjaśniania od 10 do 20):");
                b1 = sc1.nextInt();

                if (b1 < 10 || b1 > 20) {
                    System.out.println("Wprowadź liczbę z zakresu od 10 do 20.");
                }
            } while (b1 < 10 || b1 > 20);

            String nazwa = "zmieniony" + (i + 1) + ".txt";
            zmiana(nazwa, b1);
        }
    }

    static void zmiana(String nazwa, int b1) throws IOException {
        try (FileReader fr1 = new FileReader("/Users/dagapasek/Desktop/Semestr 4/PAU /projekt3/src/QuadTree/out/production/modelowanie1/src/Mapa_MD_no_terrain_low_res_Gray.txt");
             BufferedReader br1 = new BufferedReader(fr1);

             FileWriter fw1 = new FileWriter(nazwa);
             BufferedWriter bw1 = new BufferedWriter(fw1)) {

            String line;
            int imax = 255;
            while ((line = br1.readLine()) != null) {
                String[] values = line.split("\\s+");

                for (String x : values) {
                    int val = Integer.parseInt(x);
                    val += (int) (val * b1 / 100);
                    if (val > imax) {
                        val = imax;
                    } else if (val < 0) {
                        val = 0;
                    }

                    bw1.write(val + " ");
                }
                bw1.newLine();
            }

        }
    }
}


