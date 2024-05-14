public class Polynomial {
	double [] coefficients;

	public Polynomial() {
		coefficients = new double [1];
	}

	public Polynomial(double [] input){
		coefficients = new double [input.length];
		for (int i = 0; i<input.length; i++) {
			coefficients[i] = input[i];
		}
	}

	public Polynomial add (Polynomial input) {
		if (this.coefficients.length > input.coefficients.length) {
			Polynomial result = new Polynomial (this.coefficients);
			for(int i = 0; i<input.coefficients.length; i++) {
				result.coefficients[i] += input.coefficients[i];
			}
			return result;
		}
		else {
			Polynomial result = new Polynomial (input.coefficients);
			for(int i = 0; i<this.coefficients.length; i++) {
				result.coefficients[i] += this.coefficients[i];
			}
			return result;
		}
	}

	public double evaluate(double x) {
		double count = 0;
		for (int i = 0; i<coefficients.length; i++) {
			count += coefficients[i]*Math.pow(x,i);
		}
		return count;
	}

	public boolean hasRoot (double input) {
		double output = this.evaluate(input);
		if (output == 0) return true;
		return false;
	}

}