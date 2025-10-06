package com.danielpm1982.regex;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyRegex {
    public static void execute(){
        Map<String, Pattern> patternMap = MyPatternSupplier.getPatternMap();
        Map<String, String> patternStringMap = MyPatternStringSupplier.getPatternString();
        for (Map.Entry<String, Pattern> entry : patternMap.entrySet()) {
            List<String> testingStringList = patternStringMap.keySet().stream().
                    filter(key -> key.contains(entry.getKey())).map(patternStringMap::get).toList();
            System.out.println("Validating " + testingStringList + " value(s) against regex pattern: " +
                    entry.getValue().toString() + " ...");
            for (String testingString : testingStringList) {
                Matcher matcher = entry.getValue().matcher(testingString);
                if (matcher.find()) {
                    System.out.println("RegEx pattern successfully matched with String: " + matcher.group());
                } else {
                    System.out.println("RegEx pattern failed to match String: " + testingString);
                }
            }
        }
    }
    void main(){
        execute();
    }
}

/*
This class execute() method obtains, from the two Supplier classes, the patternMap and the patternStringMap, containing,
respectively, the Patterns and Strings that are to be validated here, against each other, using the RegEx Java API.
For each entry from the patternMap, we use its String key to filter which entries from the patternStringMap should
be validated against the current Pattern: the comparing Strings must have as keys a String equal or derived from the
String keys of the Pattern Map. This way, the email Strings will only validate against the email Pattern, and so on.
After filtering the keys of the Strings to be validated against each current Pattern, we then get their value from the
patternStringMap and add to the testingStringList. This list contains all Strings to be validated against the current
iterating Pattern. Next, we iterate the testingStringList and, for each testingString, we validate that against the
current Pattern, through the matcher() method. For each current matcher, we then check if it has found or not the
Pattern at the validating String, showing a message to the user along with the validating String itself - if it has been
validated, we may use the matcher.group() to get the validated String, if not, we must use the testingString as above,
and not through the matcher.group(). The two most important classes here are the Pattern and Matcher classes. The first
stores the Pattern, the latter is used to store the result and metadata from the validation (between the testingString
and the Pattern), so that we can check if the validation succeeded and show the result (and the matched String) to the
user.
*/
