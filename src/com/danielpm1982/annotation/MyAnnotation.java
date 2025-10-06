package com.danielpm1982.annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class MyAnnotation {
    public static void execute() {
        MySampleBeanClass beanInstance = new MySampleBeanClass("name1", "address1", "99-99999999");
        List<Field> beanInstanceFieldList = Arrays.asList(beanInstance.getClass().getDeclaredFields());
        List<Method> beanInstanceMethodList = Arrays.asList(beanInstance.getClass().getDeclaredMethods());
        System.out.println("Metadata from class: "+MySampleBeanClass.class.getName());
        System.out.println("\nFields (instance attributes)");
        System.out.println("\n@VisibleField Annotated:");
        beanInstanceFieldList.stream().filter(field -> field.isAnnotationPresent(VisibleField.class)).
                forEach(System.out::println);
        System.out.println("\nNot @VisibleField Annotated:");
        beanInstanceFieldList.stream().filter(field -> !field.isAnnotationPresent(VisibleField.class)).
                forEach(System.out::println);
        System.out.println("\nMethods");
        System.out.println("\n@VisibleMethod Annotated:");
        beanInstanceMethodList.stream().filter(method -> method.isAnnotationPresent(VisibleMethod.class)).
                forEach(System.out::println);
        System.out.println("\nNot @VisibleMethod Annotated:");
        beanInstanceMethodList.stream().filter(method -> !method.isAnnotationPresent(VisibleMethod.class)).
                forEach(System.out::println);
    }
    void main() {
        execute();
    }
}

/*
This class execute() method above does the following:
1 - creates a sample bean instance;
2 - gets the fields and methods of that instance;
3 - filters and shows - separately - the visible and not visible fields and methods, based on the @VisibleField and
@VisibleMethod annotations.

For more about Reflection API see the respective package at this same project - com.danielpm1982.reflection.
*/
