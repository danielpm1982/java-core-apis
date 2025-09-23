package com.danielpm1982.nio2;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class BigFileGenerator {
    @SuppressWarnings("SameParameterValue")
    protected static boolean generateBigFileAtProjectRootPath(Path filePath, long maxFileSizeInBytes, String baseLineText) {
        if(filePath.toFile().exists()){
            IO.println("File already exists ! File not recreated !");
        } else{
            IO.println("Creating and writing to file...");
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath.toFile().getPath(), false))) {
                long currentTempSize = 0;
                while (currentTempSize < maxFileSizeInBytes) {
                    bufferedWriter.write(baseLineText);
                    currentTempSize += baseLineText.getBytes().length;
                }
                IO.println("File created successfully !");
            } catch (IOException e) {
                throw new RuntimeException("Error while writing file !" + e.getMessage());
            }
        }
        return true;
    }
}

/*
Through the generateBigFileAtProjectRootPath() method, this class uses classical IO API to write sample String lines to a file, at a
given path, until the file reaches the max size. The use of this class, at this project, is basically to create a file big enough to
allow us to do some benchmark tests, comparing the efficiency of all 3 types of IO APIs available at Java 25 (at the other classes).
*/
