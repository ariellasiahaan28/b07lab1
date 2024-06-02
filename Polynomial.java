import java.io.File; 
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths; 

public class Polynomial {
	double [] coefficients;
	int [] exponents;

	public Polynomial() {
		coefficients = new double [0];
		exponents = new int[0];
	}

	public Polynomial(double [] input_coef, int[] input_exp){
		int size = input_coef.length;
		coefficients = new double  [size];
		exponents = new int [size];
		for (int i = 0; i < size; i++) {
			coefficients[i] = input_coef[i];
			exponents[i] = input_exp[i];
			}
	}
	
	public Polynomial (File file) throws IOException{	
		String path = file.getAbsolutePath();
		String poly = "";
		try{
			poly = new String(Files.readAllBytes(Paths.get(path)));
		}
		catch(IOException e){
            		e.printStackTrace();
        	}	
		
		String split[] = poly.split("[+-]");
		int size = split.length;
		coefficients = new double [size];
		exponents = new int [size];
		for (int i = 0; i < size; i++) {
			int findx = 0;
			if (findx == -1) {
				coefficients[i] = Double.parseDouble(split[i]);
				exponents[i] = 0;
			}
			else {
				coefficients[i] = Double.parseDouble(split[i].substring(0, findx));
				exponents[i] = Integer.parseInt(split[i].substring(findx + 1));	
			}
		}
		if (poly.charAt(0) == '-') coefficients[0] *= -1;
		int occur = 1;
		for (int i = 1; i < poly.length(); i++) {
			if (poly.charAt(i) == '-') {
				coefficients[occur] *= -1;
			}
			else if (poly.charAt(i) == '+') occur++;
		}
	}
	
	public Polynomial add (Polynomial input) {
		int unique_power = this.exponents.length + input.exponents.length;
		double [] raw_coefficients = new double [this.exponents.length + input.exponents.length];
		int [] raw_exponents = new int [this.exponents.length + input.exponents.length];
		for (int i = 0; i < unique_power; i++) {
			raw_coefficients[i] = -1;
			raw_exponents[i] = -1;
		}	
		for (int i = 0; i < unique_power; i++) {
			if (i < this.exponents.length) {
				for (int j = 0; j < unique_power; j++) {
					if (raw_exponents[j] == -1) {
						raw_exponents[j] = this.exponents[i];
						raw_coefficients[j] = this.coefficients[i];
						break;
					}
					else if (raw_exponents[j] == this.exponents[i]) {
						raw_coefficients[j] += this.coefficients[i];
						break;
					}
				}
			}
			else {
				int index = i - this.exponents.length;
				for (int j = 0; j < unique_power; j++) {
					if (raw_exponents[j] == -1) {
						raw_exponents[j] = input.exponents[index];
						raw_coefficients[j] = input.coefficients[index];
						break;
					}
					else if (raw_exponents[j] == input.exponents[index]) {
						raw_coefficients[j] += input.coefficients[index];
						break;
					}
				}
			}	
		}
		int size = 0;
		for (int i = 0; i < unique_power; i++) {
			if (raw_exponents[i] == -1) break;
			if (raw_coefficients[i] != 0)  size++;
		}
		double [] real_coefficients = new double[size];
		int [] real_exponents = new int[size];
		int index = 0;
		for (int i = 0; i < unique_power; i++) {
			if (raw_exponents[i] == -1) break;
			if (raw_coefficients[i] != 0) {
				real_coefficients[index] = raw_coefficients[i];
				real_exponents[index] = raw_exponents[i];
				index++;
			}
		}
		Polynomial result = new Polynomial (real_coefficients, real_exponents);
		return result;
	}
			
	public double evaluate(double x) {
		double count = 0;
		for (int i = 0; i<coefficients.length; i++) {
			count += coefficients[i]*Math.pow(x,exponents[i]);
		}
		return count;
	}

	public boolean hasRoot (double input) {
		double output = this.evaluate(input);
		if (output == 0) return true;
		return false;
	}
	
	public Polynomial multiply (Polynomial input) {
		Polynomial ans = new Polynomial();
		for (int i = 0; i < this.coefficients.length; i++) {
			double[] coef = new double [input.coefficients.length];
			int[] exp = new int [input.exponents.length];
			for (int j = 0; j < input.coefficients.length; j++) {
				coef[j] = this.coefficients[i] * input.coefficients[j];
				exp[j] = this.exponents[i] + input.exponents[j];
			}
			Polynomial to_add = new Polynomial(coef, exp);
			ans = ans.add(to_add);
		}
		return ans;
	} 
	
	public void saveToFile (String fileName) throws IOException {
		String output = "";
		
		for (int i = 0; i < coefficients.length; i++) {
			if (i == 0) {
				if (exponents[i] == 0) {
					output += Double.toString(coefficients[i]);
				}
				else {
					output += Double.toString(coefficients[i]) + 'x' + Integer.toString(exponents[i]);
				}
			}
			else {
				if (coefficients[i] > 0) {
					output += '+' + Double.toString(coefficients[i]) + 'x' + Integer.toString(exponents[i]);
				}
				else {
					output += Double.toString(coefficients[i]) + 'x' + Integer.toString(exponents[i]);
				}
			}	
		}
		
		Path tp = Paths.get(fileName);
		String tps = tp.toAbsolutePath().toString();
		Path ap = Paths.get(tps);
		try{
			Files.writeString(ap, output, StandardCharsets.UTF_8);
		}
		catch(IOException e){
            		e.printStackTrace();
        	}	
	}	
	
}
