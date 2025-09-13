package com.danielpm1982.regex;
import java.util.HashMap;
import java.util.Map;

public class MyPatternStringSupplier {
    private static final String emailString1 = "A@B.C";
    private static final String emailString2 = "AB.1";
    private static final String strongPasswordString1 = "123456aA";
    private static final String strongPasswordString2 = "123456AA";
    protected static Map<String, String> getPatternString(){
        Map<String, String> patternStringMap = new HashMap<>();
        patternStringMap.put("emailPattern1String", emailString1);
        patternStringMap.put("emailPattern2String", emailString2);
        patternStringMap.put("strongPasswordPattern1String", strongPasswordString1);
        patternStringMap.put("strongPasswordPattern2String", strongPasswordString2);
        return patternStringMap;
    }
}

/*
https://www.freecodecamp.org/news/practical-regex-guide-with-real-life-examples
*/

/*
This class and its getPatternString() method are responsible for defining Strings to be validated against the
corresponding Patterns defined at the MyPatternSupplier class, and return them into a Map, of type Map<String, String>.
The relationship between the entries of the Maps at both classes is maintained through the premise that each String key
name at the Pattern Map, at the MyPatternSupplier class, is included at one or more String key names at the Map of this
class, which, in turn, maps the Strings we wish to validate with each exact Pattern. This relationship is used at the
MyRegex class to ensure that the Strings here are compared only with the corresponding Patterns. For example, the keys
"emailPattern1String" and "emailPattern2String", here, that have as values "A@B.C" and "AB.1", respectively, contain the
"emailPattern" String. This "emailPattern" is the String key name of the email Pattern at the Map from the MyPatternSupplier
class, and, therefore, these two Strings will only be validated against the email Pattern and not against other Patterns
defined there. It this class, there are no special classes from the Regex API of Java, as we here only create and return
a Map of Strings to be validated, later, against some patterns.
*/
