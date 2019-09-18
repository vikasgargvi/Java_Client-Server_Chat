import java.net.*;
import java.io.*;

public class ChatClient{
	public static void main(String[] args){
		if(args.length != 2)
				System.out.println("two arguments needed");
		else{
			try{
				InetAddress acceptorHost = InetAddress.getByName(args[0]);
				int port = Integer.parseInt(args[1]);
				String message, you;

				Socket mySocket = new Socket(acceptorHost, port);
				System.out.println("Request Sent");
				Thread.currentThread().sleep(2000);

				InputStream inStream = mySocket.getInputStream();
				BufferedReader socketInput = new BufferedReader(new InputStreamReader(inStream));

				// get an Ouput Stream for writing data to the data socket
				OutputStream outStream = mySocket.getOutputStream();
				// create a PrintWriter Object for Character-mode Output
				PrintWriter socketOutput = new PrintWriter(new OutputStreamWriter(outStream));
				// write message to output Stream
				
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

				do{
					while((message = socketInput.readLine()).length() == 0);
					if(message.equals("end")){
						System.out.println("Chat Terminated by server");
						break;
					}

					System.out.println("Server: " + message);

					// write message to output Stream
					System.out.print("You: ");
					you = input.readLine();
					socketOutput.println(you);
	
					// flush the socketOutput (Important) : read from Java Network Programming book why it is important page no. 29
					socketOutput.flush();

					if(you.equals("end")){
						break;
					}

				}while(true);
				Thread.currentThread().sleep(2000);

				mySocket.close();
				System.out.println("Connection Closed");
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}