import java.io.*;
import java.net.*;
import java.util.*;

class HttpServer
{
	public static void main (String args[])
	{
		Socket client;
		try
		{
		System.out.println("Web server starting");
		ServerSocket ss = new ServerSocket(8080);
		System.out.println("Port number is: "+ ss.getLocalPort());
			while(true)
			{
				client = ss.accept();
				
				HttpServerSession thread = new HttpServerSession(client);
				thread.start();

				PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
				System.out.println("Connection was received: "+client.getInetAddress());
			}
		}
		catch(Exception e)
		{
		System.out.println("Error " + e);
		}

	}
}
