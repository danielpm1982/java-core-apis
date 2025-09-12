package com.danielpm1982.datetime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class MyZoneIdListSupplier {
    protected static List<ZoneId> getRandomZoneIdList(int size) {
        List<ZoneId> zoneIdList = new ArrayList<>();
        IntStream.range(1,size+1).forEach(i-> zoneIdList.add(getRandomZoneId()));
        return zoneIdList;
    };
    private static ZoneId getRandomZoneId(){
        List<String> zoneIdStringList = new ArrayList<>(ZoneId.getAvailableZoneIds());
        Collections.shuffle(zoneIdStringList);
        return ZoneId.of(zoneIdStringList.stream().findAny().orElse(ZoneId.systemDefault().getId()));
    }
}

/*
Through the method getRandomZoneIdList(), this class returns a List of ZoneIds , obtained randomly from all
available ZoneIds at the DateTime API itself. The caller should pass the size of the list he wants, i.e.,
the number of ZoneIds at the supplied list. Having a ZonedId allows the user to get the current ZonedDatetime
at that respective ZoneId.
*/
