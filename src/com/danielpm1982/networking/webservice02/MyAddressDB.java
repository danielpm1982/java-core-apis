package com.danielpm1982.networking.webservice02;
import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

public class MyAddressDB implements Serializable {
    private static final Map<String, MyAddress> myAddressDBMap = Map.ofEntries(
            Map.entry("10001000", new MyAddress("10001-000", "XStreet", "100",
                    "house","XNeighbourhood", "XCity", "999", "XState", "XCountry")),
            Map.entry("20002000", new MyAddress("20002-000", "YStreet", "500",
                    "house","YNeighbourhood", "YCity", "55", "YState", "YCountry")),
            Map.entry("30003000", new MyAddress("30003-000", "ZStreet", "5000",
                    "house","ZNeighbourhood", "ZCity", "333", "ZState", "ZCountry"))
    );
    public static Map<String, MyAddress> getMyAddressDBMap() {
        return myAddressDBMap;
    }
    public static Optional<MyAddress> getMyAddressByZipCode(String zipCodeToFetch) {
        if(myAddressDBMap.containsKey(zipCodeToFetch)) {
            return Optional.of(myAddressDBMap.get(zipCodeToFetch));
        } else{
            return Optional.empty();
        }
    }
}

/*
This simulates a "DB" of MyAddress data, with instances of some MyAddress examples. The "DB" is a Map, whose key is the zipCode (without
the hyphen) and the value is the MyAddress instance corresponding to that zipCode. The Map here is an unmodifiable one (can't have any
elements added or removed). Unmodifiable instances are wrappers to modifiable instances, and, although the external unmodifiable reference
can't be modified, if we had a reference to the internal modifiable one, we could change the values of the internal instance, which would
also be reflected at the external wrapper instance. Here we don't have a reference to the internal instance, so the external wrapper one
is effectively immutable as well. Even at unmodifiable Collections, its elements, if not immutable, could have its data modified (even
without the element being able to be added or removed). For that case, we should always use immutable element types, in combination with
immutable Collections. Having that set, and protected, we thus can return to the user the reference to any immutable element or to any
immutable Collection with immutable elements. Both would be exclusively read-only (and thread-safe) and would not pose any risk in terms
of safety of the data.

This class either returns the whole map with all DB instances (getMyAddressDBMap()) or returns only the single MyAddress element when
fetched by the zipCode (getMyAddressByZipCode()).
*/
