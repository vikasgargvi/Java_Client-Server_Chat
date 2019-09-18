import java.net.*;
import java.io.*;

public class ChatServer{
	public static void main(String[] args){
		if(args.length != 1){
			System.out.println("Program requires 2 command line arguments");
		}
		else{
			try{
				int portNo;
				String message, you; //message input from command line and sent to the client
				portNo = Integer.parseInt(args[0]);
				// message = args[1];

				while(true){
					// Create connection Socket and Listen Connection request
					ServerSocket connSocket = new ServerSocket(portNo);
					System.out.println("\nServer is ready to accept a new Connection");

					// Accept Connection request
					Socket dataSocket = connSocket.accept();
					System.out.println("Connection Accpeted" + (InetAddress)dataSocket.getInetAddress());
					Thread.currentThread().sleep(2000);

					InputStream inStream = dataSocket.getInputStream();
					BufferedReader socketInput = new BufferedReader(new InputStreamReader(inStream));

					// get an Ouput Stream for writing data to the data socket
					OutputStream outStream = dataSocket.getOutputStream();
					// create a PrintWriter Object for Character-mode Output
					PrintWriter socketOutput = new PrintWriter(new OutputStreamWriter(outStream));
					socketOutput.println("Start Conversation \n");
					socketOutput.flush();

					BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

					do{
						while((message = socketInput.readLine()).length() == 0);
						if(message.equals("end")){
							System.out.print("Chat Terminated by Client");
							break;
						}

						System.out.println("Client: " + message);

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

					// close dataSocket and connSocket
					dataSocket.close();
					System.out.println("dataSocket Closed");
					Thread.currentThread().sleep(2000);

					connSocket.close();
					System.out.println("Connection Closed");
				}

				
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}