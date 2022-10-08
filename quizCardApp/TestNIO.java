package OOP.c16_fileIO.NIOApproach;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;

public class TestNIO {
  public static void main(String[] args) {
    Path myPath = Paths.get("./OOP", "c16", "NIOApproach", "SavedFileQuizCard", "test.txt");

    try {
      BufferedWriter bufferedWriter = Files.newBufferedWriter(myPath);

      bufferedWriter.write("Dong");

      bufferedWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
