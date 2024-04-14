import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Operacje_Morfologiczne {
    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("/Users/dagapasek/Desktop/modelowanie1 2/src/Mapa_MD_no_terrain_low_res_Gray.bmp"));

            BufferedImage erodedImage = erozja(image);
            ImageIO.write(erodedImage, "jpg", new File("erozja.jpg"));

            BufferedImage dilatedImage = dylatacja(image);
            ImageIO.write(dilatedImage, "jpg", new File("dylatacja.jpg"));

            BufferedImage openedImage = otwarcie(image);
            ImageIO.write(openedImage, "jpg", new File("otwarcie.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage erozja(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int r = 1;

        for (int y = r; y < height - r; y++) {
            for (int x = r; x < width - r; x++) {
                int min = 255;
                for (int j = -r; j <= r; j++) {
                    for (int i = -r; i <= r; i++) {
                        int rgb = image.getRGB(x + i, y + j);
                        int value = (rgb >> 16) & 0xFF;
                        min = Math.min(min, value);
                    }
                }
                resultImage.setRGB(x, y, (min << 16) | (min << 8) | min);
            }
        }

        return resultImage;
    }

    public static BufferedImage dylatacja(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int r = 1;

        for (int y = r; y < height - r; y++) {
            for (int x = r; x < width - r; x++) {
                int max = 0;
                for (int j = -r; j <= r; j++) {
                    for (int i = -r; i <= r; i++) {
                        int rgb = image.getRGB(x + i, y + j);
                        int value = (rgb >> 16) & 0xFF;
                        max = Math.max(max, value);
                    }
                }
                resultImage.setRGB(x, y, (max << 16) | (max << 8) | max);
            }
        }

        return resultImage;
    }

    public static BufferedImage otwarcie(BufferedImage image) {
        BufferedImage eroded = erozja(image);
        BufferedImage opened = dylatacja(eroded);
        return opened;
    }
}

