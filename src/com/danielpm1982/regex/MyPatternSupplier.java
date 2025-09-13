package com.danielpm1982.regex;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MyPatternSupplier {
    private static final String emailRegex = "^[^@ ]+@[^@.]+\\.\\w+";
    private static final String strongPasswordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,16}";
    protected static Map<String, Pattern> getPatternMap(){
        Pattern emailPattern = Pattern.compile(emailRegex);
        Pattern strongPasswordPattern = Pattern.compile(strongPasswordRegex);
        Map<String, Pattern> patternMap = new HashMap<>();
        patternMap.put("emailPattern", emailPattern);
        patternMap.put("strongPasswordPattern", strongPasswordPattern);
        return patternMap;
    }
}

/*
https://www.freecodecamp.org/news/practical-regex-guide-with-real-life-examples
https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
*/

/*
This class and its getPatternString() method are responsible for defining some sample regEx Strings, compile them into
the respective Pattern objects and return these in a pattern Map, of type Map<String, Pattern>. The String keys
of this Pattern Map relates to (are included at) the String keys of the other Map, at MyPatternStringSupplier class.
The corresponding values, that is, the Pattern objects of this class Map and the String values of the
MyPatternStringSupplier class Map are validated at the MyRegex class. Any String key name at the Map of this class must
"be included" at the String name of keys at MyPatternStringSupplier class Map, whose values we wish to validate. So
that we validate the String values there with the correct and related Patterns defined here. For example, the
"emailPattern" key String of the Pattern Map here must be included at one or more String key name of the Map at
the MyPatternStringSupplier class, in order that we can compare with the emailPattern only the entries' values from the Map
at the MyPatternStringSupplier class, that are actually email entries, and not strong Password entries, for instance.
The most important class here is the Pattern class, through whose method compile() we can store the String regEx we wish
that Pattern object to be set with, and later use it to validate any String, by using the Pattern matcher() method.
*/
