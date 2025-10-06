package com.danielpm1982.networking.webservice01;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyWebService01Client {
    private final static String WEB_SERVICE_URL_STRING = "https://viacep.com.br/ws/01001000/json/";
    public static void execute(){
        fetchExtractAndPrintMyAddress(StrategyEnum.OLD);
        fetchExtractAndPrintMyAddress(StrategyEnum.NEW);
    }
    @SuppressWarnings("Duplicates")
    private static void fetchExtractAndPrintMyAddress(StrategyEnum strategyEnum){
        try{
            URI uri = new URI(WEB_SERVICE_URL_STRING);
            Optional<String> myAddressJsonResponseStringOptional;
            IO.println("\nCalling Web Service at: "+WEB_SERVICE_URL_STRING);
            switch (strategyEnum){
                case OLD ->  {
                    IO.println("Old way -> using Scanner, IO InputStream and URL:");
                    myAddressJsonResponseStringOptional = callWebServiceTheOldWay(uri);
                }
                case NEW ->  {
                    IO.println("New way -> using HttpClient, HttpRequest, HttpResponse and URI - all those from Java Core java.net package " +
                            "and NOT from Java EE javax.servlet or jakarta.servlet packages:");
                    myAddressJsonResponseStringOptional = callWebServiceTheNewWay(uri);
                }
                default -> throw new RuntimeException("Strategy not recognized ! Value should be one of the following: "+
                        Stream.of(strategyEnum.getClass().getEnumConstants()).map(Enum::toString).collect(Collectors.
                                joining(", "))+" and NOT "+strategyEnum+" .");
            }
            if(myAddressJsonResponseStringOptional.isPresent()){
                IO.println("JSON (serialized) format:\n"+myAddressJsonResponseStringOptional.get());
                MyAddress myAddressDeserialized = deserializeFromJsonToMyAddress(myAddressJsonResponseStringOptional.get());
                if(myAddressDeserialized == null){
                    throw new RuntimeException("Deserialization from JSON returned a null instance of the target Java Object ! Could not " +
                            "deserialize JSON String ! ");
                }
                IO.println("Object (deserialized) format:");
                IO.println(myAddressDeserialized);
            } else{
                throw new RuntimeException("Invalid response from the Web Service at: "+WEB_SERVICE_URL_STRING+" ! No JSON response to " +
                        "deserialize and, therefore, no Java instance to have its content displayed !");
            }
        } catch (Exception e) {
            IO.println(e.getClass()+": "+e.getMessage());
        }
    }
    private static Optional<String> callWebServiceTheOldWay(URI uri) throws URISyntaxException, IOException {
        StringBuilder myAddressJsonResponseStringBuilder = new StringBuilder();
        try(Scanner myScanner = new Scanner(uri.toURL().openStream())){
            if(myScanner.hasNext()){
                while(myScanner.hasNext()){
                    myAddressJsonResponseStringBuilder.append(myScanner.nextLine().trim());
                }
                IO.println("Request successful !");
                return Optional.of(myAddressJsonResponseStringBuilder.toString());
            } else{
                IO.println("Request unsuccessful !");
                return Optional.empty();
            }
        }
    }
    @SuppressWarnings("Duplicates")
    private static Optional<String> callWebServiceTheNewWay(URI uri) throws IOException, URISyntaxException, InterruptedException {
        try(HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest httpRequest = HttpRequest.newBuilder(uri).build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            int statusResponseCode = httpResponse.statusCode();
            if (statusResponseCode == 200 && !httpResponse.body().isBlank()) {
                IO.println("Request successful ! HTTP status of response: "+statusResponseCode+" !");
                return Optional.of(httpResponse.body());
            } else {
                IO.println("Request unsuccessful ! HTTP status code is "+statusResponseCode+" !");
                return Optional.empty();
            }
        }
    }
    private static MyAddress deserializeFromJsonToMyAddress(String myAddressJsonString) throws JsonSyntaxException{
        Gson gson = new Gson();
        return gson.fromJson(myAddressJsonString, MyAddress.class);
    }
    private enum StrategyEnum{
        OLD,
        NEW
    }
}

/*
Exceptionally for this subproject, an external lib has been manually downloaded and added to the base path libs directory.
You have to manually add that "libs" directory to the project structure libraries, so that the Gson dependency is added to
the classpath of the module of this application, and then can be imported and used here. We're not using Maven or Gradle for
automating that.

This class shows two ways (OLD and NEW) of sending a request to a URL or URI Web Service, with the querying data at the URL / URI
itself (a ZIP Code value), and with no data at the request message body. If successful, the response of this particular Web Service
is a JSON String (with Address data corresponding to the ZIP Code value requested). Using Google Gson dependency, this JSON response
data is then deserialized into the corresponding MyAddress Java record, and the toString() result of this record is printed out. If
the deserialization results in a null Object, that is, if the deserialization fails, a RuntimeException is thrown with a custom message.
If, on the other hand, the Web Service doesn't return any data or the HTTP status differs from 200 OK, a different RuntimeException is
thrown with the corresponding message for that case. If any Exception is thrown at the execution of this class, it is caught, at the
end, and is printed out, both the type of the Exception as its message.

At this webservice01 package, we implement this CONSUMER class, which consumes the services of an external Web Service - the PRODUCER.
Instead, we can implement both the PRODUCER as the CONSUMER and deploy them either locally or at cloud. That's what we do at the
webservice02 package.

More at: https://www.geeksforgeeks.org/java/java-networking
*/
