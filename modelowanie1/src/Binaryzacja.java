import java.io.*;
import java.util.Scanner;

public class Binaryzacja {
    public static void main(String[] args) throws Exception {
        try {
            Scanner sc = new Scanner(System.in);
            int prog = 50;

            FileWriter fw1 = new FileWriter("binaryzacja.txt");
            BufferedWriter bw1 = new BufferedWriter(fw1);

            while (true) {
                FileReader fr = new FileReader("/Users/dagapasek/Desktop/Semestr 4/PAU /projekt3/src/QuadTree/out/production/modelowanie1/src/Mapa_MD_no_terrain_low_res_Gray.txt");
                BufferedReader br = new BufferedReader(fr);

                System.out.println("Próg: " + prog);
                binaryzacja(br, bw1, prog);
                br.close();

                System.out.println("Czy zmienić próg? Wpisz tak lub nie.");
                String odp = sc.next().toLowerCase();

                if (odp.equals("tak")) {
                    System.out.println("Podaj nowy próg od 0 do 100:");
                    prog = sc.nextInt();
                } else if (odp.equals("nie")) {
                    break;
                } else {
                    System.out.println("Błąd. Wpisz tak lub nie.");
                }
            }

            bw1.close();
            fw1.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void binaryzacja(BufferedReader br, BufferedWriter bw, int prog) throws IOException {
        String line;
        int imax = 255;
        while ((line = br.readLine()) != null) {
            String[] values = line.split("\\s+");
            for (String x : values) {
                int val = (Integer.parseInt(x));
                if (val > (prog * 255/100)) {
                    val = 1;
                } else {
                    val = 0;
                }
                bw.write(val + " ");
            }
            bw.newLine();
        }
    }
}

