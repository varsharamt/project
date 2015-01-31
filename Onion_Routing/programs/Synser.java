class import java.io.*;
import java.net.*;
import java.util.*;

class  Server {
	public static void main(String[] args)throws IOException  {
		ServerSocket ss=new ServerSocket(8000);
		System.out.println("connection established");
		Socket s=ss.accept();
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(s.getInputStream()));

		while(true)
		{
			System.out.println("message from client:");
			String str1=br1.readLine();
			System.out.println(""+str1);
			System.out.println("server:");
			String str2=br.readLine();
			pw.println(str2);
			
		}
			
			
	}
}