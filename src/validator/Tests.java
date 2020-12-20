package validator;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.junit.Test;

/* Wrong file name -> it should reflect class we are testing -> i suggest name CreditCardValidatorIT as this is more an integration test then unit test
 * we should divide our test into unit test and integration test, this is integration test as it works on fully connected system
 * In unit test we should be only focusing on single class and its functionality
 *
 * In Integration tests (deepeneding how complex test us) we can connect two or more components to validate particulat system flow.
 * I would suggest to add separate test for  CreditCardVendorsReader, CreditCardVendor and each validator class you will create after this review.
 *
 * In addition to unit and integration test i would suggest to add MicroBenchmarks to critical part of application in order to monitor Performance and GC levels
 */
public class Tests {
	private static CreditCardVendorsReader reader = new CreditCardVendorsReader();
	private static ArrayList<CreditCardVendor> vendors = reader.returnVendorArray();
	private static CreditCardValidator validator = new CreditCardValidator();
	//not needed in this case
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static CreditCardVendor vendorMasterCard = vendors.get(1);

	/* Not too many test cases, we need to include following test cases in order to correctly validate system
	 * - null card number
	 * - null vendorMasterCard
	 * - alphanumeric values
	 * - empty strings
	 * - characters others then regular ones (different language or special ASCI characters)
	 */

	@Test
	public void testLengthOfNumberWhenCorrect() {
		String number = "5584239583699571";
		assertEquals(true, validator.checkValidity(vendorMasterCard, number));
	}
	
	@Test
	public void testLengthOfNumberWhenIncorrect() {
		String number = "55842395";
		assertEquals(false, validator.checkValidity(vendorMasterCard, number));
	}
	
	@Test
	public void testIINWhenCorrect() {
		String number = "5584239583699571";
		assertEquals(true, validator.checkValidity(vendorMasterCard, number));
	}
	
	@Test
	public void testIINWhenIncorrect() {
		String number = "7784239583699571";
		assertEquals(false, validator.checkValidity(vendorMasterCard, number));
	}
	
	@Test
	public void testLuhnAlghoritmWhenCorrect() {
		String number = "5584239583699571";
		assertEquals(true, validator.checkValidity(vendorMasterCard, number));
	}
	
	@Test
	public void testLuhnAlghoritmWhenIncorrect() {
		String number = "5584239583611111";
		assertEquals(false, validator.checkValidity(vendorMasterCard, number));
	}

}
