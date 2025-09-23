package com.danielpm1982.nio2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class ReadFileIOTest {
    static void readFileUsingIOAPI(Path filePath){
        long time1 = System.currentTimeMillis();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath.toFile()));
            while(bufferedReader.readLine()!=null){
//                IO.println(bufferedReader.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file at path "+filePath+" ! "+e.getMessage());
        }
        long time2 = System.currentTimeMillis();
        IO.println("Java IO API took "+(time2-time1)+"ms for reading the big file !");
    }
}

/*
Through the readFileUsingIOAPI() method, and using only the IO API package - java.io, this class shows a traditional way of
reading a file. It reads a file, from a given path, into a buffer, line by line, using the FileReader and BufferedReader
classes. We're not printing the content here to the console, we're just measuring the efficiency in reading the file by using
this IO API. The time in milliseconds for the process is then displayed.
*/
