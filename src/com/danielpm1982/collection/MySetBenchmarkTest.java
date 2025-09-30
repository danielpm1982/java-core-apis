package com.danielpm1982.collection;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class MySetBenchmarkTest {
    private static final List<Integer> elementList = Arrays.asList(getRandomIntegerDataAsArray(0,10000000,
            1000000));
    public static void executeSetBenchMarkTest() {
        IO.println("\nBenchmark of Set classes comparing performances between HashSet, LinkedHashSet and TreeSet:");
        IO.println("\nCreating HashSet, LinkedHashSet and TreeSet instances and populating them with same elements (duplicates automatically " +
                "discarded)...\n");
        Set<Integer> integerHashSet = new HashSet<>(
                elementList
        );
        Set<Integer> integerLinkedHashSet = new LinkedHashSet<>(
                elementList
        );
        Set<Integer> integerTreeSet = new TreeSet<>(
                elementList
        );
        Set<Integer> integerTreeSetWithComparator = new TreeSet<>(Comparator.comparing(Integer::intValue).reversed());
        integerTreeSetWithComparator.addAll(integerTreeSet);
        proceedOrderTest(integerHashSet, integerLinkedHashSet, integerTreeSet, integerTreeSetWithComparator);
        IO.println();
        proceedDeletionTest(integerHashSet, integerLinkedHashSet, integerTreeSet);
        IO.println();
        proceedInsertionReversedSortingTest(integerHashSet, integerLinkedHashSet, integerTreeSet);
        IO.println();
        proceedAccessingTest(integerHashSet, integerLinkedHashSet, integerTreeSet);
        IO.println("\nEnd of Set classes Benchmark !");
    }
    @SuppressWarnings("SameParameterValue")
    private static Integer[] getRandomIntegerDataAsArray(final int MIN_VALUE, final int MAX_VALUE, final int MAX_SIZE){
        return new Random().ints(MIN_VALUE, MAX_VALUE).boxed().limit(MAX_SIZE).toArray(Integer[]::new);
    }
    private static void proceedOrderTest(Set<Integer> integerHashSet, Set<Integer> integerLinkedHashSet, Set<Integer> integerTreeSet,
                                         Set<Integer> integerTreeSetWithComparator){
        IO.println("Checking order of unique elements (n="+elementList.stream().distinct().toList().size()+
                ") after insertion of same elements (n="+elementList.size()+") into equal-sized sets (duplicates discarded):");
        IO.println("Elements at a "+integerHashSet.getClass().getTypeName()+" (no order):");
//        integerHashSet.forEach(x->IO.print(x+" "));
        IO.print("First and last 10 elements: "); integerHashSet.stream().limit(10).forEach(x->IO.print(x+" ")); IO.print(" ... "); integerHashSet.stream().skip(integerTreeSet.size()-10).forEach(x->IO.print(x+" "));
        IO.println("\nElements at a "+integerLinkedHashSet.getClass().getTypeName()+" (insertion order):");
//        integerLinkedHashSet.forEach(x->IO.print(x+" "));
        IO.print("First and last 10 elements: "); integerLinkedHashSet.stream().limit(10).forEach(x->IO.print(x+" ")); IO.print(" ... "); integerLinkedHashSet.stream().skip(integerTreeSet.size()-10).forEach(x->IO.print(x+" "));
        IO.println("\nElements at a "+integerTreeSet.getClass().getTypeName()+" (natural order):");
//        integerTreeSet.forEach(x->IO.print(x+" "));
        IO.print("First and last 10 elements: "); integerTreeSet.stream().limit(10).forEach(x->IO.print(x+" ")); IO.print(" ... "); integerTreeSet.stream().skip(integerTreeSet.size()-10).forEach(x->IO.print(x+" "));
        IO.println("\nElements at a "+integerTreeSetWithComparator.getClass().getTypeName()+" (instantiated with reversed-order Comparator):");
//        integerTreeSetWithComparator.forEach(x->IO.print(x+" "));
        IO.print("First and last 10 elements: "); integerTreeSetWithComparator.stream().limit(10).forEach(x->IO.print(x+" ")); IO.print(" ... "); integerTreeSetWithComparator.stream().skip(integerTreeSet.size()-10).forEach(x->IO.print(x+" "));
    }
    @SuppressWarnings("Duplicates")
    private static void proceedDeletionTest(Set<Integer> integerHashSet, Set<Integer> integerLinkedHashSet, Set<Integer> integerTreeSet){
        IO.println("\nFor deleting around the same amount of unique elements from equal-sized sets:");
        Map<String, Long> timeResultMap = Map.ofEntries(
                Map.entry(integerHashSet.getClass().getTypeName(), printTimeToDeleteElements(integerHashSet)),
                Map.entry(integerLinkedHashSet.getClass().getTypeName(), printTimeToDeleteElements(integerLinkedHashSet)),
                Map.entry(integerTreeSet.getClass().getTypeName(), printTimeToDeleteElements(integerTreeSet))
        );
        List<Map.Entry<String, Long>> timeResultMapEntrySortedByValueList = timeResultMap.entrySet().stream().
                sorted(Map.Entry.comparingByValue()).toList();
        IO.println(timeResultMapEntrySortedByValueList.getFirst().getKey()+" ("+timeResultMapEntrySortedByValueList.getFirst().getValue()+
                " ms) is more efficient than "+timeResultMapEntrySortedByValueList.get(1).getKey()+" ("+
                timeResultMapEntrySortedByValueList.get(1).getValue()+" ms), which is more efficient than "+
                timeResultMapEntrySortedByValueList.get(2).getKey()+" ("+
                timeResultMapEntrySortedByValueList.get(2).getValue()+" ms) in deleting elements ! This does not reflect the efficiency of " +
                "deleting alone, but in combination with the creation of the list with the elements to delete !");
    }
    private static long printTimeToDeleteElements(Set<Integer> set){
        Set<Integer> tempSet = getClonedIntegerSet(set);
        IO.println("Deleting elements from a "+tempSet.getClass().getTypeName());
        IO.println("Set size before exclusions: "+tempSet.size());
        Instant initialInstant = Instant.now();
        Arrays.asList(getRandomIntegerDataAsArray(0,10000000, 1000000)).forEach(tempSet::remove);
        Instant finalInstant = Instant.now();
        IO.println("Set size after exclusions: "+tempSet.size());
        long timeConsumedInMillis = Duration.between(initialInstant, finalInstant).toMillis();
        IO.println("Time consumed: "+timeConsumedInMillis+" ms.");
        return timeConsumedInMillis;
    }
    @SuppressWarnings("Duplicates")
    private static void proceedInsertionReversedSortingTest(Set<Integer> integerHashSet, Set<Integer> integerLinkedHashSet, Set<Integer> integerTreeSet){
        IO.println("For inserting non-unique elements (duplicates discarded) at any position and reversely sorting equal-sized sets:");
        Map<String, Long> timeResultMap = Map.ofEntries(
                Map.entry(integerHashSet.getClass().getTypeName(), printTimeForInsertionReversedSortingElements(integerHashSet)),
                Map.entry(integerLinkedHashSet.getClass().getTypeName(), printTimeForInsertionReversedSortingElements(integerLinkedHashSet)),
                Map.entry(integerTreeSet.getClass().getTypeName(), printTimeForInsertionReversedSortingElements(integerTreeSet))
        );
        List<Map.Entry<String, Long>> timeResultMapEntrySortedByValueList = timeResultMap.entrySet().stream().
                sorted(Map.Entry.comparingByValue()).toList();
        IO.println(timeResultMapEntrySortedByValueList.getFirst().getKey()+" ("+timeResultMapEntrySortedByValueList.getFirst().getValue()+
                " ms) is more efficient than "+timeResultMapEntrySortedByValueList.get(1).getKey()+" ("+
                timeResultMapEntrySortedByValueList.get(1).getValue()+" ms), which is more efficient than "+
                timeResultMapEntrySortedByValueList.get(2).getKey()+" ("+
                timeResultMapEntrySortedByValueList.get(2).getValue()+" ms) in inserting and sorting elements ! This does not reflect the " +
                "efficiency of insertion alone, but in combination with creation of the list with the elements to add, as well as the " +
                "sorting process. Other than that, the HashSet does not allow direct sorting of elements, as there's no order guaranteed " +
                "at the structure by definition. Therefore, for the HashSet, we have to deal with the additional overhead of using Streams " +
                "for sorting, not mentioning having to collect the ordered result stream into an insertion-ordered Collection, as a List " +
                "or a LinkedHashSet. For LinkedHashSet and TreeSet, we can perform the ordering directly on the data structure itself.");
    }
    @SuppressWarnings("UseBulkOperation")
    private static long printTimeForInsertionReversedSortingElements(Set<Integer> set){
        Set<Integer> tempSet = getClonedIntegerSet(set);
        IO.println("Inserting and reversely sorting elements at a "+tempSet.getClass().getTypeName());
        IO.println("Set size before insertion and reversed sorting: "+tempSet.size());
        Instant initialInstant = Instant.now();
        Arrays.asList(getRandomIntegerDataAsArray(0,10000000, 100000)).forEach(tempSet::add);
//      From Set classes, only TreeSet and LinkedHashSet can be sorted directly (without Streams), returning a NavigableSet and a
//      SequencedSet respectively.
        if(tempSet instanceof TreeSet<Integer>){
            tempSet = ((TreeSet<Integer>) tempSet).reversed();
        } else if(tempSet instanceof LinkedHashSet<Integer>){
            tempSet = ((LinkedHashSet<Integer>) tempSet).reversed();
        } else {
//      In the case of HashSet, the order is not guaranteed, and there's no way to sort this data structure directly, thus we have to
//      send the data to a LinkedHashSet (through Constructors or Streams) and then sort the data with the desired order. Otherwise,
//      the ordering might be lost after collection.
            tempSet = tempSet.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toCollection(LinkedHashSet::new));
        }
        Instant finalInstant = Instant.now();
        IO.println("Set size after insertion and reversed sorting: "+tempSet.size());
        long timeConsumedInMillis = Duration.between(initialInstant, finalInstant).toMillis();
        IO.println("Time consumed: "+timeConsumedInMillis+" ms.");
        return timeConsumedInMillis;
    }
    private static void proceedAccessingTest(Set<Integer> integerHashSet, Set<Integer> integerLinkedHashSet, Set<Integer> integerTreeSet){
        IO.println("For accessing unique elements at random positions of equal-sized sets:");
        Map<String, Long> timeResultMap = Map.ofEntries(
                Map.entry(integerHashSet.getClass().getTypeName(), printTimeForAccessingElements(integerHashSet)),
                Map.entry(integerLinkedHashSet.getClass().getTypeName(), printTimeForAccessingElements(integerLinkedHashSet)),
                Map.entry(integerTreeSet.getClass().getTypeName(), printTimeForAccessingElements(integerTreeSet))
        );
        List<Map.Entry<String, Long>> timeResultMapEntrySortedByValueList = timeResultMap.entrySet().stream().
                sorted(Map.Entry.comparingByValue()).toList();
        IO.println(timeResultMapEntrySortedByValueList.getFirst().getKey()+" ("+timeResultMapEntrySortedByValueList.getFirst().getValue()+
                " ms) is more efficient than "+timeResultMapEntrySortedByValueList.get(1).getKey()+" ("+
                timeResultMapEntrySortedByValueList.get(1).getValue()+" ms), which is more efficient than "+
                timeResultMapEntrySortedByValueList.get(2).getKey()+" ("+
                timeResultMapEntrySortedByValueList.get(2).getValue()+" ms) in retrieving elements ! This does not reflect the efficiency " +
                "of accessing elements alone, but in combination with Streams filtering and comparing processes (between a set and a list - " +
                "elementToFindList, in this case) !");
    }
    private static long printTimeForAccessingElements(Set<Integer> set){
        final int MAX_NUMBER_OF_ELEMENTS = 1000;
        Set<Integer> tempSet = getClonedIntegerSet(set);
        IO.println("Looking for "+MAX_NUMBER_OF_ELEMENTS+" elements at a "+tempSet.getClass().getTypeName()+" of size "+tempSet.size());
        List<Integer> elementToFindList = Arrays.asList(getRandomIntegerDataAsArray(0,10000000, MAX_NUMBER_OF_ELEMENTS));
        Instant initialInstant = Instant.now();
        List<Integer> elementFoundList = tempSet.stream().filter(elementToFindList::contains).toList();
        Instant finalInstant = Instant.now();
        IO.println("From the "+MAX_NUMBER_OF_ELEMENTS+" random elements looked into the set, "+elementFoundList.size()+" element(s) " +
                "found:");
        IO.println(elementFoundList.isEmpty() ? "No elements to show !" : elementFoundList);
        long timeConsumedInMillis = Duration.between(initialInstant, finalInstant).toMillis();
        IO.println("Time consumed: "+timeConsumedInMillis+" ms.");
        return timeConsumedInMillis;
    }
    private static Set<Integer> getClonedIntegerSet(Set<Integer> set){
        return switch (set.getClass().getTypeName()) {
            case "java.util.HashSet" -> new HashSet<>(set);
            case "java.util.LinkedHashSet" -> new LinkedHashSet<>(set);
            case "java.util.TreeSet" -> new TreeSet<>(set);
            default -> throw new RuntimeException("Couldn't detect the type of instance of the Set for Benchmark testing !");
        };
    }
}

/*
This class basically instantiates and populates HashSet, LinkedHashSet and TreeSet instances with sample random Integer values,
and compares how efficient these classes are in performing CRUD operations, as: delete, insert, inversely sort and accessing
(querying) random Integer elements. It also shows the native order of elements when using each data structure.
*/
