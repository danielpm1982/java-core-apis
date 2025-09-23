package com.danielpm1982.nio2;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyNIO2 {
    public static void execute() {
        final Path myFilePath =
                Paths.get(System.getProperty("user.dir")+ File.separator + "files" + File.separator + "myFileName.txt");
        IO.println("Attempting to write a sample big file...");
        CreateFileTest.creatSampleBigFile(myFilePath);
        IO.println("\nAttempting to read a sample big file using IO API...");
        ReadFileIOTest.readFileUsingIOAPI(myFilePath);
        IO.println("\nAttempting to read a sample big file using NIO API...");
        ReadFileNIOTest.readFileUsingNIOAPI(myFilePath);
        IO.println("\nAttempting to read a sample big file using NIO2 API...");
        ReadFileNIO2Test.readFileUsingNIO2API(myFilePath);
    }
}

/*
Through the execute() method, this class defines the path for the big file, created at this project, and passes that path when calling
the file creating class, as well as when calling, in sequence, the file reading classes. The reading classes, called here, will read
the big file content, in 3 possible ways, by using the IO, the NIO and the NIO2 APIs, and will show, at the console, the times that each
API took to read the same big file. This is thus a Benchmark class that compares how fast the same big file is read whe using each of
the three available IO APIs at Java 25.
*/
