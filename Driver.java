import java.io.IOException;

public class Driver {
public static void main(String [] args) throws IOException {
		// test constructor
		Polynomial p1 = new Polynomial();
		System.out.println(p1.coefficients.length == 0);
		double a[] ={-1.0, -2.0, 3.0};
		int b[] = {5, 3, 1};
		Polynomial p2 = new Polynomial(a, b);
		double c[] ={-3.0, 2.0, 1.0}; 
		int d[] = {1, 3, 5};
		Polynomial p3 = new Polynomial(c, d);
		// test add
		Polynomial p5 = p2.add(p3);
		System.out.println(p5.coefficients.length == 0);
		// test multiply
		Polynomial p4 = new Polynomial(a, d);
		Polynomial p6 = p4.multiply(p3);
		for(int j = 0; j < p6.coefficients.length; j++){
			System.out.println(p6.coefficients[j] + "x" + p6.exponents[j]);
		}
		// test evaluate
		System.out.println(p6.evaluate(1));
		// test hasRoot
		System.out.println(p6.hasRoot(0));
		// test saveToFile
		String s = "mytext.txt";
		p6.saveToFile(s);
	}
}
