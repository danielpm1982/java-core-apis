package com.danielpm1982.collection;

public class MyCollection {
    public static void execute(){
        MyListBenchmarkTest.executeListBenchMarkTest();
        MySetBenchmarkTest.executeSetBenchMarkTest();
        MyMapBenchmarkTest.executeMapBenchMarkTest();
    }
    void main() {
        execute();
    }
}

/*
This class basically calls a demonstration of execution of List, Set and Map Interfaces' classes, in order to assay the
difference in performance between each of these Interface's classes. Example CRUD operations are tried, and the time of
execution, when used  each Collection type - as the base data structure, is measured. Important to notice that, as explicitly
mentioned at the comments inside each class, the execution times are not a direct measure of the time each class takes to
perform the CRUD operations alone, but all other operations involved as well, as creating the various data structures, populating
them, traversing the stream of data involved, performing Streams operations and so on. Therefore, even if any of the classes
tested has a low cost for a certain CRUD operation, it may happen that, for the used implementation, the time is eventually
measured with an additional overhead (because of other operations, not the CRUD ones), which would make that class seem
inefficient at that particular CRUD case. We'd have to use a very low-code level to do an actual benchmark with minimal interference
of operations other than the ones we intended to measure. The object here is more on exemplifying the Collections API syntax,
rather than calculating the accurate performance for CRUD operations when using such Collections classes. Having that said, it's
clear that the performance varies a lot between the different data structures tested here, and the developer should check out the
documentation to know better which Collection type would be more efficient in each case, and all the tradeoffs involved, specially
when manipulating big data.
For the List interface, ArrayList and LinkedList were exemplified.
For the Set interface, HashSet, LinkedHashSet and TreeSet were exemplified.
For the Map interface, HashMap, LinkedHashMap and TreeMap were exemplified.

More info at:
https://dev.java/learn/api/collections-framework
*/
