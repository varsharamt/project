import java.io.*;
import java.net.*;

class  Client {
	public static void main(String[] args)throws IOException  {
		Socket s=new Socket("127.0.0.1",8000);
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
		BufferedReader br1=new BufferedReader(new InputStreamReader(s.getInputStream()));
		while(true)
		{
			System.out.println("client:");
			String str=br.readLine();
			pw.println(str);
			System.out.println("message from server:");
			String str1=br1.readLine();
			System.out.println(""+str1);
		}


		}
}
	