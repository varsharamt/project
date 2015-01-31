 import java.io.*;
import java.net.*;
import java.util.*;
class ThreadCreation implements Runnable {
		BufferedReader br1 = null;			
	public ThreadCreation()  {
		try {
				Socket s = new Socket("127.0.0.1",8000);
				InputStreamReader isr = new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(isr);
				PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
				 br1 = new BufferedReader(new InputStreamReader(s.getInputStream()));
				Thread th = new Thread(this);
			    th.start();
			while(true) {
				
				System.out.println("client:");
				String str = br.readLine();
				pw.println(str);	
						}
		    } 
		catch(Exception e){
			System.out.println(e);
						  }
		
						  
		

	}
			
	public void run(){
		try{

			while (true) {
				System.out.println("message from server:");
				String str1 = br1.readLine();
				System.out.println(""+str1);
			}
		}
		catch(Exception e){
				System.out.println(e);
		}
	}
		
	
}
class AsynClient {
	public static void main(String[] args)throws IOException  {
		new ThreadCreation();
	}
}