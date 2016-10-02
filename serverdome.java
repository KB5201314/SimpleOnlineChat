
import java.net.*;
import java.io.*;
import java.util.*;

public class serverdome{

ArrayList<BufferedReader> BRList;
ArrayList<PrintWriter> PWList;
int i;
int person;
ServerSocket serversocket;
Scanner scanner;


	public serverdome(){

		BRList = new ArrayList<BufferedReader>();
		PWList = new ArrayList<PrintWriter>();
		i = -1;
		person = i + 1;
		try{
			Socket socket;
			System.out.println("Choose a port number");
			scanner = new Scanner(System.in);
			serversocket = new ServerSocket(Integer.parseInt(scanner.nextLine()));
			System.out.println("waiting.....");
			new Thread(){
				public void run(){
					
					String message;
					try{
						while(true){
							message = scanner.nextLine();

							for(PrintWriter PW : PWList){
								if(PW != null){
								
									PW.println("> \n " + "> admin:" + message + " \n >");
									
									PW.flush();

								}
							}
						}
					}catch(Exception e){}
				}
			}.start();

		while(true){
			socket = serversocket.accept();
			BRList.add(new BufferedReader(new InputStreamReader(socket.getInputStream())));
			PWList.add(new PrintWriter(socket.getOutputStream()));
			i++;
			person = i + 1;
			new Thread(new Live()).start();
			System.out.println("Someone join in..." + "  " + person +"  ");

		}


		}catch(IOException e){}
	
	}


	public static void main(String[] args) {

		serverdome s = new serverdome();
		

	}

	public class Live implements Runnable{
		public void run(){
			try{
			int num = i;

			String message;

			while((message = BRList.get(num).readLine()) != null){
				System.out.println(message);
				for(PrintWriter PW : PWList){
					if (PW != null) {
						PW.println(message);
						PW.flush();
					}
				}

			}
			BRList.get(num).close();
			PWList.get(num).close();
		}catch(IOException e){}
			System.out.println("Someone left...");
		
		}
	}

}