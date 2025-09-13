package com.danielpm1982.datetime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyDateTimeFormatterListSupplier {
    protected static List<DateTimeFormatter> getDateTimeFormatterList(){
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss VV");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z");
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("E, MMM dd, yyyy hh:mm:ss a O");
        DateTimeFormatter formatter4 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.FULL);
        DateTimeFormatter formatter5 = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        DateTimeFormatter formatter6 = DateTimeFormatter.RFC_1123_DATE_TIME;
        return new ArrayList<>(Arrays.asList(formatter1, formatter2, formatter3, formatter4, formatter5, formatter6));
    }
}

/*
Through the method getDateTimeFormatterList(), this class returns a list of DateTimeFormatters, already configured,
that can be used to display one or more ZonedDateTime instances at that specific String format.
If the formatter is used along with the format() method (from the DateTimeFormatter class), it outputs the String
representation of a certain DateTime.
If the formatter, otherwise, is used with the parse() method (from the LocalDateTime or ZonedDateTime classes), it
inputs a String, at that specific format representation, into the LocalDateTime or ZonedDateTime. So the formatter
works both ways, either outputting a date and/or time as a String or inputting a String into a date and/or time.
*/
