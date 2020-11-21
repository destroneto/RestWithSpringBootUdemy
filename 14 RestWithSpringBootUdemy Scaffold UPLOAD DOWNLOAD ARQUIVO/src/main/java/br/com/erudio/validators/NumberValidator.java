package br.com.erudio.validators;

import br.com.erudio.exception.ResourceNotFoundException;

public class NumberValidator {

	public static void isNumbers(String numberOne, String numberTwo) {
		if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new ResourceNotFoundException("Please set a numeric value!");
		}
	}

	public static boolean isNumeric(String strNumber) {
		if (strNumber == null) return false;
		String number = strNumber.replace(",", ".");
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
}
