import com.danielpm1982.datetime.MyDateTime;
import com.danielpm1982.optional.MyOptional;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing Optional API demonstration...\n");
        MyOptional.execute();
        System.out.println("\nOptional API demonstration successfully executed !");
        System.out.println("\n=============================================\n");
        System.out.println("Initializing DateTime API demonstration...\n");
        MyDateTime.execute();
        System.out.println("\nDateTime API demonstration successfully executed !");
    }
}

/*
This is the main class (entrypoint) of this project.
It's meant to execute sample code implementations, using each discussed Java core API (the most used APIs of Java).
The API examples are organized into their own packages, for instance, com.danielpm1982.optional, com.danielpm1982.datetime,
and so on.
Detailed explanation is available at the code itself, within each class, inside each package.
This project was implemented using Java OpenJDK 25. If your IDE asks for the Java module (.iml), when trying to run this
project, you can simply create it - at the IDE respective section - and point it out to your installation package of Java 25.
No further dependencies are necessary. For the greatest simplicity, we didn't use Maven or Gradle at this project.
*/
