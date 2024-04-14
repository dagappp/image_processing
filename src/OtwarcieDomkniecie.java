import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.*;
import java.util.Scanner;

public class OtwarcieDomkniecie {
    public static void main(String[] args) throws Exception {
        try {
            FileReader fr = new FileReader("/Users/dagapasek/Desktop/modelowanie1 2/src/Mapa_MD_no_terrain_low_res_Gray.txt");
            BufferedReader br = new BufferedReader(fr);

            BufferedImage image = readImageFromTextFile(br);

            saveImageToTextFile(image, "obraz1.txt");


            Scanner scanner = new Scanner(System.in);
            System.out.print("Podaj promień sąsiedztwa (krańcówki): ");
            int radius = scanner.nextInt();

            applyOpening(image, radius);

            saveImageAsJpg(image, "otwarcie2.jpg");
            applyClosing(image, radius);

            saveImageAsJpg(image, "domkniecie2.jpg");

            scanner.close();

            fr.close();
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void applyOpening(BufferedImage image, int radius) {
        // Otwarcie = Erozja(Dylatacja(obraz))
        applyErosion(image, radius);
        applyDilation(image, radius);
    }

    static void applyClosing(BufferedImage image, int radius) {
        // Domknięcie = Dylatacja(Erozja(obraz))
        applyDilation(image, radius);
        applyErosion(image, radius);
    }

    static void applyDilation(BufferedImage image, int radius) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = radius; y < height - radius; y++) {
            for (int x = radius; x < width - radius; x++) {
                int maxVal = 0;

                // Sąsiedztwo Moore'a o promieniu 'radius'
                for (int j = -radius; j <= radius; j++) {
                    for (int k = -radius; k <= radius; k++) {
                        int newX = x + k;
                        int newY = y + j;
                        int val = image.getRGB(newX, newY) & 0xFF;
                        maxVal = Math.max(maxVal, val);
                    }
                }

                int newRGB = (maxVal << 16) | (maxVal << 8) | maxVal;
                resultImage.setRGB(x, y, newRGB);
            }
        }

        // Skopiuj wynik dylatacji do obrazu źródłowego
        image.getGraphics().drawImage(resultImage, 0, 0, null);
    }

    static void applyErosion(BufferedImage image, int radius) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = radius; y < height - radius; y++) {
            for (int x = radius; x < width - radius; x++) {
                int minVal = 255;

                for (int j = -radius; j <= radius; j++) {
                    for (int k = -radius; k <= radius; k++) {
                        int newX = x + k;
                        int newY = y + j;
                        int val = image.getRGB(newX, newY) & 0xFF;
                        minVal = Math.min(minVal, val);
                    }
                }

                int newRGB = (minVal << 16) | (minVal << 8) | minVal;
                resultImage.setRGB(x, y, newRGB);
            }
        }

        image.getGraphics().drawImage(resultImage, 0, 0, null);
    }

    static BufferedImage readImageFromTextFile(BufferedReader br) throws IOException {
        String line;
        int width = 0;
        int height = 0;

        while ((line = br.readLine()) != null) {
            String[] values = line.split("\\s+");
            width = values.length;
            height++;
        }

        br.close();
        br = new BufferedReader(new FileReader("obraz1.txt"));

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int y = 0;
        while ((line = br.readLine()) != null) {
            String[] values = line.split("\\s+");
            for (int x = 0; x < values.length; x++) {
                int val = Integer.parseInt(values[x]);
                int rgb = (val << 16) | (val << 8) | val;
                image.setRGB(x, y, rgb);
            }
            y++;
        }

        return image;
    }

    static void saveImageToTextFile(BufferedImage image, String outputPath) throws IOException {
        int width = image.getWidth();
        int height = image.getHeight();

        FileWriter fw = new FileWriter(outputPath);
        BufferedWriter bw = new BufferedWriter(fw);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y) & 0xFF;
                bw.write(rgb + " ");
            }
            bw.newLine();
        }

        bw.close();
        fw.close();
    }

    static void saveImageAsJpg(BufferedImage image, String outputPath) throws IOException {
        File output = new File(outputPath);
        ImageIO.write(image, "jpg", output);
    }
}



