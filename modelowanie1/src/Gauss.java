import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Gauss {
    public static void main(String[] args) throws Exception {
        try {
            FileReader fr = new FileReader("/Users/dagapasek/Desktop/modelowanie1 2/modelowanie1/src/Mapa_MD_no_terrain_low_res_Gray.txt");
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw1 = new FileWriter("rozmyty.txt");
            BufferedWriter bw1 = new BufferedWriter(fw1);

            applyGaussianBlur(br, bw1);

            br.close();
            fr.close();

            bw1.close();
            fw1.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void applyGaussianBlur(BufferedReader br, BufferedWriter bw) throws IOException {
        String line;
        int[][] kernel = {
                {1, 2, 1},
                {2, 4, 2},
                {1, 2, 1}
        };

        int kernelSum = 32;

        while ((line = br.readLine()) != null) {
            String[] values = line.split("\\s+");
            int[] blurredValues = new int[values.length];

            for (int i = 0; i < values.length; i++) {
                int sum = 0;
                int count = 0;

                for (int j = -1; j <= 1; j++) {
                    for (int k = -1; k <= 1; k++) {
                        if (i + j >= 0 && i + j < values.length) {
                            sum += Integer.parseInt(values[i + j]) * kernel[1 + j][1 + k];
                            count += kernel[1 + j][1 + k];
                        }
                    }
                }

                int blurredValue = sum / kernelSum;
                blurredValues[i] = blurredValue;
            }

            for (int i = 0; i < blurredValues.length; i++) {
                bw.write(blurredValues[i] + " ");
            }
            bw.newLine();
        }
    }
}

