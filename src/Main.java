import com.danielpm1982.annotation.MyAnnotation;
import com.danielpm1982.collection.MyCollection;
import com.danielpm1982.cryptography.MyCryptography;
import com.danielpm1982.datetime.MyDateTime;
import com.danielpm1982.nio2.MyNIO2;
import com.danielpm1982.optional.MyOptional;
import com.danielpm1982.reflection.MyReflection;
import com.danielpm1982.regex.MyRegex;

public class Main {
    void main(String[] args) {
        IO.println("Initializing Optional API demonstration...\n");
        MyOptional.execute();
        IO.println("\nOptional API demonstration successfully executed !");
        IO.println("\n=============================================\n");
        IO.println("Initializing DateTime API demonstration...\n");
        MyDateTime.execute();
        IO.println("\nDateTime API demonstration successfully executed !");
        IO.println("\n=============================================\n");
        IO.println("Initializing Reflection API demonstration...\n");
        MyReflection.execute();
        IO.println("\nReflection API demonstration successfully executed !");
        IO.println("\n=============================================\n");
        IO.println("Initializing Annotation API demonstration...\n");
        MyAnnotation.execute();
        IO.println("\nAnnotation API demonstration successfully executed !");
        IO.println("\n=============================================\n");
        IO.println("Initializing RegEx API demonstration...\n");
        MyRegex.execute();
        IO.println("\nRegEx API demonstration successfully executed !");
        IO.println("\n=============================================\n");
        IO.println("Initializing Cryptography API demonstration...\n");
        MyCryptography.execute();
        IO.println("\nCryptography API demonstration successfully executed !");
        IO.println("\n=============================================\n");
        IO.println("Initializing NIO2 API demonstration...\n");
        MyNIO2.execute();
        IO.println("\nNIO2 API demonstration successfully executed !");
        IO.println("\n=============================================\n");
        IO.println("Initializing Collections API demonstration...\n");
        MyCollection.execute();
        IO.println("\nCollections API demonstration successfully executed !");
    }
}

/*
This is the main class (entrypoint) of this project.
It's meant to execute sample code implementations, using each discussed Java core API (the most used APIs of Java).
The API examples are organized into their own packages, for instance, com.danielpm1982.optional, com.danielpm1982.datetime,
com.danielpm1982.reflection and so on.
Detailed explanation is available at the code itself, within each class, inside each package.
This project was implemented using Java OpenJDK 25. If your IDE asks for the Java module (.iml), when trying to run this
project, you can simply create it - at the IDE respective section - and point it out to your installation package of Java 25.
No further dependencies are necessary. For the greatest simplicity, we didn't use Maven or Gradle at this project.
*/
