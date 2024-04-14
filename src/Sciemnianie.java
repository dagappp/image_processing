import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sciemnianie {
    public static void main(String[] args) throws Exception {
        try {
            FileReader fr = new FileReader("/Users/dagapasek/Desktop/modelowanie1 2/src/Mapa_MD_no_terrain_low_res_Gray.txt");
            BufferedReader br = new BufferedReader(fr);

            Scanner sc = new Scanner(System.in);
            int b;
            do {
                System.out.println("Podaj wartość w % o jaką zmieni się składowa piksela podczas ściemniania - od 1 do 99:");
                b = sc.nextInt();

                if (b < 1 || b > 99) {
                    System.out.println("Wprowadź liczbę z zakresu od 1 do 99.");
                }
            } while (b < 1 || b > 99);

            List<List<Integer>> pixelData = readPixelData(br);

            BufferedImage image = createImageFromPixelData(pixelData, b);

            File output = new File("sciemniony.png");
            ImageIO.write(image, "png", output);

            br.close();
            fr.close();

            sc.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
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

    static BufferedImage createImageFromPixelData(List<List<Integer>> pixelData, int b) {
        int width = pixelData.get(0).size();
        int height = pixelData.size();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int val = pixelData.get(y).get(x);
                val -= (int) (val * b / 100);
                if (val > 255) {
                    val = 255;
                } else if (val < 0) {
                    val = 0;
                }
                image.setRGB(x, y, val << 16 | val << 8 | val);
            }
        }

        return image;
    }
}





