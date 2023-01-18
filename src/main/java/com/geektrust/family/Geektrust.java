package com.geektrust.family;

import com.geektrust.family.service.Family;
import com.geektrust.family.service.FileProcessor;
import com.geektrust.family.util.Helper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Geektrust {

	public static void main(String... args) {

		try {
			switch (args.length) {
				case 0: {
					System.out.println(">> Please provide program arguments <<");
					break;
				}
				case 1: {		//Init file not provided. Hence, use our hardcoded init file.
					processInputFileOnly(args[0]);
					break;
				}
				case 2: {		//Init and input file provided.
					processInitAndInputFile(args[0], args[1]);
					break;
				}
				default: {
					System.out.println(">> Invalid program arguments <<");
					break;
				}
			}
		} catch (ArrayIndexOutOfBoundsException | IOException e) {
			System.out.println("Please enter valid inputs | file");
		}

	}

	private static void processInputFileOnly(String inputFilePath) throws IOException {
		Geektrust sol = new Geektrust();
		Family family = new Family();

		InputStream resource = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("setup/init.txt");
		if (resource == null) {
			throw new IllegalArgumentException("Hardcoded Init File not found!");
		}
		File file = new File("tmp.txt");
		Helper.copyInputStreamToFile(resource, file);
		sol.fileToProcess(family, file.getPath(), false);    // Init file
		sol.fileToProcess(family, inputFilePath, true);    // Input file
	}

	private static void processInitAndInputFile(String initFilePath, String inputFilePath) throws IOException {
		Geektrust sol = new Geektrust();
		Family family = new Family();

		sol.fileToProcess(family, initFilePath, false);  // Init file
		sol.fileToProcess(family, inputFilePath, true);    // Input file
	}

	/**
	 * Read file to process.
	 *
	 * @param family instance
	 * @param filePath	of init / input
	 * @param isInputFile response if invalid file java.io.FileNotFoundException
	 */
	public void fileToProcess(Family family, String filePath, boolean isInputFile) {
		File file = new File(filePath);
		FileProcessor processor = new FileProcessor();
		processor.processInputFile(family, file, isInputFile);
	}


}
