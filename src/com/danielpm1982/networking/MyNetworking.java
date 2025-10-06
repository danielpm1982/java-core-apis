package com.danielpm1982.networking;
import com.danielpm1982.networking.webservice01.MyWebService01Client;
import com.danielpm1982.networking.webservice02.MyHttpServer;
import com.danielpm1982.networking.webservice02.MyWebService02Client;
import com.danielpm1982.networking.webservice03.MyHttpSocketServer;
import com.danielpm1982.networking.webservice03.MyWebService03SocketClient;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MyNetworking {
    public static void execute(){
        // executing webservice01 client app (remote server)
        IO.println("\n****** WEB SERVICE 01: ******");
        MyWebService01Client.execute();
        // executing webservice02 local web server and, then, the also local, client app
        IO.println("\n****** WEB SERVICE 02: ******");
        try {
            MyHttpServer.startServer();
            TimeUnit.SECONDS.sleep(1);
            MyWebService02Client.execute();
        } catch (IOException | InterruptedException e) {
            IO.println(e.getMessage());
        } finally {
            MyHttpServer.closeServer();
        }
        // executing webservice03 local web server and, then, the also local, client app
        IO.println("\n****** WEB SERVICE 03: ******\n");
        Thread server03Thread = new Thread(MyHttpSocketServer::startServer);
//        server03Thread.setDaemon(true);
        server03Thread.start();
        try {
            TimeUnit.SECONDS.sleep(1);
            MyWebService03SocketClient.execute();
//            MyWebService03SocketClient.execute();
//            MyWebService03SocketClient.execute();
        } catch (InterruptedException e) {
            IO.println(e.getMessage());
        }
    }
    // executing all webservices at once
    void main(){
        execute();
    }
}

/*
Within this subproject, we have actually three independent projects, inside webservice01, webservice02 and webservice03
packages.

At webservice01, it is shown how to use old and new approaches to create a Client Consumer app, in order to consume
external REST Web Services - an Address web service available at https://viacep.com.br/ws/{zipCode}/json/ . The input
is the zipCode, passed as a path variable, at the request URL itself, that is used to fetch complete Address info
regarding that zipCode exact place.

At webservice02, it is shown how to both create a REST Web Service Producer Http Server, and a Client Consumer app, in
order to consume the Web Services, while the local server is up and running. Both for creating the Producer Web Server as
for creating the Consumer Client App, no jakarta.servlet API classes were used, as well as no JEE third-party web servers,
servlet containers, application servers or web development frameworks.

At webservice03, we used an even lower level code - the Socket API - to create another Http Server, establish a connection
between a Client app and that Server, and exchange some messages between them, both ways. When using Sockets we have to
manage threads manually, differently from when using higher level APIs, as in the case of the other two webservices above.

As said, we implemented this subproject only by using Java Core APIs alone, specifically the java.net and com.sun.net core
packages (JAVA JDK 25).

Of course, in a real project, we should use both a good web | application server, as TomCat or JBoss, and a web development
framework, as Spring or Quarkus... in order to boost our productivity and reuse lots of complex-solution packages already
available for us to simply configure, integrate and deploy, along with our own business implemented code. The intent here
is simply to prove that only with the Java Core libs, with no external packages, we can do the basics regarding Networking.
*/
