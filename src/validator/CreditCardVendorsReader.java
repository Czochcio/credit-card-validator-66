package validator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CreditCardVendorsReader implements CreditCardVendorReading {

	/* Missing Logger
	 * private static final LOGGER ....
	 */

	@Override
	/* Do not use implementation - use interface, please use List interface instead of ArrayList
	 * while using ArrayList type may have minor performance improvements it limits options when it comes to using implementation of List
	 *
	 * We should rename this method to initVendors() -> we have to create List of vendors in class body in order to cache results.
	 * it is not optimal to read file each time, in order to reload vendors we can use file system monitor
	 * to reload when file changes if we want to have live reload
	 */
	public ArrayList<CreditCardVendor> returnVendorArray() {
		ArrayList<CreditCardVendor> list = new ArrayList<CreditCardVendor>();
		
		try(BufferedReader br = new BufferedReader(new FileReader("Base_of_credit_card_vendors.txt"))) {
		    for(String line; (line = br.readLine()) != null; ) {
				//extract this to method initVendorDetails, this is too long method and would be good to split it
		        String[] values = line.split(" ");
		        
		        String vendorName = values[0];

		        /* methods should not modify input parameters
		         * if we need to return something please use return statement
		         * Don't create variables outside method, just to modify it
		         * Same as at top - please use List interface
		         */
		        ArrayList<Integer> vendorNumbersOfChars = new ArrayList<Integer>();
		        ArrayList<String> vendorMasks = new ArrayList<String>();

		        parseLengths(values[1], vendorNumbersOfChars);
		        parseMasks(values[2], vendorMasks);

		        /* Best practice here would be to use builder pattern - constructor is ok, but with additional parameters it would make this line not readable*/
		        list.add(new CreditCardVendor(vendorName,vendorNumbersOfChars,vendorMasks));
		        
		    }
		} catch (FileNotFoundException e) {
			// Please use LOGGER
			e.printStackTrace();
		} catch (IOException e) {
			// Please use LOGGER
			e.printStackTrace();
		}
		
		return list;
	}

	/* ArrayList<Integer> vendorNumbersOfChars - this should not be a parameter
     * Please return value or List<Integer> - create variable in this method and return it
     *
     * Method should throw ParseCardLengthException checked exception
	 */
	private void parseLengths(String value, ArrayList<Integer> vendorNumbersOfChars){
		/* Missing null checks - should return Collections.emptyList in that case
		 * "/" we can use named constant here
		 */
		String[] values = value.split("/");
		for(int i=0; i<values.length; i++){
        	vendorNumbersOfChars.add(Integer.parseInt(values[i]));
        }
	}

	/* Same case as parseLengths method - Please dont use ArrayList<String> vendorMasks
	 * return List<String> - create variable and return it
	 *
	 * Method should throw ParseCardMaskException checked exception
	 */
	private void parseMasks(String value,  ArrayList<String> vendorMasks){
		 /* Please use try/catch statement here, this method should throw ParseCardMaskException on exception with description
		  * We should consider catching know exception with and throw checked ParseCardMaskException correct description - like NumberParseException with description invalid character in mask string
		  *
		  * Missing null checks - should return Collections.emptyList in that case
		  * "/", "-" we can use named constant here
		  */
	     String[] masks = value.split("/");
	     for(int i=0;i<masks.length; i++){
	     	/* extract to method
	     	 * it should return processed mask -> first if statement can have return, second part doesnt have to be in else, code would be easier to read
	     	 */
	     	if (masks[i].split("-").length == 1){
	     		vendorMasks.add(masks[i]);
	     	}else{
	     		long min= Long.parseLong((masks[i].split("-"))[0]);
	     		long max= Long.parseLong((masks[i].split("-"))[1]);
	     		for (long j = min; j<=max;j++)
	     			//we should use string format here -> we can miss leading zeros if card values will start with 0555-1555
	     			vendorMasks.add(""+j);
	     	}
	     }
	}
	
}
