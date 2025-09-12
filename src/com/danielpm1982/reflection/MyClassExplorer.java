package com.danielpm1982.reflection;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MyClassExplorer {
    protected static void exploreClassMetaData(Object o) throws Exception{
        System.out.println("Metadata description for class: "+o.getClass().getName());
        System.out.println("\nAttributes");
        System.out.printf("%-20s %-20s %-20s %-20s %n", "Name:", "Type:", "Modifier:", "Value:");
        for(Field f : o.getClass().getDeclaredFields()){
            f.setAccessible(true);
            System.out.printf("%-20s %-20s %-20s %-20s %n", f.getName(), f.getType().getName(),
                    Modifier.toString(f.getModifiers()), f.get(o));
            f.setAccessible(false);
        }
        System.out.println("\nMethods");
        System.out.printf("%-20s %-20s %-20s %n", "Name:", "Return Type:", "Modifier:");
        for(Method m : o.getClass().getDeclaredMethods()){
            m.setAccessible(true);
            System.out.printf("%-20s %-20s %-20s %n", m.getName(), m.getReturnType().getName(), Modifier.toString(m.getModifiers()));
            m.setAccessible(false);
        }
        System.out.println("\nMethods");
        System.out.printf("%-20s %-20s %-20s %n", "Name:", "Argument Value:", "Return Value:");
        for(Method m : o.getClass().getDeclaredMethods()){
            m.setAccessible(true);
            if(m.getName().startsWith("get") || m.getName().contentEquals("toString") ||
                    m.getName().contentEquals("hashCode")){
                System.out.printf("%-20s %-20s %-20s %n", m.getName(), "null", m.invoke(o));
            }
            if(m.getName().contentEquals("equals")){
                System.out.printf("%-20s %-20s %-20s %n", m.getName(), "same instance", m.invoke(o, o));
                System.out.printf("%-20s %-20s %-20s %n", m.getName(), "different instance", m.invoke(o, new Object()));
            }
            if(m.getName().contentEquals("setName")){
                System.out.printf("%-20s %-20s %-20s %n", m.getName(), "inputName", m.invoke(o, "Daniel"));
            }
            m.setAccessible(false);
        }
    }
}

/*
Through the exploreClassMetaData() method, as it receives an instance, this class aims at exploring all metadata
associated to that instance, namely the attributes' name, type, modifier and value, as well as the methods' name,
return type, modifier, argument value and return value - these latter two as we invoke the methods programmatically.
That's possible through the Reflection API, which is an API associated to all classes, and therefore all instances.
Through the getClass() method, we can get the complete name of the class (including the package); through the
getDeclaredFields() method, we can get all attributes declared at that received instance - and, for each Field, get
all fields' metadata (name, type, modifier, value); through the getDeclaredMethods() method, we can get all methods
declared at the received instance - and for each Method, get all methods' metadata (name, type, modifier, argument
value and return value). For getting a Field value, we use the get() instance method of the respective Field, passing
the instance as argument (so that it gets the value for that field at that instance). For getting the values of the
arguments and returns of methods, we use the invoke() instance method of the respective Method, passing the instance
as argument (so that it gets the arg and return values for that method at that instance), and, if the method receives
input arguments, we also pass them right after the instance arg. We must separate the invocation of no-arg methods from
the invocation of arg methods, as, in the first, only the exploring instance is passed to the invoke() method, while,
on the second, we must also pass the method demanded input args - in addition to the exploring instance itself. An
important thing to have in mind is that, for private fields or methods, the access is forbidden, unless you change Field
or Method accessible property through the method setAccessible(), setting it true (while you access it), and then setting
it back to false (right after you no longer need access to it).
*/
