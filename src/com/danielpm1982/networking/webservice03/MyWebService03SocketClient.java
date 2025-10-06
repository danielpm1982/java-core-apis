package com.danielpm1982.networking.webservice03;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MyWebService03SocketClient {
    private static final int SERVER_PORT = 8000;
    private static final String DOMAIN_NAME = "localhost";
    public static void execute(){
        final UUID CLIENT_ID = UUID.randomUUID();
        IO.println("CLIENT "+CLIENT_ID+": Establishing a connection to the Server at: http://"+DOMAIN_NAME+":"+SERVER_PORT+"/ ...");
        try(
                Socket clientSocket = new Socket(DOMAIN_NAME, SERVER_PORT);
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream())
        ){
            IO.println("CLIENT "+CLIENT_ID+": Connection established ! ");
            IO.println("CLIENT "+CLIENT_ID+": Sending request message to server at "+clientSocket.getRemoteSocketAddress()+" !");
            TimeUnit.SECONDS.sleep(3);
            dos.writeUTF("Hello World from Client "+CLIENT_ID+" !");
            IO.println("CLIENT "+CLIENT_ID+": Message sent !");
            IO.println("CLIENT "+CLIENT_ID+": Waiting for server response...");
            String response = dis.readUTF();
            if(response.isBlank()){
                IO.println("CLIENT "+CLIENT_ID+": Server response is empty or did not respond after waiting time !");
            } else{
                IO.println("CLIENT "+CLIENT_ID+": Server response message: \""+response+"\"");
            }
        } catch (Exception e){
            IO.println("CLIENT "+CLIENT_ID+": Error at Client side ! "+e);
        } finally {
            IO.println("CLIENT "+CLIENT_ID+": Connection terminated !");
        }
    }
    void main(){
        execute();
    }
}

/*
This class represents a Consumer Client for the webservice03 Producer Server. It basically establishes a connection to the Server,
by using the Server DOMAIN_NAME (or IP) and the SERVER_PORT. After the connection is formed, this class sends a sample message to
the server and then receives another message as response. We basically use only the Socket class for that (at the Client side). The
Sockets API is part of Java Core API, differently from JEE (Jakarta) Servlet APIs. With this Socket instance, we not only can get
information regarding the connection (for both sides - Server and Client), but we can send and receive the messages to (OutputStream)
and from (InputStream) the Server. Local, here, means the Client, while remote means the Server. At least two threads are involved in
that networking communication: one for the Server and another for the Client. Here we do it in a local single machine (localhost), but
we could do it in different machines, as long as we knew the correct IPs of both, at a common shared network.
*/
