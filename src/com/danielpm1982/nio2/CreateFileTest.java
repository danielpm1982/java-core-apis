package com.danielpm1982.nio2;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CreateFileTest {
    public static void creatSampleBigFile(Path filePath) {
        final long myMaxFileSizeInBytes = 200*1024*1024;
        final String myBaseLineText = "This is a test text line !\n";
        if(BigFileGenerator.generateBigFileAtProjectRootPath(filePath, myMaxFileSizeInBytes, myBaseLineText)){
            showFileProperties(filePath);
        }
    }
    @SuppressWarnings("SameParameterValue")
    private static void showFileProperties(Path filePath){
        if(!filePath.toFile().exists()){
            throw new RuntimeException("File at path doesn't exist ! Cannot show file properties ! "+filePath);
        }
        try{
            BasicFileAttributeView fileAttributeView = Files.getFileAttributeView(filePath, BasicFileAttributeView.class);
            IO.println("File attributes...\n" +
                    "path: " + filePath.toAbsolutePath().toString() + "\n" +
                    "creation time: " + showDateTimeStringFromFileTime(fileAttributeView.readAttributes().creationTime()) + "\n" +
                    "last accessed: " + showDateTimeStringFromFileTime(fileAttributeView.readAttributes().lastAccessTime()) + "\n" +
                    "last modified: " + showDateTimeStringFromFileTime(fileAttributeView.readAttributes().lastModifiedTime()) + "\n" +
                    "size: " + showBytesAsString(fileAttributeView.readAttributes().size()) + "\n" +
                    "is regular file ? " + fileAttributeView.readAttributes().isRegularFile() + "\n" +
                    "is directory ? " + fileAttributeView.readAttributes().isDirectory() + "\n" +
                    "is symbolic link ? " + fileAttributeView.readAttributes().isSymbolicLink() + "\n" +
                    "is other ? " + fileAttributeView.readAttributes().isOther()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error showing file's properties ! "+e.getMessage());
        }
    }
    private static String showDateTimeStringFromFileTime(FileTime fileTime){
        DateTimeFormatter myDateTimeFormatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        ZonedDateTime zdt = ZonedDateTime.ofInstant(fileTime.toInstant(), ZoneId.systemDefault());
        return myDateTimeFormatter.format(zdt);
    }
    private static String showBytesAsString(long bytes){
        String[] units = {"Bytes", "KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        while (bytes >= 1024 && unitIndex < units.length - 1) {
            bytes /= 1024;
            unitIndex++;
        }
        return String.format("%.2f %s", (double) bytes, units[unitIndex]);
    }
}

/*
By calling the BigFileGenerator.generateBigFileAtProjectRootPath() method, this class triggers the creation of a sample big file,
at a given path, with the max size and String line content defined here. If the file is successfully created, or if the file already
exists - by having already being created at a previous run, this class then displays at the console the basic file properties of
such file. Basically, it calls for the creation of the big file and shows its properties.
*/
