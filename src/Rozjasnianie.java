import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Rozjasnianie {
    public static void main(String[] args) throws Exception {
        Scanner sc1 = new Scanner(System.in);

        int procentZmiany;

        do {
            System.out.println("Podaj wartość w % o jaką zmieni się składowa piksela podczas rozjaśniania od 10 do 20:");
            procentZmiany = sc1.nextInt();

            if (procentZmiany < 10 || procentZmiany > 20) {
                System.out.println("Wprowadź liczbę z zakresu od 10 do 20.");
            }
        } while (procentZmiany < 10 || procentZmiany > 20);

        for (int i = 0; i < 3; i++) {
            String nazwaTxt = "zmieniony" + (i + 1) + ".txt";
            zmiana(nazwaTxt, procentZmiany);

            String nazwaPng = "zmieniony" + (i + 1) + ".png";
            List<List<Integer>> pixelData = readPixelData(new FileReader(nazwaTxt));
            BufferedImage image = createImageFromPixelData(pixelData);
            saveImage(image, nazwaPng);

            procentZmiany += 10;
        }
    }

    static void zmiana(String nazwa, int procentZmiany) throws IOException {
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
                    val += (int) (val * procentZmiany / 100);
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

    static List<List<Integer>> readPixelData(FileReader fileReader) throws IOException {
        List<List<Integer>> pixelData = new ArrayList<>();
        BufferedReader br = new BufferedReader(fileReader);
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

        br.close();
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





