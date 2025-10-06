package com.danielpm1982.networking.webservice03;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.TimeUnit;

public class MyHttpSocketServer implements Runnable {
    private static boolean isOpenForConnections;
    private static final int SERVER_PORT = 8000;
    private static final String DOMAIN_NAME = "localhost";
    public static void startServer() {
        isOpenForConnections = true;
        while(isOpenForConnections && !Thread.currentThread().isInterrupted()) {
            synchronized (MyHttpSocketServer.class) {
                IO.println("SERVER: Server starting at: " + "http://" + DOMAIN_NAME + ":" + SERVER_PORT + "/ ...");
                IO.println("SERVER: Waiting for a Client to establish a connection...");
                try (
                        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
                        Socket clientSocket = serverSocket.accept();
                        DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream())
                ){
                    IO.println("SERVER: Connection established ! ");
                    IO.println("SERVER: Client address " + clientSocket.getRemoteSocketAddress() + " !");
                    String inputString = dis.readUTF();
                    IO.println("SERVER: Client message received: \"" + inputString + "\"");
                    IO.println("SERVER: Writing and sending server response message back to the Client...");
                    TimeUnit.SECONDS.sleep(3);
                    dos.writeUTF("Hello World from Server ! We've received your message at:\n"+
                            ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)) +
                            ", dear Client !");
                    IO.println("SERVER: Message sent !");
                } catch (Exception e) {
                    IO.println("SERVER: Error at Server side ! " + e);
                } finally {
                    IO.println("SERVER: Client disconnected !");
                    closeServer();
                }
            }

        }
    }
    private static void closeServer() {
        isOpenForConnections = false;
        Thread.currentThread().interrupt();
    }
    void main(){
        startServer();
    }
    @Override
    public void run() {
        startServer();
    }
}

/*
As a lower-level code example, with no further dependencies other than the Java Core APIs alone, this class implements another
Web Server Producer app, and a Consumer app for connecting and changing messages with the former. Here we use the lowest-level
Java Core API possible, which is the Socket API (ServerSocket and Socket classes), from the java.net package. We also use Java IO
old API, for managing the low-level arguments, with the data streamed, between the Socket TCP/IP connection endpoints.
The ServerSocket, through the accept() method, turns available a thread, in waiting of a Client to connect, at the established
SERVER_PORT. As soon as any Client connects (at that Server IP and Port), the connection is established between the two. InputStream
and OutputStream instances are created for receiving and sending messages at the Server side - from and to the Client side, respectively.
These streams are wrapped into a DataInputStream and DataOutputStream, in order that we can read and write the messages as String,
rather than as byte arrays. Also, the terms local and remote change their meaning at the Server and at the Client. At the Server,
local refers to the Server side, and remote refers to the Client side; while, at the Client class, local means the Client side and
remote refers to the Server side. The Client side class is the Socket class, which is used, including at the Server side, to obtain
information about the connection, as for receiving the inputs from the Client and sending the outputs to the Client. The Server specific
Socket API class is the ServerSocket, which basically opens the Server for new connections. For keeping the Server alive, after dealing
with a certain Client request, and not letting the server-side script terminates, we used an infinite loop here, which only terminates
if we call closeServer(), or shutdown the execution of the Server console thread with Ctrl+C. The client-side execution thread, though,
will terminate as soon as its scripts ends, and will liberate the connection for another eventual Client to connect.

The business case dealt here is simply the following:
1 - the Client establishes a Socket connection with the Server, who was already awaiting a Client to connect;
2 - as soon as the connection actually establishes, this Server class gets the input data sent from the Client, and shows it at the
console;
3 - the Server then responds to the Client, with another message, telling the Client that his message was received, and the datetime
of that event;
4 - if we comment out the "closeServer();" line at the finally block above, and after the Client script terminates and the Client connection 
to this Server closes, the ServerSocket detects that and loops, for letting the connection available again for new Clients to connect, and 
waits until it happens or until we interrupt this Server thread, by calling interrupt() on it or killing the process at the OS. If you wanna
test this feature just comment out that part of the code... and open the Server and Client threads in two separate console views - by calling 
their own main() method individually. You can use Ctrl+C to kill the Server thread process, if not implementing that programmatically. The way
the code is above, it will end the Server thread as soon as the connection to the first Client ends (for avoiding blocking the continuation of 
the outer demonstration script, which involves other subpackages).

Observe that we only used Socket API classes for the connection, and not higher level classes or APIs, including Jakarta Servlet API.
This example is simply to demonstrate how all higher level classes in Java have been implemented, including the internal Core Java
java.net.http classes we used on webservice01 and webservice02, as well as the external (NOT Java Core) Jakarta Servlet API (old J2EE
Specification), and thus all implementations of commercial Web or Application Servers on the market, as well as all Java Web frameworks,
as Spring and Quarkus, that extend those. All networking communications in Java pass through the Socket API. And, of course, in real
scenarios, we should never use such a low-level code to implement, by hand, what a real Web or Application Servers, extended by the Web
Frameworks, already implement for us. There's so much more complexity involved and that we should not bother to implement, unless we would
maintain the Java JDK or related development tools. But not when creating business applications at a Production environment.
*/
