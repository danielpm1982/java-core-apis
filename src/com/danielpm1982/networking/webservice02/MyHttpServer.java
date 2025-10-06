package com.danielpm1982.networking.webservice02;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class MyHttpServer {
    private static final String DOMAIN_NAME = "localhost";
    private static final int SERVER_PORT = 8000;
    private static HttpServer httpServer;
    public static void startServer() throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
        httpServer.setExecutor(null);
        httpServer.createContext("/", MyHttpServer::configureHttpHandlerForRootContextPath);
        httpServer.createContext("/addresses/", exchange ->
                MyHttpServer.configureHttpHandlerForFetchingAddressByZipCode(exchange,""));
        MyAddressDB.getMyAddressDBMap().keySet().forEach(zipString->
                httpServer.createContext("/addresses/"+zipString, exchange ->
                        MyHttpServer.configureHttpHandlerForFetchingAddressByZipCode(exchange, zipString))
                );
        IO.println("\nStarting Web Server at: http://"+DOMAIN_NAME+":"+SERVER_PORT+"/ ...");
        httpServer.start();
        IO.println("Server started and running at: "+httpServer.getAddress());
    }
    private static void configureHttpHandlerForRootContextPath(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
        }
        String responseString = "Hello World ! This is the root path of this web application ! Access /addresses/{zipCode} for getting " +
                "an Address or a list of available Addresses (if no zipCode is passed) !";
        String responseJsonString = serializeFromObjectToJson(responseString);
        writeResponseBytesToResponseBodyOutputStream(exchange, responseJsonString);
    }
    private static void configureHttpHandlerForFetchingAddressByZipCode(HttpExchange exchange, String zipCodeToFetch) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
        }
        // search for the MyAddress instance at the DB by the passed zipCode, returning the found instance or an empty instance, if not found:
        MyAddress myFetchedAddress = MyAddressDB.getMyAddressByZipCode(zipCodeToFetch).orElse(new MyAddress());
        String responseJsonString;
        // if the zipCode does not correspond to any existing MyAddress instance at the DB - if the instance returned above is empty -
        // serialize the whole map of MyAddress instances, with their respective zipCode keys, and write it back at the response body:
        if(myFetchedAddress.isEmpty()) {
            responseJsonString = serializeFromObjectToJson(MyAddressDB.getMyAddressDBMap());
        // if the zipCode corresponds to an existing MyAddress instance, have that single instance serialized and written back at the
        // response body:
        } else{
            responseJsonString = serializeFromObjectToJson(myFetchedAddress);
        }
        writeResponseBytesToResponseBodyOutputStream(exchange, responseJsonString);
    }
    private static String serializeFromObjectToJson(Object objectToSerialize) {
        Gson gson = new Gson();
        return gson.toJson(objectToSerialize);
    }
    private static void writeResponseBytesToResponseBodyOutputStream(HttpExchange exchange, String responseJsonString) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.add("Content-type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(200, responseJsonString.length());
        try (OutputStream responseBodyOutputStream = exchange.getResponseBody()) {
            responseBodyOutputStream.write(responseJsonString.getBytes());
        }
    }
    public static void closeServer() {
        httpServer.stop(60);
    }
}

/*
This class creates an HttpServer, a Producer web service app, setting a custom server port at instantiation. A default Executor is
set by setting null at the setExecutor() method. The HttpServer HttpContexts are then specified, by setting the Path for each endpoint
of this web service, associating it with its respective HttpHandler. The handler is a functional interface that receives the HttpExchange
as argument and returns void, setting the responseHeaders and responseBody of the response, which is returned to the user, as soon as the
handler is triggered by the event of the user accessing the web service endpoint through a browser, or, programmatically, through a Client
Consumer app (e.g. MyWebService02Client class), or from the console, using Http tools as curl or Postman. The body content of the response
is serialized into JSON, at the HttpServer. And, at the Consumer, it must be deserialized into the appropriate Object, whatever programming
language the Consumer app is written in. In the case of this project, we created a Consumer app in Java (could be any other language), which
consumes the services from the specified endpoints. When the HttpServer is started (up), we can either send GET requests through the browser
(and have the resulting browser view show the MyAddress entity as JSON), or we can use a Consumer app for sending the same GET request to
the service and manipulating the response, programmatically, as needed. As said above, you can also use terminal commands, as curl:
curl -v -H "Content-Type:application/json" -X GET "http://localhost:8000/addresses/10001000 . You can even use telnet app to open server
connections and write your own requests manually, by working directly at the Application layer of the Network TCP/IP model, and write your
requests according to the protocols themselves, without further abstractions.
Regarding this web service inputs, the endpoints of such project solely use path variables, and not query parameters. The input is basically
an existing {zipCode}. If no valid zipCode input is passed at the endpoint URL, as a path variable, then all instances will be returned, in a
Map, serialized as JSON. If a valid zipCode is passed, only that specific MyAddress instance is returned, serialized as JSON.

Available endpoints for this project (if running locally, use localhost as the domain, not forgetting the port 8080):
HTTP GET /
HTTP GET /addresses/
HTTP GET /addresses/{zipCode}
*/
