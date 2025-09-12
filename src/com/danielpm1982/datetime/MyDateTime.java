package com.danielpm1982.datetime;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MyDateTime {
    public static void execute(){
        List<ZoneId> myZoneIdList = MyZoneIdListSupplier.getRandomZoneIdList(5);
        List<ZonedDateTime> myZonedDateTimeList = myZoneIdList.stream().map(ZonedDateTime::now).toList();
        List<DateTimeFormatter> myDateTimeFormatterList = MyDateTimeFormatterListSupplier.getDateTimeFormatterList();
        for(ZonedDateTime zonedDateTime : myZonedDateTimeList){
            myDateTimeFormatterList.forEach(myDateTimeFormatter ->
                    System.out.println(myDateTimeFormatter.format(zonedDateTime)));
            System.out.println();
        }
        LocalDateTime myLocalDateTime1 = LocalDateTime.parse("1900-01-31T12:30:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        checkIfLocalDateTimeIsGreaterThanOneCenturyFromNow(myLocalDateTime1);
        LocalDateTime myLocalDateTime2 = LocalDateTime.parse("1950-01-31T00:30:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        checkIfLocalDateTimeIsGreaterThanOneCenturyFromNow(myLocalDateTime2);
    }
    private static void checkIfLocalDateTimeIsGreaterThanOneCenturyFromNow(LocalDateTime localDateTime){
        String out = localDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).
                withZone(ZoneId.systemDefault()));
        if(localDateTime.isBefore(LocalDateTime.now().minus(1, ChronoUnit.CENTURIES))){
            System.out.println(out+" is more than 1 century old than the current date !");
        } else{
            System.out.println(out+" is less than 1 century old than the current date !");
        }
    }
}

/*
Through the execute() method, this class shows two common ways of working with the datetime API.

Firstly, it gets a ZoneId list, with 5 ZoneIds (size==5).
Then it uses the ZoneId list to get the current ZonedDateTime at each of those ZoneIds, and adds that to a list of
ZonedDateTimes.
It also gets a DateTimeFormatter list, with 6 DateTimeFormatter examples already configured.
Next, it iterates the ZonedDateTimeList, and, for each of the ZonedDateTime instances, it outputs the String
representations of those ZonedDateTimes using all supplied DateTimeFormatters. This way, we have the visualization
of each ZonedDateTime in 6 different ways.
The main method, in this case, is the format() method, from the DateTimeFormatter class, which outputs the String
representation of a certain Date, Time, LocalDateTime or ZonedDateTime, using the format configured at the formatter.

Secondly, and unrelated with the latter example, it is shown how to do the exact opposite: pass a String representation
as argument to a LocalDateTime instance, along with a specific DateTimeFormatter configuration, also passed as argument.
The main method, in this case, is the parse() method, from the LocalDateTime or ZonedDateTime classes.
It is then performed some data and time calculations, by using the checkIfLocalDateTimeIsGreaterThanOneCenturyFromNow()
method, in order to check if the parsed LocalDateTime has more or less than one century.

Many more examples of date and time comparisons and calculations can be demonstrated with this API, using a diversion
of operation methods, along with the ChronoUnit (TemporalUnit), Period, Epoch and Instant values we wish it to use.
*/
