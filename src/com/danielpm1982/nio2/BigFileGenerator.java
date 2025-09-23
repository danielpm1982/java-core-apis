package com.danielpm1982.nio2;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

public class BigFileGenerator {
    @SuppressWarnings("SameParameterValue")
    protected static boolean generateBigFileAtProjectRootPath(Path directoryPath, Path filePath, long maxFileSizeInBytes, String baseLineText) {
        createResources(directoryPath, filePath);
        writeToResourcesUsingIOAPI(filePath, maxFileSizeInBytes, baseLineText);
//        writeToResourcesUsingNIO2API(filePath, maxFileSizeInBytes, baseLineText);
        return true;
    }
    private static void createResources(Path directoryPath, Path filePath){
        try {
            if (Files.notExists(directoryPath)) {
                IO.println("Creating directory...");
                Files.createDirectories(directoryPath);
                IO.println("Directory successfully created !");
            }
            if (Files.exists(filePath)) {
                IO.println("File already exists (as well as its directory) ! Deleting existing file !");
                Files.delete(filePath);
                IO.println("Existing file deleted !");
            }
            if (Files.notExists(filePath)){
                IO.println("Creating new empty file...");
                Files.createFile(filePath);
                IO.println("File successfully created !");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error creating the file or its directory !" + e.getMessage());
        }
    }
    private static void writeToResourcesUsingIOAPI(Path filePath, long maxFileSizeInBytes, String baseLineText) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath.toString(), false))){
            IO.println("Writing to file...");
            long currentTempSize = 0;
            while (currentTempSize < maxFileSizeInBytes) {
                bufferedWriter.write(baseLineText);
                currentTempSize += baseLineText.getBytes().length;
            }
            IO.println("File written successfully !");
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file !" + e.getMessage());
        }
    }
    private static void writeToResourcesUsingNIO2API(Path filePath, long maxFileSizeInBytes, String baseLineText) {
        try {
            IO.println("Writing to file...");
            long currentTempSize = 0;
            while (currentTempSize < maxFileSizeInBytes) {
                Files.writeString(filePath, baseLineText, StandardOpenOption.APPEND);
                currentTempSize += baseLineText.getBytes().length;
            }
            IO.println("File written successfully !");
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file !" + e.getMessage());
        }
    }
}

/*
Through the generateBigFileAtProjectRootPath() method, and using either NIO.2 or old IO package, as a comparison, this class
writes sample String lines to a file, at a given path, until the file reaches the max size. Before writing to the file, both
its directory and the file itself (empty) are created (or recreated, if the file previously exists) through the createResources()
method - also using NIO.2 Files class. Comparing both APIs, it's clear that the IO API is a lot faster when writing to big files
than the NIO.2 API. This BigFileGenerator class, at this project, basically aims at creating a file big enough to allow us to do
some benchmark tests later (at the other classes), comparing further the efficiency, in reading big files, amongst all three
File Stream APIs (IO, NIO and NIO.2).
*/
