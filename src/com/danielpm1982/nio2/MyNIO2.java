package com.danielpm1982.nio2;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyNIO2 {
    public static void execute () {
        final Path myDirectoryPath =
                Paths.get(System.getProperty("user.dir"), "files");
        final Path myFilePath =
                myDirectoryPath.resolve(Paths.get("myFileName.txt"));

        IO.println("Attempting to write a sample big file...");
        CreateFileTest.creatSampleBigFile(myDirectoryPath, myFilePath);
        IO.println("\nAttempting to read a sample big file using IO API...");
        ReadFileIOTest.readFileUsingIOAPI(myFilePath);
        IO.println("\nAttempting to read a sample big file using NIO API...");
        ReadFileNIOTest.readFileUsingNIOAPI(myFilePath);
        IO.println("\nAttempting to read a sample big file using NIO2 API...");
        ReadFileNIO2Test.readFileUsingNIO2API(myFilePath);
    }
}

/*
Through the execute() method, and using only NIO.2 API package - java.nio.file, this class defines the path for the big file,
created at this project, as well as the path for its directory, and passes them both when calling the file creating class (the
directory must be created before the file). The path for the big file, alone, is also passed when calling, in sequence, the file
reading classes. The reading classes, called here, will read the big file content, in 3 possible ways, by using the IO, the NIO
and the NIO2 APIs, and will show, at the console, the times that each API took to read the same big file. This is thus a Benchmark
class that compares how fast the same big file is read whe using each of the three available IO APIs at Java 25.
*/
