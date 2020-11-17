package br.com.erudio.converters;

import br.com.erudio.validators.NumberValidator;

public class NumberConverter {
	
	public static Double convertToDouble(String strNumber) {
		if (strNumber == null) return 0D;
		String number = strNumber.replace(",", ".");
		if (NumberValidator.isNumeric(strNumber)) return Double.parseDouble(number);
		return 0D;
	}

}
