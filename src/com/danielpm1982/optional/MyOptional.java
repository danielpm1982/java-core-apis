package com.danielpm1982.optional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class MyOptional {
    public static void execute(){
        List<MyItemRecord> itemList = new ArrayList<>();
        IntStream.range(1,6).forEach(i -> itemList.add(MyItemSupplier.getRandomItem("item_"+
                        new Random().nextInt(1000,5001)).
                        orElse(MyItemSupplier.getEmptyItem())));
        itemList.forEach(System.out::println);
    }
    void main(){
        execute();
    }
}

/*
This class has one single method which is meant to execute an example of how to use the MyItemSupplier and MyItemRecord.
Here, we simply create a List and add 5 items inside it, being these items instances of the MyItemRecord. The MyItemSupplier
methods are called to get an actual non-null instance of the record or the same type null representation of it, for later
showing all items at the console. For each item being added to the list, the MyItemSupplier getRandomItem() method will return
an Optional with either a non-null instance of the record or null inside it. The Optional API allow us to treat that
automatically and, if the item returned from the getRandomItem() method is not null, then we add it to the list, otherwise
we call the getEmptyItem() and add the returned empty representation of the same record, instead of null itself.
At all cases, we avoid dealing with null returns or null items directly... and use actual instances instead. At the Optional
API we have other methods for treating the Optional returns, other than the orElse() method. We can throw custom exceptions,
if we wish - instead of NullPointerException, or a variety of other strategies. The key thing is that both non-null and null
instances of a certain Object is returned wrapped inside an Optional, and then we can manually treat those returns, extracting
the wrapped Object instances or treating the null instances without risking throwing NullPointerException errors. The use of
the Stream and Random APIs is optional here, and not demanded by the Optional API.
*/
