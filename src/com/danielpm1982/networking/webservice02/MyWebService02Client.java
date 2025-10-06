package com.danielpm1982.networking.webservice02;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class MyWebService02Client {
    private static final int SERVER_PORT = 8000;
//    private static final String WEB_SERVICE_URL = WebServiceUrlEnum.ROOT_URL_STRING.getUrl();
    private static final String WEB_SERVICE_URL = WebServiceUrlEnum.ALL_ELEMENTS_URL_STRING.getUrl();
//    private static final String WEB_SERVICE_URL = WebServiceUrlEnum.SAMPLE_ELEMENT_URL_STRING.getUrl();
    public static void execute(){
        fetchExtractAndPrintMyAddress();
    }
    @SuppressWarnings("Duplicates")
    private static void fetchExtractAndPrintMyAddress(){
        try{
            URI uri = new URI(WEB_SERVICE_URL);
            IO.println("\nCalling Web Service at: "+WEB_SERVICE_URL);
            Optional<String> myAddressJsonResponseStringOptional = callWebServiceTheNewWay(uri);
            if(myAddressJsonResponseStringOptional.isPresent()){
                String serializedEntity = myAddressJsonResponseStringOptional.get();
                IO.println("JSON (serialized) format:\n"+serializedEntity);
                // trying to deserialize the JSON serializedEntity into a valid class: String, MyAddress or Map<String,MyAddress>:
                // At the switch below we'd better compare the enum values directly - which are compile-time constants, and not their
                // associated url values... which also are compile-time constants, but only if we hard coded them redundantly. We
                // can't use the getUrl() method, or any method, as labels in a switch, 'cause they're not compile-time constants. So,
                // we first got the WEB_SERVICE_URL_ENUM for the WEB_SERVICE_URL (through the enum fromUrl() method), and then compared
                // this WEB_SERVICE_URL_ENUM value with each possible enum value from the enum:
                WebServiceUrlEnum WEB_SERVICE_URL_ENUM = WebServiceUrlEnum.fromUrl(WEB_SERVICE_URL);
                if(WEB_SERVICE_URL_ENUM != null) {
                    switch (WEB_SERVICE_URL_ENUM) {
                        case WebServiceUrlEnum.ROOT_URL_STRING -> {
                            String deserializedFromJsonToString = deserializeFromJsonToString(serializedEntity);
                            IO.println("JSON (deserialized) format - " + deserializedFromJsonToString.getClass().getTypeName() + ":\n" + deserializedFromJsonToString);
                        }
                        case WebServiceUrlEnum.ALL_ELEMENTS_URL_STRING -> {
                            Map<String, MyAddress> deserializedFromJsonToMyAddressMap = deserializeFromJsonToMyAddressMap(serializedEntity);
                            IO.println("JSON (deserialized) format - " + deserializedFromJsonToMyAddressMap.getClass().getTypeName() + ":");
                            deserializedFromJsonToMyAddressMap.forEach((k, v) -> IO.println(k + ": " + v));
                        }
                        case WebServiceUrlEnum.SAMPLE_ELEMENT_URL_STRING -> {
                            MyAddress deserializedFromJsonToMyAddress = deserializeFromJsonToMyAddress(serializedEntity);
                            IO.println("JSON (deserialized) format - " + deserializedFromJsonToMyAddress.getClass().getTypeName() + ":\n" + deserializedFromJsonToMyAddress);
                        }
                        default ->
                                IO.println("JSON (deserialized) format:\n" + "Invalid target Java Object format after deserialization !");
                    }
                } else{
                    throw new RuntimeException("Cannot identify the enum constant value associated with the passed url ! ");
                }
            } else{
                throw new RuntimeException("Invalid response from the Web Service at: "+WEB_SERVICE_URL+" ! No JSON response and, " +
                        "therefore, no Java instance to deserialize to or have its content displayed !");
            }
        } catch (Exception e) {
            IO.println(e.getClass()+": "+e.getMessage());
        }
    }
    @SuppressWarnings("Duplicates")
    private static Optional<String> callWebServiceTheNewWay(URI uri) throws IOException, InterruptedException {
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
    // in the case of primitives or simple Object types, as String, you don't need to use reflective TypeTokens for making
    // Gson deserialize to the correct type
    private static String deserializeFromJsonToString(String jsonStringToDeserialize){
        Gson gson = new Gson();
        return gson.fromJson(jsonStringToDeserialize, String.class);
    }
    // in the case of Generic Object types, as in Optional<MyAddress>, you MUST use reflective TypeTokens, for forcing
    // Gson deserialize to the correct type - MyAddress, in this case. Otherwise, it will deserialize to LinkedTreeMap,
    // if it doesn't identify the original class type you serialized the JSON from.
    private static MyAddress deserializeFromJsonToMyAddress(String jsonStringToDeserialize){
        Gson gson = new Gson();
        Type myAddressType = new TypeToken<MyAddress>(){}.getType();
        return gson.fromJson(jsonStringToDeserialize, myAddressType);
    }
    // in the case of complex nested Collection types, including when using Generics, as in Map<String, MyAddress>, you MUST use
    // reflective TypeTokens, for forcing Gson deserialize to the correct type - Map<String,MyAddress>, in this case. Otherwise,
    // it will deserialize to LinkedTreeMap, if it doesn't identify the original class type you serialized the JSON from.
    private static Map<String,MyAddress> deserializeFromJsonToMyAddressMap(String jsonStringToDeserialize){
        Gson gson = new Gson();
        Type myAddressMapType = new TypeToken<LinkedHashMap<String,MyAddress>>(){}.getType();
        return gson.fromJson(jsonStringToDeserialize, myAddressMapType);
    }
    private enum WebServiceUrlEnum{
        ROOT_URL_STRING("http://localhost:"+SERVER_PORT+"/"),
        ALL_ELEMENTS_URL_STRING("http://localhost:"+SERVER_PORT+"/addresses/"),
        SAMPLE_ELEMENT_URL_STRING("http://localhost:"+SERVER_PORT+"/addresses/10001000");
        private final String url;
        WebServiceUrlEnum(String url){
            this.url = url;
        }
        public String getUrl() {
            return url;
        }
        @Override
        public String toString() {
            return getUrl();
        }
        // Helper method for reverse lookup by URL string (returns the enum that corresponds to the URL)
        public static WebServiceUrlEnum fromUrl(String url) {
            for (WebServiceUrlEnum e : WebServiceUrlEnum.values()) {
                if (e.url.equals(url))
                    return e;
            }
            return null;
        }
    }
}

/*
This is a Client Consumer Class, that consumes the web services produced by the MyHttpServer. Before running this client class, or
consuming the same web services from a browser, MyHttpServer must have been already initialized and be UP for receiving HTTP calls.
Using the new java.net.http package classes, here we create an HttpClient and build an HttpRequest for the endpoint URI to be called,
based on the WEB_SERVICE_URL, set at the beginning of the class - there, you may uncomment the declaration that sets the endpoint URL
you wish to test, from the three available. Then the HttpClient is used to send that HttpRequest, along with the HttpResponse.BodyHandlers
type setting, which prepares the HttpResponse to receive a body type String. This is the String that is received, at the response body,
as a JSON serialized format, from the HttpServer. If the response to the request returns an Http status 200 and a non-blank body (with the
JSON response in it), then it returns an Optional to the caller, who prints that JSON string to the console and sends it for deserialization,
later printing again, the same data, as a deserialized Java object, which could be manipulated as one wishes.
The hardest part here regards to the deserialization when using GSon. When Generics and/or complex Collection types are used, at the
server, to serialize JSON documents from, when it's time those JSON documents are deserialized, at the Client app, GSon just can't
infer correctly what Java types those JSON documents actually came from. Any time that happens, instead of deserializing to the correct
original Java object, it'll use a LinkedTreeMap to store the data, either as a single element with a list of its properties inside the
LinkedTreeMap, or, if there are multiple elements, a list of those elements' mappings inside the LinkedTreeMap. Either way, the
deserialization would be wrong, as we want the data to be stored inside the original data structures used before serialization, or
even new different structures... but not necessarily inside a LinkedTreeMap. After hours of maximum-patience research, I was able to
finally realize that, for those cases, we have to use com.google.gson.reflect.TypeToken, in order to set the java.lang.reflect.Type
of the target class that we want GSon to use when deserializing the JSON document data. See the code above, at this class.
Observe that here, at this webservice02 subpackage project, we create both the producer server (HttpServer) as the client app (HttpClient)
for our customized web service. Differently from the webservice01 subpackage project, where we only created the client app (HttpClient)
for an external preexistent producer server (HttpServer). Both strategies are equally important to be learnt, as we may need to create our
own web services server, but also we end up using a lot of external web services and microservices, at third-party servers, as well.
Regarding the enum (WebServiceUrlEnum), it associates a constant enum value with each available endpoint URL. Although the getUrl() method
can generally be used to get the corresponding URL from the enum constant, in the case of switch labels, it can't, as only compile-time
constants can be used as switch labels, and not methods. THerefore, if we use the WEB_SERVICE_URL at the switch, we'd have to use the urls,
as well, as the labels, but how would we get them from the enum without using getUrl() method ? We'd have to hardcode each full URL as
label (and that's redundant as they're already at the enum). That's why it's better to switch, instead of the WEB_SERVICE_URL, the
WEB_SERVICE_URL_ENUM, that is, the enum value constant that corresponds to the WEB_SERVICE_URL. As such, we can use, as labels, not the URL
of each endpoint, but the enum constants themselves, which are pretty ok to be used as labels and be compared with the switch constant. For
getting the enum constant value from the correspondent URL, we created the static method fromUrl(), inside the enum, which can be used prior
to initiating the switch... so that we switch the enum constant value and not its URL.
*/
