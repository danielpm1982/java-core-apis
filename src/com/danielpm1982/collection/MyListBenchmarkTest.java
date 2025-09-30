package com.danielpm1982.collection;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class MyListBenchmarkTest {
    public static void executeListBenchMarkTest() {
        IO.println("\nBenchmark of List classes comparing performances between ArrayList and LinkedList:");
        IO.println("\nCreating ArrayList and equivalent LinkedList instances and populating them with same elements (duplicates allowed)...\n");
        List<Integer> integerArrayList = new ArrayList<>(
                Arrays.asList(getRandomIntegerDataAsArray(1,1000,1000000))
        );
        List<Integer> integerLinkedList = new LinkedList<>(
                integerArrayList
        );
        proceedDeletionTest(integerArrayList, integerLinkedList);
        IO.println();
        proceedInsertionReversedSortingTest(integerArrayList, integerLinkedList);
        IO.println();
        proceedAccessingTest(integerArrayList, integerLinkedList);
        IO.println("\nEnd of List classes Benchmark !");
    }
    @SuppressWarnings("SameParameterValue")
    private static Integer[] getRandomIntegerDataAsArray(final int MIN_VALUE, final int MAX_VALUE, final int MAX_SIZE){
        return new Random().ints(MIN_VALUE, MAX_VALUE).boxed().limit(MAX_SIZE).toArray(Integer[]::new);
    }
    private static void proceedDeletionTest(List<Integer> integerArrayList, List<Integer> integerLinkedList){
        IO.println("For deleting around the same amount of non-unique elements from equal-sized lists:");
        long timeForArrayList = printTimeToDeleteElements(integerArrayList);
        long timeForLinkedList = printTimeToDeleteElements(integerLinkedList);
        IO.println((timeForLinkedList<timeForArrayList ? "LinkedList" : "ArrayList") + " is more efficient in deleting elements ! This " +
                "does not reflect the efficiency of deletion alone, but in combination with the creation of the list with the elements to " +
                "be deleted !");
    }
    private static long printTimeToDeleteElements(List<Integer> list){
        List<Integer> tempList = getClonedIntegerList(list);
        IO.println("Deleting elements from a "+tempList.getClass().getTypeName());
        IO.println("List size before exclusions: "+tempList.size());
        Instant initialInstant = Instant.now();
        Arrays.asList(getRandomIntegerDataAsArray(1,1000,1000)).forEach(tempList::remove);
        Instant finalInstant = Instant.now();
        IO.println("List size after exclusions: "+tempList.size());
        long timeConsumedInMillis = Duration.between(initialInstant, finalInstant).toMillis();
        IO.println("Time consumed: "+timeConsumedInMillis+" ms.");
        return timeConsumedInMillis;
    }
    private static void proceedInsertionReversedSortingTest(List<Integer> integerArrayList, List<Integer> integerLinkedList){
        IO.println("For inserting non-unique elements at the first position (and moving all other elements one position forward) and " +
                "reversely sorting equal-sized lists:");
        long timeForArrayList = printTimeForInsertionReversedSortingElements(integerArrayList);
        long timeForLinkedList = printTimeForInsertionReversedSortingElements(integerLinkedList);
        IO.println((timeForLinkedList<timeForArrayList ? "LinkedList" : "ArrayList") + " is more efficient in inserting, moving and " +
                "sorting elements ! This does not reflect the efficiency of insertion alone, but in combination with the creation of " +
                "the list with the elements to be added, as well as the sorting process !");
    }
    private static long printTimeForInsertionReversedSortingElements(List<Integer> list){
        List<Integer> tempList = getClonedIntegerList(list);
        IO.println("Inserting (at first position), moving and reversely sorting elements at a "+tempList.getClass().getTypeName());
        IO.println("List size before insertion and reversed sorting: "+tempList.size());
        Instant initialInstant = Instant.now();
        Arrays.asList(getRandomIntegerDataAsArray(1,1000,100000)).forEach(tempList::addFirst);
        tempList.sort(Comparator.comparingInt(Integer::intValue).reversed());
        Instant finalInstant = Instant.now();
        IO.println("List size after insertion, moving and reversed sorting: "+tempList.size());
        long timeConsumedInMillis = Duration.between(initialInstant, finalInstant).toMillis();
        IO.println("Time consumed: "+timeConsumedInMillis+" ms.");
        return timeConsumedInMillis;
    }
    private static void proceedAccessingTest(List<Integer> integerArrayList, List<Integer> integerLinkedList){
        IO.println("For accessing elements at random positions of equal-sized lists:");
        long timeForArrayList = printTimeForAccessingElements(integerArrayList);
        long timeForLinkedList = printTimeForAccessingElements(integerLinkedList);
        IO.println((timeForArrayList<timeForLinkedList ? "ArrayList" : "LinkedList") + " is more efficient in retrieving elements ! " +
                "This does not reflect the efficiency of accessing elements alone, but in combination with the creation of the index list " +
                "with the index values to look for at the main list being accessed !");
    }
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static long printTimeForAccessingElements(List<Integer> list){
        int MAX_NUMBER_OF_ELEMENTS = 10000;
        IO.println("Accessing "+MAX_NUMBER_OF_ELEMENTS+" elements at a "+list.getClass().getTypeName()+" of size "+list.size());
        Instant initialInstant = Instant.now();
        Arrays.asList(getRandomIntegerDataAsArray(0, list.size()-1, MAX_NUMBER_OF_ELEMENTS)).forEach(list::get);
        Instant finalInstant = Instant.now();
        long timeConsumedInMillis = Duration.between(initialInstant, finalInstant).toMillis();
        IO.println("Time consumed: "+timeConsumedInMillis+" ms.");
        return timeConsumedInMillis;
    }
    private static List<Integer> getClonedIntegerList(List<Integer> list){
        return switch (list.getClass().getTypeName()) {
            case "java.util.ArrayList" -> new ArrayList<>(list);
            case "java.util.LinkedList" -> new LinkedList<>(list);
            default -> throw new RuntimeException("Couldn't detect the type of instance of the List for Benchmark testing !");
        };
    }
}

/*
This class basically instantiates and populates an ArrayList and LinkedList with sample random Integer values, and compares how
efficient these classes are in performing CRUD operations, as: delete, insert, inversely sort and accessing (querying) random
Integer elements.
*/
