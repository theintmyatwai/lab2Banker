import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Banker {
	private int numberOfCustomers;	// the number of customers
	private int numberOfResources;	// the number of resources

	private int[] available; 	// the available amount of each resource
	private int[][] maximum; 	// the maximum demand of each customer
	private int[][] allocation;	// the amount currently allocated
	private int[][] need;		// the remaining needs of each customer

	/**
	 * Constructor for the Banker class.
	 * @param resources          An array of the available count for each resource.
	 * @param numberOfCustomers  The number of customers.
	 */
	public Banker (int[] resources, int numberOfCustomers) {
		// TODO: set the number of resources
		this.numberOfResources = resources;

		// TODO: set the number of customers
		this.numberOfCustomers = numberOfCustomers;
		// TODO: set the value of bank resources to available
		this.available = new int[resources];

		// TODO: set the array size for maximum, allocation, and need
		this.maximum = new int[numberOfCustomers][resources];
		this.allocation = new int[numberOfCustomers][resources];
		this.need = new int[numberOfCustomers][resources];
		//Done
	}

	/**
	 * Sets the maximum number of demand of each resource for a customer.
	 * @param customerIndex  The customer's index (0-indexed).
	 * @param maximumDemand  An array of the maximum demanded count for each resource.
	 */
	public void setMaximumDemand(int customerIndex, int[] maximumDemand) {
		// TODO: add customer, update maximum and need
		for (i = 0; i < numberOfResources; i++){
			this.maximum[customerIndex][i] = maximumDemand[i];
			this.need[customerIndex][i] = maximumDemand[i];
		}
		//Done
	}

	/**
	 * Prints the current state of the bank.
	 */
	public void printState() {
        System.out.println("\nCurrent state:");
        // print available
        System.out.println("Available:");
        System.out.println(Arrays.toString(available));
        System.out.println("");

        // print maximum
        System.out.println("Maximum:");
        for (int[] aMaximum : maximum) {
            System.out.println(Arrays.toString(aMaximum));
        }
        System.out.println("");
        // print allocation
        System.out.println("Allocation:");
        for (int[] anAllocation : allocation) {
            System.out.println(Arrays.toString(anAllocation));
        }
        System.out.println("");
        // print need
        System.out.println("Need:");
        for (int[] aNeed : need) {
            System.out.println(Arrays.toString(aNeed));
        }
        System.out.println("");
	}

	/**
	 * Requests resources for a customer loan.
	 * If the request leave the bank in a safe state, it is carried out.
	 * @param customerIndex  The customer's index (0-indexed).
	 * @param request        An array of the requested count for each resource.
	 * @return true if the requested resources can be loaned, else false.
	 */
	public synchronized boolean requestResources(int customerIndex, int[] request) {
		// TODO: print the request
		System.out.println("Customer " + customerIndex + " requesting");
		for (int  = 0; k < numberOfResources; k++){	
			System.out.println(request[k]);
			
		}
		
		// TODO: check if request larger than need
		// TODO: check if request larger than available
		
		for (int j = 0; j < numberOfResources; j++){
			if (request[j] > need[j] || request[j] > available[j]){
				//raise error 
				System.out.println(customerIndex+"request of "+j+"rejected\n");
			}	
		}
		// TODO: check if the state is safe or not
		// TODO: if it is safe, allocate the resources to customer customerNumber
		for (int i = 0; i < numberOfResources; i++){
			available[i] = available[i] - request[i];
			allocation[customerIndex][i] = allocation[customerIndex][i] + request[i];
			need[customerIndex][i] = need[customerIndex][i] - request[i];
		}
		return true;
		//Done
	}

	/**
	 * Releases resources borrowed by a customer. Assume release is valid for simplicity.
	 * @param customerIndex  The customer's index (0-indexed).
	 * @param release        An array of the release count for each resource.
	 */
	public synchronized void releaseResources(int customerIndex, int[] release) {
		// TODO: print the release
		System.out.println("Customer " + customerIndex + " releasing");
		for (int k = 0; k < numberOfResources; k++){
			System.out.println(release[k]);
		}
		// TODO: release the resources from customer customerNumber
		for (int j = 0; j < numberOfResources; j++){
			available[j] = available[j] + release[j];
			allocation[customerIndex][j] -= release[j];
			need[customerIndex][j] +=release[j];
		}

	}

	/**
	 * Checks if the request will leave the bank in a safe state.
	 * @param customerIndex  The customer's index (0-indexed).
	 * @param request        An array of the requested count for each resource.
	 * @return true if the requested resources will leave the bank in a
	 *         safe state, else false
	 */
	private synchronized boolean checkSafe(int customerIndex, int[] request) {
		//work = available
		//create copies of available, need and allocation
		int[] work = new int[resources];
		int[][] tempNeed = new int[numberOfCustomers][resources];
		int[][] tempAllocation = new int[numberOfCustomers][resources];
		// TODO: copy the bank's state into the newly created arrays, and allocate.
		for (int i = 0; i < numberOfCustomers; i++)
		{
			for (int j = 0; j < numberOfResources; j++)
			{
				if (i == customerIndex)
				{
					tempNeed[i][j] = need[i][j] - request[j];
					tempAllocation[i][j] = allocation[i][j] + request[j];
				}
				else
				{
					tempNeed[i][j] = need[i][j];
					tempAllocation[i][j] = allocation[i][j];
				}
			}
			
			
		}
		//create the boolean array initialized with false
		boolean[] finish = new int[numberOfCustomers];
		for (int i = 0; i < numberOfCustomers; i++){
			finish[i] = false;
		}
		//initialize work
		for (int j = 0; j < numberOfCustomers; j++){
			work[j] = available[j] - request[j];
		}
			
		// TODO: check if the state is safe
		int x = 1;
		while (x){
			x = 0;
			for (int k = 0; k < numberOfCustomers; k++){
				int countResource = 0;
				if (finish[k] == false)
				{
					for (int l = 0; l < numberOfResources; l++){
						//(b)Need[i] <= Work
						if (tempNeed[k][l] <= work[l]){
							countResource++
						}
						if (countResource == numberOfResources){
							x = 1;
							//update
							for (int j = 0; j < numberOfResources; j++){
								work[j] = work[j] - request[j];
								finish[j] = true;
							}
						}
						countResource = 0;
					}
				}
			}	
		}
		//once a false in finish is detected, it will return false
		for (int m = 0; m < numberOfCustomers; m++){
			if (finish[m] == false){
				return false;
			}
		}
			//if all are true, the condition finish[m] == false will not be executed. Hence, it will return one.
		return true;
		
	}

	/**
	 * Parses and runs the file simulating a series of resource request and releases.
	 * Provided for your convenience.
	 * @param filename  The name of the file.
	 */
	public static void runFile(String filename) {

		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(filename));

			String line = null;
			String [] tokens = null;
			int [] resources = null;

			int n, m;

			try {
				n = Integer.parseInt(fileReader.readLine().split(",")[1]);
			} catch (Exception e) {
				System.out.println("Error parsing n on line 1.");
				fileReader.close();
				return;
			}

			try {
				m = Integer.parseInt(fileReader.readLine().split(",")[1]);
			} catch (Exception e) {
				System.out.println("Error parsing n on line 2.");
				fileReader.close();
				return;
			}

			try {
				tokens = fileReader.readLine().split(",")[1].split(" ");
				resources = new int[tokens.length];
				for (int i = 0; i < tokens.length; i++)
					resources[i] = Integer.parseInt(tokens[i]);
			} catch (Exception e) {
				System.out.println("Error parsing resources on line 3.");
				fileReader.close();
				return;
			}

			Banker theBank = new Banker(resources, n);

			int lineNumber = 4;
			while ((line = fileReader.readLine()) != null) {
				tokens = line.split(",");
				if (tokens[0].equals("c")) {
					try {
						int customerIndex = Integer.parseInt(tokens[1]);
						tokens = tokens[2].split(" ");
						resources = new int[tokens.length];
						for (int i = 0; i < tokens.length; i++)
							resources[i] = Integer.parseInt(tokens[i]);
						theBank.setMaximumDemand(customerIndex, resources);
					} catch (Exception e) {
						System.out.println("Error parsing resources on line "+lineNumber+".");
						fileReader.close();
						return;
					}
				} else if (tokens[0].equals("r")) {
					try {
						int customerIndex = Integer.parseInt(tokens[1]);
						tokens = tokens[2].split(" ");
						resources = new int[tokens.length];
						for (int i = 0; i < tokens.length; i++)
							resources[i] = Integer.parseInt(tokens[i]);
						theBank.requestResources(customerIndex, resources);
					} catch (Exception e) {
						System.out.println("Error parsing resources on line "+lineNumber+".");
						fileReader.close();
						return;
					}
				} else if (tokens[0].equals("f")) {
					try {
						int customerIndex = Integer.parseInt(tokens[1]);
						tokens = tokens[2].split(" ");
						resources = new int[tokens.length];
						for (int i = 0; i < tokens.length; i++)
							resources[i] = Integer.parseInt(tokens[i]);
						theBank.releaseResources(customerIndex, resources);
					} catch (Exception e) {
						System.out.println("Error parsing resources on line "+lineNumber+".");
						fileReader.close();
						return;
					}
				} else if (tokens[0].equals("p")) {
					theBank.printState();
				}
			}
			fileReader.close();
		} catch (IOException e) {
			System.out.println("Error opening: "+filename);
		}

	}

	/**
	 * Main function
	 * @param args  The command line arguments
	 */
	public static void main(String [] args) {
		if (args.length > 0) {
			runFile(args[0]);
		}
	}

}