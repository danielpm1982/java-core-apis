package com.danielpm1982.nio2;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReadFileNIO2Test {
    static void readFileUsingNIO2API(Path filePath){
        long time1 = System.currentTimeMillis();
        try {
//            List<String> fileTempLinesList = Files.readAllLines(filePath, StandardCharsets.UTF_8);
//            fileTempLinesList.forEach(IO::println);
            Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file at path "+filePath+" ! "+e.getMessage());
        }
        long time2 = System.currentTimeMillis();
        IO.println("Java NIO2 API took "+(time2-time1)+"ms for reading the big file !");
    }
}

/*
Through the readFileUsingNIO2API() method, and using only the NIO.2 API package - java.nio.file, this class shows the easiest way
of reading a file (although not the most efficient one), by using the Java NIO2 API. It reads all lines of a file, from a given path,
directly into a List<String>, using the Files class. With this API, the programmer doesn't have to deal with low-level treatment of the
file data, and may manipulate the String lines at the List<String> at once... showing them at the console or outputting to any other
file or socket. We're not printing the content here to the console, we're just measuring the efficiency in reading the file by using
this NIO2 API. The time in milliseconds for the process is then displayed.
*/
