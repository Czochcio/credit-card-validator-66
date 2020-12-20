package validator;

import java.util.ArrayList;

public class CreditCardVendor {
	private String name;

	/* Please use interface
	 * Migrate NumberOfChars to Set -> this will optimize process of validating sizes
	 */
	private ArrayList<Integer> numberOfChars;
	/* Please use interface
	 * we can use Map<Integer, Set<String>> instead of Lists
	 * Integer - will be size of map
	 * Set - will contain all combinations
	 */
	private ArrayList<String> IIN;
	
	public String getName(){
		return name;
	}

	//this is not immutable
	public ArrayList<Integer> getNumberOfChars(){
		return numberOfChars;
	}

	//this is not immutable
	public ArrayList<String> getIIN(){
		return IIN;
	}

	//This is not immutable - the best would be to use builder pattern
	public CreditCardVendor(String vendorName, ArrayList<Integer> charsNumbers, ArrayList<String> masks){
		name = vendorName;
		numberOfChars = charsNumbers;
		IIN = masks;
	}

	//please use reflection builder to create toString - to string is missing information
	@Override
	public String toString(){
		return name;
	}
}
