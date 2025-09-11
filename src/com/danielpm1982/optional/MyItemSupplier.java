package com.danielpm1982.optional;
import java.util.Optional;
import java.util.Random;

public class MyItemSupplier {
    protected static Optional<MyItemRecord> getRandomItem(String itemName){
        if(returnNotEmpty()){
            return Optional.of(new MyItemRecord(itemName));
        }
        return Optional.empty();
    }
    private static boolean returnNotEmpty(){
        int randomInt = new Random().nextInt(1,100);
        return randomInt % 2 == 0;
    }
    protected static MyItemRecord getEmptyItem(){
        return new MyItemRecord("non-existent item !");
    }
}

/*
Based on a random chance, set at the returnNotEmpty() method, this class will statically return an instance of the
MyItemRecord or a null value, when having its getRandomItem() method called. Null values, if returned directly, may
throw NullPointerException errors at the calling classes (objects). Optional wrappers were created exactly for such cases.
Instead of returning either the record instance or null, we may safely return either one, but wrapped inside the Optional.
The Optional class (object) offers useful methods for the calling classes (objects) to deal with non-null and null
returns for the objects wrapped inside (MyItemRecord, in this case), with no possibility of throwing NullPointerException
errors, as, even for the wrapped null records, these would be returned inside the Optional object, which is an actual
instance and thus not a null value. The caller can then, in a controlled fashion, extract from the Optional the actual
record instance or treat the null value, if it is the case, by using the Optional API methods. The last method of this
class always returns the same empty structure of the MyItemRecord, not wrapped by the Optional object. This method is
useful when the user is manually treating the return of the getRandomItem() method, the wrapped item is null, and,
nonetheless, he wishes to add a null representation of the same MyItemRecord type to a List<MyItemRecord>.
*/
