package validator;

import java.util.ArrayList;

public class CreditCardValidator implements CreditCardChecking {

	/* Missing Logger
	 * private static final LOGGER ....
	 */

	@Override
	public boolean checkValidity(CreditCardVendor ccv, String number) {
		//We should provide CreditCardVendor param to each validator instead of individual arrays
		ArrayList<Integer> chars = ccv.getNumberOfChars();
		ArrayList<String> masks = ccv.getIIN();

		/* Best option for each validation would be to add interface for it
		 * it would be great to have CardValidator interface - parameters would be name and CreditCardVendor
		 * Then we would create LengthCardValidator, IINMaskCardValidator, LuhnCorrectnessCardValidator
		 * It can be also optimization as right now we have to check all validators to get results
		 * With this we can fail as soon as one validator returns false.
		 */
		boolean result1 = checkLengthCorrectness(number, chars);
		boolean result2 = checkIINMaskCorrectness (number, masks);
		boolean result3 = checkLuhnalgorithmCorrectness(number);

		//Would be good to have debug logger in this place to log which validator failed for card

		return result1 && result2 && result3;
	}

	//Instead of ArrayList<Integer> we can provide CreditCardVendor -> for number length validation we should use Set
	private boolean checkLengthCorrectness(String number, ArrayList<Integer> chars){
		boolean result = false;
		// With Length we can use set of valid -> we have to get number.lenght() and check if Set contains this number
		for (int i = 0; i<chars.size();i++){
			if (chars.get(i) == number.length())
				result = true;
		}
		return result;
	}

	//Instead of ArrayList<String> we can provide CreditCardVendor
	private boolean checkIINMaskCorrectness (String number, ArrayList<String> masks){
		/* Lets use Map<Integer, Set<String>> where Integer is size of pattern. and Set contains valid numbers
		 * we iterate through Map, to validate if patter of size matcher start of our card number, should be much faster
		 * in case we have large number of valid prefixes for card, but only slightly slower if we have single number of prefixes
		 */
		boolean result = false;
		for (int i = 0; i<masks.size();i++){
			if (number.startsWith(masks.get(i)))
				result = true;
		}
		return result;
	}

	private boolean checkLuhnalgorithmCorrectness(String number){
		boolean result = false;

		/* lines 59 to 66 can be optimized
		 * first we can use two Methods
		 * - faster charAt(i)
		 * - with memory copy -> toCharArray()
		 *
		 * we can create intCharsReverese with size from number.length();
		 * there is no need to split number
		 */
		String[] characters = number.split("");
		int[] intCharsReverse = new int[characters.length];
		int sum = 0;

		// If we know that number doesnt contain any other characters then 0-9 we can use faster operation to get int char - '0'
		for(int i = 0;i<characters.length;i++){
			intCharsReverse[characters.length-1-i] = Integer.parseInt(characters[i]);
		}

		/* I think we can remove if in this statement -> we can start iterating from 1 and have jump of 2, could improve performance
		 * we can merge this loop with one below
		 */
		for(int i = 0;i<intCharsReverse.length;i++){
			intCharsReverse[i] = (i%2==1) ? 2*intCharsReverse[i] : intCharsReverse[i];
		}

		/* optimization -> we only multiply numbers which are odd numbers
		 *
		 * we are operating in numbers from range of 1 to 9 and in previous step we have multiplied it by 2, maximum value in our case is 18
		 *
		 * TWO optimizations to check:
		 * First:
		 * We do not need last divide by 10 (dive is slow cpu operation) we can use add +1 instead, as we are trying to get module (values from 0 to 8 , as 18 is max number)
		 * in this algo we divide by 10 to get 1, as leading number, as we always do it for values greater then 10, we can skip divide and just add 1.
		 * in this case we keep If statement but remove divide which is slow
		 *
		 * Same optimization as in previous step, lets start with 1 and jump by 2 -
		 *
		 * Second:
		 * We remove if statement and perform only intCharsReverse[i]%10 + intCharsReverse[i]/10
		 * Same optimization as in previous step, lets start with 1 and jump by 2 - i think if has to stay
		 *
		 * Would be good to check in microbenchmarks which set of operations is faster
		 */
		for(int i = 0;i<intCharsReverse.length;i++){
			sum += (intCharsReverse[i]<10) ? intCharsReverse[i] : (intCharsReverse[i]%10 + intCharsReverse[i]/10);
		}

		// We can just return sum%10 == 0 -> we dont need if and return and result variable
		if(sum%10==0)
			result = true;
		
		return result;
	}

}
