import com.danielpm1982.annotation.MyAnnotation;
import com.danielpm1982.cryptography.MyCryptography;
import com.danielpm1982.datetime.MyDateTime;
import com.danielpm1982.optional.MyOptional;
import com.danielpm1982.reflection.MyReflection;
import com.danielpm1982.regex.MyRegex;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing Optional API demonstration...\n");
        MyOptional.execute();
        System.out.println("\nOptional API demonstration successfully executed !");
        System.out.println("\n=============================================\n");
        System.out.println("Initializing DateTime API demonstration...\n");
        MyDateTime.execute();
        System.out.println("\nDateTime API demonstration successfully executed !");
        System.out.println("\n=============================================\n");
        System.out.println("Initializing Reflection API demonstration...\n");
        MyReflection.execute();
        System.out.println("\nReflection API demonstration successfully executed !");
        System.out.println("\n=============================================\n");
        System.out.println("Initializing Annotation API demonstration...\n");
        MyAnnotation.execute();
        System.out.println("\nAnnotation API demonstration successfully executed !");
        System.out.println("\n=============================================\n");
        System.out.println("Initializing RegEx API demonstration...\n");
        MyRegex.execute();
        System.out.println("\nRegEx API demonstration successfully executed !");
        System.out.println("\n=============================================\n");
        System.out.println("Initializing Cryptography API demonstration...\n");
        MyCryptography.execute();
        System.out.println("\nCryptography API demonstration successfully executed !");
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
