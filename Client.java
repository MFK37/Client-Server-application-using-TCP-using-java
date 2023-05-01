import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	
	
	
	public static void main(String[] args) throws Exception {
		Socket clientSocket = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		Scanner in = null;
		PrintWriter socketOut = null;
		DataOutputStream dos = null;
		
		try {
			clientSocket =  new Socket("localhost" , 3333); // create socket and connect to the server
			
			System.out.println("Connection Established");
			
			inputStreamReader = new InputStreamReader(clientSocket.getInputStream()); 
			dos = new DataOutputStream(clientSocket.getOutputStream()); // send to server
			socketOut = new PrintWriter(clientSocket.getOutputStream(), true); // send to server

			bufferedReader = new BufferedReader(inputStreamReader); // receive from server
			in = new Scanner(System.in); // input form user
			

		}catch (Exception e) { 
			System.out.println("Connection can not be established!");
			return;
			}
		
		int option = 8;
		do {
			
			///////////////////////////////////////// user menu
			
			System.out.println("*************************************************************");
			System.out.println("*            Welcome to branch management system            *");
			System.out.println("* 1- Add new branch            (MAX number of branchs is 10)*");
			System.out.println("* 2- Remove branch                                          *");
			System.out.println("* 3- Update branch info                                     *");
			System.out.println("* 4- View branch info                                       *");
			System.out.println("* 5- View all branchs info                                  *");
			System.out.println("* 6- View total Sales                                       *");
			System.out.println("* 7- View average rating                                    *");
			System.out.println("* 8- Exit                                                   *");
			System.out.println("*************************************************************");
			
			///////////////////////////////////////// user input
			
			try {
				System.out.print("Enter: "); 
				option = in.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid data entered!");
			}
			
			///////////////////////////////////////// adding block
			
			if (option == 1) {
				socketOut.println("add"); // sends alert message to server that the user wants to add branch					

				while (true) {
					System.out.print("enter id: ");
					String id = in.next();
					socketOut.println(id);	
						
					System.out.print("enter address: ");
					String address = in.next();
					socketOut.println(address);
		
					System.out.print("enter manager: ");
					String manager = in.next();
					socketOut.println(manager);						
						
					System.out.print("Enter sales: ");
					int sales = in.nextInt();
					dos.writeInt(sales);	
						
					System.out.print("enter rating: ");
					double rating = in.nextDouble();
					dos.writeDouble(rating);
						
					String loopBreaker = bufferedReader.readLine();
					if (!loopBreaker.equalsIgnoreCase("added")) { // if the array is full it will break from while loop
						System.out.println(loopBreaker);
						break;
					}
					System.out.println(loopBreaker); // adding confirmation
					
					System.out.print("Enter another? (yse / no) -> ");
					String another = in.next();
					socketOut.println(another);	// send to server if user wants to add another or not					
						
					if (another.equalsIgnoreCase("no")) {
						break;
					}
				}
			}
			
			///////////////////////////////////////// removing block
			
			else if (option == 2) { 	
				socketOut.println("remove"); // sends alert message to server that the user wants to remove branch
				
				while (true) {
					
					System.out.print("Enter Branch ID: ");
					String id = in.next();
					socketOut.println(id);
					
					String loopBreaker = bufferedReader.readLine();
					if (!loopBreaker.equalsIgnoreCase("Removed")) { // checks if server can remove or not if not exit the loop
						System.out.println(loopBreaker);
						break;
					}
					System.out.println(loopBreaker); // remove confirmation					

					System.out.print("Remove another? (yse / no) -> ");
					String another = in.next();
					socketOut.println(another);
						
					if (another.equalsIgnoreCase("no")) {
						break;
					}
				}
				
			}
			
			///////////////////////////////////////// updating block

			else if (option == 3) {
				socketOut.println("update");  // sends alert message to server that the user wants to update branch
				
				while (true) {
					
					System.out.print("Enter Branch ID: ");
					String id = in.next();
					socketOut.println(id);
					
					String output = bufferedReader.readLine(); // checks if server can update branch or not
					
					if (output.equals("found")) {
						
						System.out.print("enter updated id: ");
						String newID = in.next();
						socketOut.println(newID);
						
						System.out.print("enter updated address: ");
						String newAddress = in.next();
						socketOut.println(newAddress);
		
						System.out.print("enter updated manager: ");
						String newM = in.next();
						socketOut.println(newM);
						
						System.out.print("Enter updated sales: ");
						int newSales = in.nextInt();
						dos.writeInt(newSales);	
						
						System.out.print("enter updated rating: ");
						double newRating = in.nextDouble();
						dos.writeDouble(newRating);
						
						String loopBreaker = bufferedReader.readLine();
						if (!loopBreaker.equalsIgnoreCase("Updated")) { // checks if server can update or not if not exit the loop
							System.out.println(loopBreaker);
							break;
						}
						System.out.println(loopBreaker); // update confirmation
						
						System.out.print("Update another? (yse / no) -> ");
						String another = in.next();
						socketOut.println(another);
							
						if (another.equalsIgnoreCase("no")) {
							break;
						}
					}
					else {
						System.out.println(output);
						break;
					}
				}
			}
			
			///////////////////////////////////////// printOne block

			else if (option == 4) {
				socketOut.println("printOne"); // sends alert message to server that the user wants to print branch
				
				while (true) {
					
					System.out.print("Enter ID: ");
					String id = in.next();
					socketOut.println(id);
										
					System.out.println(bufferedReader.readLine());
					break;
				}
			}
			
			///////////////////////////////////////// printAll block

			else if (option == 5) {
				socketOut.println("printAll"); // sends alert message to server that the user wants to print all branches
				
				while (true) {
					String messege = bufferedReader.readLine();
					
					if (messege.equals("-1")) // if there is no branches stored exit
						break;
					
					System.out.println(messege);
				}
			}
			
			///////////////////////////////////////// total Sales block
			
			else if (option == 6) {
				socketOut.println("printTotalSales"); // sends alert message to server that the user wants to print total sales branches
				
				System.out.println(bufferedReader.readLine());
			}
			
			///////////////////////////////////////// exit block

			else if (option == 7) {
				socketOut.println("printAvgRating"); // sends alert message to server that the user wants to print average rating branches

				System.out.println(bufferedReader.readLine());
			}
			
			///////////////////////////////////////// exit block
			
			else if (option == 8) 
				socketOut.println("exit"); // sends alert message to server that the user wants to exit
			
			else 
				System.out.println("Please enter a valid option."); // if user entered invalid input
			
			///////////////////////////////////////// condition block
			
		}while (option != 8 && clientSocket.isConnected());
		
		System.out.println("Goodbye");
		
		try {
			socketOut.close();
			bufferedReader.close();
			dos.close();
			inputStreamReader.close();
			in.close();
			clientSocket.close();
		} catch (Exception e) {}
	}
}