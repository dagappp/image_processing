import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Gauss {
    public static void main(String[] args) throws Exception {
        try {
            FileReader fr = new FileReader("/Users/dagapasek/Desktop/modelowanie1 2/src/Mapa_MD_no_terrain_low_res_Gray.txt");
            BufferedReader br = new BufferedReader(fr);

            BufferedImage image = readImageFromTextFile(br);

            saveImageToTextFile(image, "obraz.txt");

            gauss(image);

            saveImageAsJpg(image, "gauss.jpg");

            fr.close();
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void gauss(BufferedImage image) {
        int[][] kernel = {
                {1, 2, 1},
                {2, 4, 2},
                {1, 2, 1}
        };
        int kernelSum = 16;

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int sum = 0;

                for (int j = -1; j <= 1; j++) {
                    for (int k = -1; k <= 1; k++) {
                        int newX = x + k;
                        int newY = y + j;

                        if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                            int val = image.getRGB(newX, newY) & 0xFF;
                            sum += val * kernel[1 + j][1 + k];
                        }
                    }
                }

                int blurredValue = sum / kernelSum;
                int newRGB = (blurredValue << 16) | (blurredValue << 8) | blurredValue;
                resultImage.setRGB(x, y, newRGB);
            }
        }

        image.getGraphics().drawImage(resultImage, 0, 0, null);
    } static void Gauss(BufferedImage image) {
        int[][] kernel = {
                {1, 2, 1},
                {2, 4, 2},
                {1, 2, 1}
        };
        int kernelSum = 16;

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int sum = 0;

                for (int j = -1; j <= 1; j++) {
                    for (int k = -1; k <= 1; k++) {
                        int newX = x + k;
                        int newY = y + j;

                        if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                            int val = image.getRGB(newX, newY) & 0xFF;
                            sum += val * kernel[1 + j][1 + k];
                        }
                    }
                }

                int blurredValue = sum / kernelSum;
                int newRGB = (blurredValue << 16) | (blurredValue << 8) | blurredValue;
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
        br = new BufferedReader(new FileReader("/Users/dagapasek/Desktop/modelowanie1 2/src/Mapa_MD_no_terrain_low_res_Gray.txt"));

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




