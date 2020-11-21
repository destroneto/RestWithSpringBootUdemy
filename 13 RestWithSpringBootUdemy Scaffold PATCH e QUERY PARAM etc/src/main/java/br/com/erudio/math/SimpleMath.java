package br.com.erudio.math;

public class SimpleMath {

	public Double sum(Double numberOne, Double numberTwo) throws Exception {
		return numberOne + numberTwo;
	}

	
	public Double subtract(Double numberOne, Double numberTwo) {
		return (numberOne) - (numberTwo);
	}

	
	public Double multiply(Double numberOne, Double numberTwo) {
		return (numberOne) * (numberTwo);
	}

	
	public Double divide(Double numberOne, Double numberTwo) {
		return (numberOne) / (numberTwo);
	}

	
	public Double square(Double numberOne, Double numberTwo) {
		if ((numberOne).equals(0D) || (numberTwo).equals(0D)) {
			return 0D;
		}
		return Math.pow(numberOne, 1.0 / numberTwo);
	}

	
	public Double mean(Double numberOne, Double numberTwo) {
		return ((numberOne) + (numberTwo)) / 2;
	}

}
