import java.io.*;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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


            FileReader fr2 = new FileReader("binaryzacja.txt");
            BufferedReader br2 = new BufferedReader(fr2);

            List<List<Integer>> pixelData = readPixelData(br2);
            BufferedImage image = createImageFromPixelData(pixelData);
            saveImage(image, "binaryzacja.png");

            br2.close();
            fr2.close();

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
                    val = 255;
                } else {
                    val = 0;
                }
                bw.write(val + " ");
            }
            bw.newLine();
        }
    }

    static List<List<Integer>> readPixelData(BufferedReader br) throws IOException {
        List<List<Integer>> pixelData = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null) {
            String[] values = line.split("\\s+");
            List<Integer> row = new ArrayList<>();
            for (String pixelValue : values) {
                int val = Integer.parseInt(pixelValue);
                row.add(val);
            }
            pixelData.add(row);
        }

        return pixelData;
    }

    static BufferedImage createImageFromPixelData(List<List<Integer>> pixelData) {
        int width = pixelData.get(0).size();
        int height = pixelData.size();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int val = pixelData.get(y).get(x);
                image.setRGB(x, y, val << 16 | val << 8 | val);
            }
        }

        return image;
    }

    static void saveImage(BufferedImage image, String filePath) {
        try {
            File output = new File(filePath);
            ImageIO.write(image, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



