package com.danielpm1982.nio2;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ReadFileNIOTest {
    static void readFileUsingNIOAPI(Path filePath){
        long time1 = System.currentTimeMillis();
        try (FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.READ)){
            ByteBuffer byteBuffer = ByteBuffer.allocate(64*1024);
            while (fileChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
//                IO.println(new String(byteBuffer.array(), StandardCharsets.UTF_8));
                byteBuffer.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file at path "+filePath+" ! "+e.getMessage());
        }
        long time2 = System.currentTimeMillis();
        IO.println("Java NIO API took "+(time2-time1)+"ms for reading the big file !");
    }
}

/*
Through the readFileUsingNIOAPI() method, and using only the NIO API package - java.nio, this class shows a very efficient
way of reading a file. This is, by far, the most efficient way of reading big files in Java, up until now (see the benchmark
times at the execution). It reads a file, from a given path, into a buffer, line by line, using the FileChannel and ByteBuffer
classes. Regarding the buffer used, observe that, according to the size of memory we allocate for it, we turn the reading more
or less efficient - the larger the memory, the faster the reading will be. We're not printing the content here to the console,
we're just measuring the efficiency in reading the file by using this NIO API. The time in milliseconds for the process is then
displayed.
*/
