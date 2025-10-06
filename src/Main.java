import com.danielpm1982.annotation.MyAnnotation;
import com.danielpm1982.collection.MyCollection;
import com.danielpm1982.cryptography.MyCryptography;
import com.danielpm1982.datetime.MyDateTime;
import com.danielpm1982.networking.MyNetworking;
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
        IO.println("\n=============================================\n");
        IO.println("Initializing Networking API demonstration...\n");
        MyNetworking.execute();
        IO.println("\nNetworking API demonstration successfully executed !");
    }
}

/*
This is the main class (entrypoint) of this Java Core exemplifying project - which is a didactic project for teaching Java 25,
not a real app to be deployed. You should import it to your IDE (e.g. IntelliJ IDEA) and analyse the source code and
explanations at the source classes. It's supposed to show, at each subproject, how to use a specific Java Core API and how to
run it for testing. The Java Core API examples are organized into their own standalone subpackages, e.g. com.danielpm1982.optional,
com.danielpm1982.datetime, com.danielpm1982.reflection and so on. You can have the complete list of subpackages at the README.md,
at the root of this project.
This particular entrypoint Main class, when run, executes all subprojects of this project at once.
If you want to execute each subproject at a time, just look for the entrypoint class at the root of each subproject, named as
"My[nameOfSubpackage]". You'll also find a main method at each of these entrypoint subprojects' classes with an execute() method
that executes only that particular subproject.
This project was implemented using Java 25 LTS. If your IDE asks for the Java module (.iml), when trying to run this project, you
can simply create it yourself, after importing this project into your IDE, at Project Structure -> Project Settings -> Modules and
point out to your local Java JDK 25 installation from there.
For the greatest simplicity of this project, we didn't use testing classes, as we also didn't use Maven or Gradle build tools. External
dependencies are all available as jars at the "lib" folder, at the root path of this project. Add them to your classpath before running
this project (or its subprojects). You can do that at Project Structure -> Project Settings -> Libraries .
This project was created using Jetbrains IntelliJ IDEA Ultimate, but you can adapt it for running at any other Java IDE you wish
(Eclipse, Netbeans, VSCode, etc).
*/
