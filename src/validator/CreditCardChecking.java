package validator;

public interface CreditCardChecking {
	//Do not need public
	public boolean checkValidity(CreditCardVendor ccv, String number);
}
