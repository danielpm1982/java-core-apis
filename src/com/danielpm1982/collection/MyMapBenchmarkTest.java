package com.danielpm1982.collection;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyMapBenchmarkTest {
    private static final List<Integer> elementList = Arrays.asList(getRandomIntegerDataAsArray(0,1000,
            1000000));
    public static void executeMapBenchMarkTest() {
        IO.println("\nBenchmark of Map classes comparing performances between HashMap, LinkedHashMap and TreeMap:");
        IO.println("\nCreating HashMap, LinkedHashMap and TreeMap instances and populating them with same elements. Duplicates management " +
                "should be configured at Collectors.toMap(). In the cases below, we define that, when duplicates eventually occur (two values " +
                "are added for a same key), it will be considered the old value and ignored the new one, that is, duplicates will be ignored. " +
                "We could do the opposite or accumulate new values with old existent ones - just by changing the lambda at the Collectors. " +
                "Regarding the key:value pairs, both will NOT be continuous at the maps, even after sorting, as the original list, with the " +
                "elements attempted to be added to the map, has a lot of elements NOT actually added to the map (due to repetition of the " +
                "value), and thus the index of that element at the list also will not be used as a key at the map, because thet value was " +
                "ignored. The keys here are not actual indexes at the map, they're indexes (and continuous) only at the original list. Keys " +
                "at a map do not need not be continuous (although they're unique), and can be of any value, as well as their paired values.\n");
        Map<Integer, Integer> integerHashMap = elementList.stream().collect(Collectors.toMap(elementList::indexOf, Function.identity(),
                (oldElement, newElement) -> oldElement, HashMap::new));
        Map<Integer, Integer> integerLinkedHashMap = elementList.stream().collect(Collectors.toMap(elementList::indexOf, Function.identity(),
                (oldElement, newElement) -> oldElement, LinkedHashMap::new));
        Map<Integer, Integer> integerTreeMap = elementList.stream().collect(Collectors.toMap(elementList::indexOf, Function.identity(),
                (oldElement, newElement) -> oldElement, TreeMap::new));
        Map<Integer, Integer> integerTreeMapWithComparator = elementList.stream().collect(Collectors.toMap(elementList::indexOf,
                Function.identity(), (oldElement, newElement) -> oldElement, ()->new TreeMap<>(Comparator.
                        comparing(elementList::get).reversed())));

        proceedOrderTest(integerHashMap, integerLinkedHashMap, integerTreeMap, integerTreeMapWithComparator);
        IO.println();
        proceedDeletionTest(integerHashMap, integerLinkedHashMap, integerTreeMap);
        IO.println();
        proceedInsertionReversedSortingTest(integerHashMap, integerLinkedHashMap, integerTreeMap);
        IO.println();
        proceedAccessingTest(integerHashMap, integerLinkedHashMap, integerTreeMap);
        IO.println("\nEnd of Map classes Benchmark !");
    }
    @SuppressWarnings("SameParameterValue")
    private static Integer[] getRandomIntegerDataAsArray(final int MIN_VALUE, final int MAX_VALUE, final int MAX_SIZE){
        return new Random().ints(MIN_VALUE, MAX_VALUE).boxed().limit(MAX_SIZE).toArray(Integer[]::new);
    }
    @SuppressWarnings("Duplicates")
    private static void proceedOrderTest(Map<Integer, Integer> integerHashMap, Map<Integer, Integer> integerLinkedHashMap,
                                         Map<Integer, Integer> integerTreeMap, Map<Integer, Integer> integerTreeMapWithComparator){
        IO.println("Checking order of unique elements actually inserted (n="+elementList.stream().distinct().toList().size()+
                ") after trying the insertion of same elements (n="+elementList.size()+") into equal-sized maps (duplicate keys discarded):");
        IO.println("Elements at a "+integerHashMap.getClass().getTypeName()+" (no order):");
//        integerHashMap.forEach((key,value)->IO.print(key+":"+value+" ")); IO.println();
        IO.print("First and last 10 elements: "); integerHashMap.entrySet().stream().limit(10).forEach(entry->IO.print(entry.getKey()+":"+entry.getValue()+" ")); IO.print(" ... "); integerHashMap.entrySet().stream().skip(integerTreeMap.size()-10).forEach(entry->IO.print(entry.getKey()+":"+entry.getValue()+" "));
        IO.println("\n"+integerHashMap.size()+" elements added !");
        IO.println("Elements at a "+integerLinkedHashMap.getClass().getTypeName()+" (insertion order):");
//        integerLinkedHashMap.forEach((key,value)->IO.print(key+":"+value+" ")); IO.println();
        IO.print("First and last 10 elements: "); integerLinkedHashMap.entrySet().stream().limit(10).forEach(entry->IO.print(entry.getKey()+":"+entry.getValue()+" ")); IO.print(" ... "); integerLinkedHashMap.entrySet().stream().skip(integerTreeMap.size()-10).forEach(entry->IO.print(entry.getKey()+":"+entry.getValue()+" "));
        IO.println("\n"+integerLinkedHashMap.size()+" elements added !");
        IO.println("Elements at a "+integerTreeMap.getClass().getTypeName()+" (natural order for keys):");
//        integerTreeMap.forEach((key,value)->IO.print(key+":"+value+" ")); IO.println();
        IO.print("First and last 10 elements: "); integerTreeMap.entrySet().stream().limit(10).forEach(entry->IO.print(entry.getKey()+":"+entry.getValue()+" ")); IO.print(" ... "); integerTreeMap.entrySet().stream().skip(integerTreeMap.size()-10).forEach(entry->IO.print(entry.getKey()+":"+entry.getValue()+" "));
        IO.println("\n"+integerTreeMap.size()+" elements added !");
        IO.println("Elements at a "+integerTreeMapWithComparator.getClass().getTypeName()+" (instantiated with Comparator: reversed order for values):");
//        integerTreeMapWithComparator.forEach((key,value)->IO.print(key+":"+value+" ")); IO.println();
        IO.print("First and last 10 elements: "); integerTreeMapWithComparator.entrySet().stream().limit(10).forEach(entry->IO.print(entry.getKey()+":"+entry.getValue()+" ")); IO.print(" ... "); integerTreeMapWithComparator.entrySet().stream().skip(integerTreeMap.size()-10).forEach(entry->IO.print(entry.getKey()+":"+entry.getValue()+" "));
        IO.println("\n"+integerTreeMapWithComparator.size()+" elements added !");
    }
    private static void proceedDeletionTest(Map<Integer, Integer> integerHashMap, Map<Integer, Integer> integerLinkedHashMap,
                                            Map<Integer, Integer> integerTreeMap){
        IO.println("For deleting around the same amount of unique elements from equal-sized maps:");
        Map<String, Long> timeResultMap = Map.ofEntries(
                Map.entry(integerHashMap.getClass().getTypeName(), printTimeToDeleteElements(integerHashMap)),
                Map.entry(integerLinkedHashMap.getClass().getTypeName(), printTimeToDeleteElements(integerLinkedHashMap)),
                Map.entry(integerTreeMap.getClass().getTypeName(), printTimeToDeleteElements(integerTreeMap))
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
    private static long printTimeToDeleteElements(Map<Integer, Integer> map){
        Map<Integer, Integer> tempMap = getClonedIntegerMap(map);
        IO.println("Deleting elements - by key - from a "+tempMap.getClass().getTypeName());
        IO.println("Set size before exclusions: "+tempMap.size());
        Instant initialInstant = Instant.now();
        Arrays.asList(getRandomIntegerDataAsArray(0,1000,1000000)).forEach(tempMap::remove);
        Instant finalInstant = Instant.now();
        IO.println("Set size after exclusions: "+tempMap.size());
        long timeConsumedInMillis = Duration.between(initialInstant, finalInstant).toMillis();
        IO.println("Time consumed: "+timeConsumedInMillis+" ms.");
        return timeConsumedInMillis;
    }
    @SuppressWarnings("Duplicates")
    private static void proceedInsertionReversedSortingTest(Map<Integer, Integer> integerHashMap, Map<Integer, Integer> integerLinkedHashMap,
                                                            Map<Integer, Integer> integerTreeMap){
        IO.println("For inserting non-unique elements (duplicates discarded) at any position and reversely sorting equal-sized maps:");
        Map<String, Long> timeResultMap = Map.ofEntries(
                Map.entry(integerHashMap.getClass().getTypeName(), printTimeForInsertionReversedSortingElements(integerHashMap)),
                Map.entry(integerLinkedHashMap.getClass().getTypeName(), printTimeForInsertionReversedSortingElements(integerLinkedHashMap)),
                Map.entry(integerTreeMap.getClass().getTypeName(), printTimeForInsertionReversedSortingElements(integerTreeMap))
        );
        List<Map.Entry<String, Long>> timeResultMapEntrySortedByValueList = timeResultMap.entrySet().stream().
                sorted(Map.Entry.comparingByValue()).toList();
        IO.println(timeResultMapEntrySortedByValueList.getFirst().getKey()+" ("+timeResultMapEntrySortedByValueList.getFirst().getValue()+
                " ms) is more efficient than "+timeResultMapEntrySortedByValueList.get(1).getKey()+" ("+
                timeResultMapEntrySortedByValueList.get(1).getValue()+" ms), which is more efficient than "+
                timeResultMapEntrySortedByValueList.get(2).getKey()+" ("+
                timeResultMapEntrySortedByValueList.get(2).getValue()+" ms) in deleting elements ! This does not reflect the efficiency of " +
                "insertion alone, but in combination with the creation of the list with the elements to be added, as well as the sorting " +
                "process !");
    }
    @SuppressWarnings("Duplicates")
    private static long printTimeForInsertionReversedSortingElements(Map<Integer, Integer> map){
        Map<Integer, Integer> tempMap = getClonedIntegerMap(map);
        IO.println("Inserting and reversely sorting elements - by key - at a "+tempMap.getClass().getTypeName());
        IO.println("Map size before insertion and reversed sorting: "+tempMap.size());
        Instant initialInstant = Instant.now();
        List<Integer> listWithElementsToBeAdded = Arrays.asList(getRandomIntegerDataAsArray(0,10000000, 10000));
        for(Integer element:listWithElementsToBeAdded){
            tempMap.put(listWithElementsToBeAdded.indexOf(element),element);
        }
//      From Map classes, only TreeMap and LinkedHashMap can be sorted directly (without Streams), returning a NavigableMap and a
//      SequencedMap respectively.
        if(tempMap instanceof TreeMap<Integer, Integer>){
            tempMap = ((TreeMap<Integer,Integer>) tempMap).reversed();
        } else if(tempMap instanceof LinkedHashMap<Integer,Integer>){
            tempMap = ((LinkedHashMap<Integer,Integer>) tempMap).reversed();
        } else {
//      In the case of HashMap, the order is not guaranteed, and there's no way to sort this data structure directly, thus we have to
//      send the data to a LinkedHashMap (through Constructors or Streams) and then sort the data with the desired order, which returns
//      a SequencedMap.
            tempMap = new LinkedHashMap<>(tempMap).reversed();
        }
        Instant finalInstant = Instant.now();
        IO.println("Map size after insertion and reversed sorting: "+tempMap.size());
        long timeConsumedInMillis = Duration.between(initialInstant, finalInstant).toMillis();
        IO.println("Time consumed: "+timeConsumedInMillis+" ms.");
        return timeConsumedInMillis;
    }
    @SuppressWarnings("Duplicates")
    private static void proceedAccessingTest(Map<Integer, Integer> integerHashMap, Map<Integer, Integer> integerLinkedHashMap,
                                             Map<Integer, Integer> integerTreeMap){
        IO.println("For accessing unique elements at random positions of equal-sized maps:");
        Map<String, Long> timeResultMap = Map.ofEntries(
                Map.entry(integerHashMap.getClass().getTypeName(), printTimeForAccessingElements(integerHashMap)),
                Map.entry(integerLinkedHashMap.getClass().getTypeName(), printTimeForAccessingElements(integerLinkedHashMap)),
                Map.entry(integerTreeMap.getClass().getTypeName(), printTimeForAccessingElements(integerTreeMap))
        );
        List<Map.Entry<String, Long>> timeResultMapEntrySortedByValueList = timeResultMap.entrySet().stream().
                sorted(Map.Entry.comparingByValue()).toList();
        IO.println(timeResultMapEntrySortedByValueList.getFirst().getKey()+" ("+timeResultMapEntrySortedByValueList.getFirst().getValue()+
                " ms) is more efficient than "+timeResultMapEntrySortedByValueList.get(1).getKey()+" ("+
                timeResultMapEntrySortedByValueList.get(1).getValue()+" ms), which is more efficient than "+
                timeResultMapEntrySortedByValueList.get(2).getKey()+" ("+
                timeResultMapEntrySortedByValueList.get(2).getValue()+" ms) in retrieving elements ! This does not reflect the efficiency " +
                "of accessing elements alone, but in combination with Streams filtering and comparing processes (between a map and a list - " +
                "keysToFindList, in this case) !");
    }
    @SuppressWarnings("Duplicates")
    private static long printTimeForAccessingElements(Map<Integer, Integer> map){
        final int MAX_NUMBER_OF_KEYS = 1000000;
        List<Integer> keysToFindList = Arrays.asList(getRandomIntegerDataAsArray(0,10000000, MAX_NUMBER_OF_KEYS));
        Map<Integer,Integer> tempMap = getClonedIntegerMap(map);
        IO.println("Looking for Map.Entries of "+keysToFindList.stream().distinct().count()+" unique keys (duplicates discarded) - from "+
                keysToFindList.size()+" originally and randomly requested - and the respective elements at a "+tempMap.getClass().
                getTypeName()+" of size "+tempMap.size()+".");
        Instant initialInstant = Instant.now();
        List<Map.Entry<Integer, Integer>> entryFoundList = tempMap.entrySet().stream().filter(x->keysToFindList.
                contains(x.getKey())).toList();
        Instant finalInstant = Instant.now();
        IO.println("From the "+keysToFindList.stream().distinct().count()+" unique random Map.Entries looked into the map, "+
                entryFoundList.size()+" was/were found:");
        IO.println(entryFoundList.isEmpty() ? "No elements to show !" : entryFoundList);
        long timeConsumedInMillis = Duration.between(initialInstant, finalInstant).toMillis();
        IO.println("Time consumed: "+timeConsumedInMillis+" ms.");
        return timeConsumedInMillis;
    }
    private static Map<Integer, Integer> getClonedIntegerMap(Map<Integer, Integer> map){
        return switch (map.getClass().getTypeName()) {
            case "java.util.HashMap" -> new HashMap<>(map);
            case "java.util.LinkedHashMap" -> new LinkedHashMap<>(map);
            case "java.util.TreeMap" -> new TreeMap<>(map);
            default -> throw new RuntimeException("Couldn't detect the type of instance of the Map for Benchmark testing !");
        };
    }
}

/*
This class basically instantiates and populates HashMap, LinkedHashMap and TreeMap instances with sample random Integer values,
and compares how efficient these classes are in performing CRUD operations, as: delete, insert, inversely sort and accessing
(querying) random Integer elements. It also shows the native order of elements when using each data structure.
*/
