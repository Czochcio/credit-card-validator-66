package validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/* SUMMARIZING COMMENT
 *
 * Will be good to use IoC in this code -> this will allow us to create more flexible system and help with testing
 *
 * To convert this application to rest service following things has to be considered:
 * - adding REST service support via framework (Spring)
 * - defining correct API
 *   - GET example supported vendors card validations
 *   - PUT validate card
 * - implement security
 *   - JWT only vendors who are registered can use API
 *   - rate limiter -> we should not allow users to spam
 */

public class CreditCardValidatorProject {

	/* Missing Logger
	 * private static final LOGGER ....
	 */

	private static CreditCardVendorsReader reader = new CreditCardVendorsReader();
	private static ArrayList<CreditCardVendor> vendors = reader.returnVendorArray();
	private static CreditCardValidator validator = new CreditCardValidator();
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) {
		//Use Logger
        System.out.println("CREDIT CARD VALIDATOR\n");

        //
        int vendorsLength = vendors.size();
        int selected = 0;

        //This method is too long and have to many sections
        while (true){
        	printMenu();
        	try{
        		//Extract content of this try catch to method method
                selected = Integer.parseInt(br.readLine());
                /* in method, change condition to oposite, and log when its true, using logger -> unknown card vendor, use return statement inside condition
                 * rest part of code should be outside of if, this will make code more readable and less complex and easier to read plain
                 */
                if(selected < vendorsLength){
					//Use Logger
    	        	System.out.println("Type your credit card number:");
    	        	String cardNumber;
    				try {
    					cardNumber = br.readLine();
    					boolean result = validator.checkValidity(vendors.get(selected), cardNumber);
    					printResultOfValidation(result);
    				} catch (IOException e) {
						//Use Logger
    					System.out.println("Something went wrong!\n Please retry the process");
    				}
    	        	
            	}else{
            		break;
            	}
            }catch(NumberFormatException | IOException e){
				//Use Logger
                System.err.println("Invalid Format!");
            }
        	
        }
        
    }
	
	static private void printMenu(){
		//Use Logger
		System.out.println("Select your card vendor from list below("+vendors.size()+" exits):");
    	for (int i = 0;i<vendors.size();++i)
			//Use Logger
    		System.out.println(i+" "+vendors.get(i).toString());
	}
	
	static private void printResultOfValidation(boolean result){
		if(result){
			//Use Logger
    		System.out.println("\n\nCard number is valid.\n");
    	}else{
			//Use Logger
    		System.out.println("\n\nCard number is invalid!\n");
    	}
	}
}
