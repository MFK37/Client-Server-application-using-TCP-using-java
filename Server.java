import java.io.*;
import java.net.*;

public class Server {
	public static void main(String[] args) throws Exception {

		ServerSocket serverSocket = null;
		Socket connectionSocket = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		DataInputStream dis = null;
		BranchMethods bM = null;	
		String option = null;
		PrintWriter socketOut = null;

		////////////////////////////////////////////// Initializing 
		
		try {
			serverSocket = new ServerSocket(3333); // create socket
			connectionSocket = serverSocket.accept(); // accept client connection
			
			System.out.println("Connection Established");
			
			inputStreamReader = new InputStreamReader(connectionSocket.getInputStream());
			dis = new DataInputStream(connectionSocket.getInputStream()); // read form client
			bufferedReader = new BufferedReader(inputStreamReader); // read from client
			socketOut = new PrintWriter(connectionSocket.getOutputStream(), true); // send to client

			bM = new BranchMethods(); // create object of brachMethods and initialize array
			
			
		}catch (Exception e) {
			System.out.println("Connection can not be established!");
			return;
		}
		
		////////////////////////////////////////////// methods loop
		
		while (true) {
			option = bufferedReader.readLine(); // receive user choice 
			
			////////////////////////////////////////////// Adding loop 
			
			if (connectionSocket.isConnected() && option.equalsIgnoreCase("add")) { // enter if user wants to add new branch
				while (true) {
					String id = bufferedReader.readLine();
					String address = bufferedReader.readLine();
					String manager = bufferedReader.readLine();
					int sal = dis.readInt();
					double rating = dis.readDouble();
					
					Branch branch = new Branch(id, address, manager, sal, rating); // create a new branch
					if (bM.addBranch(branch))  // adds the new branch to branch array using branchMethods class
						socketOut.println("Added"); // add confirmation						
									
					if (bM.getNbBranch() == 10 || bufferedReader.readLine().equalsIgnoreCase("no")) { // check if array is full and user want to exit
						if (bM.getNbBranch() == 10 && bufferedReader.readLine().equalsIgnoreCase("yes")) // check if array is full and user want to continue
							socketOut.println("Can not add, array is full");						
						break;
					}
				}
			} 
			
			////////////////////////////////////////////// removing loop 
			
			else if (connectionSocket.isConnected() && option.equalsIgnoreCase("remove")) {
				while (true) {
					String id = bufferedReader.readLine(); // receive id wanted to be removed

					if (bM.removeBranch(id)) // if removed
						socketOut.println("Removed");
					else 
						socketOut.println("Can not remove");
					
					if (bM.getNbBranch() == 0 || bufferedReader.readLine().equalsIgnoreCase("no")) // if array is empty
						break;
				}				
			}
			
			////////////////////////////////////////////// updating loop 
			
			else if (connectionSocket.isConnected() && option.equalsIgnoreCase("update")) {
				
				while (true) {
					String id = bufferedReader.readLine(); // receive id wanted to be updated
					
					if (bM.searchID(id) != -1) {
						socketOut.println("found"); // found confirmation
						
						String newID = bufferedReader.readLine();
						String newAddress = bufferedReader.readLine();
						String newManager = bufferedReader.readLine();
						int newSal = dis.readInt();
						double newRating = dis.readDouble();
						
						Branch b = new Branch(newID,newAddress, newManager, newSal, newRating);
						if (bM.updateBranch(b, id)) { // add updated array
							socketOut.println("Updated");
						}
						else
							socketOut.println("Can not update");
						
						if (bufferedReader.readLine().equalsIgnoreCase("no"))
							break;
					}
					else {
						socketOut.println("Can not update");
						break;
					}
				}
			}
			
			////////////////////////////////////////////// Print one loop
			
			else if (connectionSocket.isConnected() && option.equalsIgnoreCase("printOne")) {
				while (true) {
					
					String bID = bufferedReader.readLine(); // id wanted to be printed
					int index = bM.searchID(bID); // index of the id
					
					if (index != -1) { // if found
						
						String id = bM.getBranchID(index);
						String address = bM.getAddress(index);
						String name = bM.getBranchManager(index);
						int sal = bM.getBranchSales(index);
						double rating = bM.getRating(index);
						
						socketOut.println("Branch ID: " + id + " // " 
										+ "Branch address: " + address + " // "
									    + "Branch manager name: " + name + " // " 
										+ "Branch sales: " + sal + " // " 
									    + "Branch rating: " + rating + "/10");						
						break;						
					}
					else 
						socketOut.println("Sorry, Can not find branch with that ID");
				}
			}
			
			////////////////////////////////////////////// Print all branches info
			
			else if (connectionSocket.isConnected() && option.equalsIgnoreCase("printAll")) {
			
				if (bM.getNbBranch() == 0) // if array is empty
					socketOut.println("Sorry, there is no branches saved");
				
				for (int i = 0 ; i < bM.getNbBranch() ; i++) { // print loop
					socketOut.println("Branch ID: " + bM.getBranchID(i) + " // " 
							+ "Branch address: " + bM.getAddress(i) + " // "
						    + "Branch manager name: " + bM.getBranchManager(i) + " // " 
							+ "Branch sales: " + bM.getBranchSales(i) + " // " 
						    + "Branch rating: " + bM.getRating(i) + "/10");
				}
				socketOut.println("-1"); // finish confirmation 
			}	
			
			///////////////////////////////////////////// Print total sales
			
			else if (connectionSocket.isConnected() && option.equalsIgnoreCase("printTotalSales")) {
				int sum = 0;
				
				for (int i = 0 ; i < bM.getNbBranch() ; i++) { // sum loop
					sum += bM.getBranchSales(i);
				}
				socketOut.println("Total sales: " + sum + " SR");
			}
			
			///////////////////////////////////////////// Print average rating

			else if (connectionSocket.isConnected() && option.equalsIgnoreCase("printAvgRating")) {
				if (bM.getNbBranch() != 0) { 
					double sum = 0;
					for (int i = 0 ; i < bM.getNbBranch() ; i++) 
						sum += bM.getRating(i);
					socketOut.println("Average branches rating: " + sum/bM.getNbBranch() + "/10");
				}
				else {
					socketOut.println("Sorry, Can not calculate. there is no branches saved");
				}
			}
			
			////////////////////////////////////////////// Exit for methods loop condition 
			
			else if (option.equalsIgnoreCase("exit")){
				break;
			}
		}	
		try {
			bufferedReader.close();
			dis.close();
			inputStreamReader.close();
			connectionSocket.close();
			serverSocket.close();
		} catch (Exception e) {}
	}
}