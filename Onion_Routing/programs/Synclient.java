 import java.io.*;
import java.net.*;
import java.util.*;
class ThreadCreation implements Runnable {
	BufferedReader br1=null;
	public ThreadCreation()  {
		try{

			ServerSocket ss = new ServerSocket("127.0.0.1",8000);
			Socket s=ss.accept();
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
			 br1 = new BufferedReader(new InputStreamReader(s.getInputStream()));
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
class Synser {
	public static void main(String[] args)  {
		new ThreadCreation();
	}
}