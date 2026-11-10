import java.io.*;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {
        String source1 = "file1.txt";
        String source2 = "file2.txt";
        String dest = "destination.txt";

        createTestFile(source1, 10_000_000);
        createTestFile(source2, 10_000_000);

        long startTime = System.nanoTime();

        try (OutputStream out = new FileOutputStream(dest, false)) {
            copyFile(source1, out);
            copyFile(source2, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();
        long durationMs = (endTime - startTime) / 1_000_000;

        System.out.println("Время выполнения: " + durationMs + " мс");
    }

    private static void copyFile(String sourcePath, OutputStream out) throws IOException {
        try (InputStream in = new FileInputStream(sourcePath)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    private static void createTestFile(String path, int size) {
        try (FileWriter writer = new FileWriter(path)) {
            char[] chunk = new char[8192];
            java.util.Arrays.fill(chunk, 'A');
            for (int i = 0; i < size / chunk.length; i++) {
                writer.write(chunk);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}