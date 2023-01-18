package com.geektrust.family.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Helper {
  public static final int DEFAULT_BUFFER_SIZE = 8192;

  /**
   * convert InputStream to File
   * @param inputStream any input stream from reading content
   * @param file as output
   * @throws IOException
   */
  public static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
    // append = false
    try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
      int read;
      byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
      while ((read = inputStream.read(bytes)) != -1) {
        outputStream.write(bytes, 0, read);
      }
    }
  }
}
