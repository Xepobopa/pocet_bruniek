package com.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Test {
    private List<File> files;
    private File resultsFile;

    public Test() {
        files = new ArrayList<>();

        File dir = new File("test/files");
        // Ensure files are sorted in the correct order
        File[] fileArray = dir.listFiles();
        if (fileArray != null) {
            Arrays.sort(fileArray); // Sort files alphabetically
            files.addAll(Arrays.asList(fileArray));
        }

        // print all files
        for (File file : files) {
            System.out.println(file.getName());
        }

        resultsFile = new File("test/vysledky.txt");
    }

    public void run() {
        System.out.println("Testing started.");
        Scanner scanner;
        try {
            scanner = new Scanner(resultsFile);
        } catch (FileNotFoundException e) {
            System.err.println("Results file not found" + e);
            return;
        }

        int counter = 0;
        int errors = 0;
        for (File file : files) {
            // get data from file
            System.out.println("Testing code with file: " + file.getName());
            AnalyzatorSkenu analizator = new AnalyzatorSkenu(file.getAbsolutePath());
            counter = analizator.getPocetBuniek();

            // compare to results
            if (scanner.hasNextLine()) {
                int expectedResult = Integer.parseInt(scanner.nextLine().split(" ")[1]);

                System.out.println(
                    "Expected result: " + expectedResult + ", Actual result: " + counter
                );
                if (expectedResult == counter) {
                    System.out.println("Test passed for file: " + file.getName() + " ✅");
                } else {
                    System.err.println("Test failed for file: " + file.getName() + " ❌");
                    errors++;
                }
                
                System.out.println("-----------------------------------");
            } else {
                System.err.println("Test failed for file: " + file.getName());
                break;
            }
        }

        if (errors == 0) {
            System.out.println("All " + files.size() + " tests passed ✅");
        } else {
            System.err.println(errors + " tests failed ❌");
        }
    }

    public static void main(String[] args) {
        new Test().run();
    }
}
