package com.danielpm1982.reflection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyReflection {
    public static void execute(){
        MySampleBeanClass mySampleBeanClass = new MySampleBeanClass("anySampleName");
        try {
            MyClassExplorer.exploreClassMetaData(mySampleBeanClass);
        } catch (Exception e) {
            Logger.getLogger(MyReflection.class.getName()).log(Level.SEVERE, "An exception has been thrown: "+e.getClass().getName());
        }
    }
    void main(){
        execute();
    }
}

/*
This class simply creates an instance of the sample bean class, and then pass it to the exploreClassMetaData() method,
from the class MyClassExplorer, in order to have all its metadata explored and shown to the user, through the Reflection
API. It also catches and treats any Exception thrown by the internal called classes/methods, adding a note to the log,
with the explanation and class type of the Exception thrown/caught (instead of showing the whole stacktrace to the user).
*/
