 import java.io.*;
import java.net.*;
import java.util.*;
class ThreadCreation implements Runnable {
	BufferedReader br = null;
	PrintWriter pw = null;
	public ThreadCreation()  {
		try{

			ServerSocket ss = new ServerSocket(8000);
			Socket s=ss.accept();
			System.out.println("Connection Established");
			InputStreamReader isr = new InputStreamReader(System.in);
			 br = new BufferedReader(isr);
			 pw = new PrintWriter(s.getOutputStream(),true);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(s.getInputStream()));
			Thread th = new Thread(this);
			th.start();
			while(true) {
				System.out.println("message from client:");
				String str = br1.readLine();
				System.out.println(""+str);	
			}
		}catch(Exception e){}
			
	}

	public void run(){
		try{

			while (true) {
				System.out.println(" server:");
				String str1 = br.readLine();
				pw.println(""+str1);
			}
		}catch(Exception e1){}
	}
}
class Asynser {
	public static void main(String[] args)  {
		new ThreadCreation();
	}
}