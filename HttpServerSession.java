import java.io.*;
import java.net.*;
import java.util.*;

class HttpServerSession extends Thread 
{
	Socket client;
	public HttpServerSession(Socket s)
	{
		client = s;
	}
		public void run()
		{
			try
			{	
				BufferedOutputStream bos = new BufferedOutputStream(client.getOutputStream());
				FileInputStream fileStream;
				BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				System.out.println("Request is sent from the client");
				String line = reader.readLine();
				System.out.println(line);
				String[] parts = line.split(" ");
				String fileName = parts[1].substring(1);
				byte[] bite = new byte[1024];
				String type = "text/plain"; 

				try
				{
				if(fileName.equals("") || fileName.equals("favicon.ico"))
				{
					fileName = "default.html";
				}

				File file = new File(fileName);
				fileStream = new FileInputStream(file);

				if(fileName.endsWith(".html") || fileName.endsWith(".htm"))
				{
					type = "text/html";
				}
				
				if(fileName.endsWith(".png"))
				{
					type = "image/png";
				}
				if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))
				{
					type = "image/jpg";
				}
				
				while(true)
				{
					String string = reader.readLine();
					if(string == null)
						System.out.println("End of file");
					if(string.compareTo("") == 0)
						break;
				}
				println("HTTP/1.1 200 OK");
				println("Content-Type: " + type);
				System.out.println("Content-Type: " + type);
				println("");

				while(fileStream.read(bite)!=-1)
				{
					bos.write(bite);
					sleep(1000);
				}

				bos.flush();
				bos.close();
				fileStream.close();
				client.close();
			}
			catch (FileNotFoundException e)
			{
				try{
				System.out.println(e);
				println("HTTP/1.1 404 NotFound");
				println("Content-Type: text/html");
				println("");
				println("<head></head><body>File Not Found</body>");

				client.close();
				}
				catch(Exception ex)
				{
					System.out.println("err: " + ex);
				}
			}
			catch (Exception e)
			{
				System.out.println("err: " + e);
				e.printStackTrace();
			}
		}
			catch (Exception e)
			{
				System.out.println("err: " + e);
				e.printStackTrace();
			}
	}
		private void println(String s) throws IOException
		{
			BufferedOutputStream bos = new BufferedOutputStream(client.getOutputStream());
			String news = s + "\r\n";
			byte[] array = news.getBytes();
			for(int i=0; i<array.length; i++){ bos.write(array[i]);
				bos.flush();}
			return;
		}
}
