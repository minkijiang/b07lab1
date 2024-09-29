import java.lang.Math;
import java.util.Scanner;
import java.lang.Double;
import java.lang.Integer;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class Polynomial {
    double[] polynomialCoefficients; //has to be non zero
    int[] polynomialExponents;

    public Polynomial() {
        this.polynomialCoefficients = new double[1];
        this.polynomialCoefficients[0] = 0;

        this.polynomialExponents = new int[1];
        this.polynomialExponents[0] = 0;

    }

    public Polynomial(double[] polynomialCoefficients, int[] polynomialExponents) {
        this.polynomialCoefficients = polynomialCoefficients;
        this.polynomialExponents = polynomialExponents;
    }

    public Polynomial(File polynomial) {
        try {
            Scanner s = new Scanner(polynomial);

            String polynomialLine = s.nextLine();
            polynomialLine += "+";

            Polynomial update = new Polynomial();

            String term;
            String[] termSplit;

            double coefficient;
            int exponent;

            int k = 0;

            for (int i = 1; i < polynomialLine.length(); i++) {
                if (polynomialLine.charAt(i)=='+' || polynomialLine.charAt(i)=='-') {
                    termSplit = polynomialLine.substring(k,i).split("x");


                    if (termSplit[0].substring(1).equals("")) {
                        coefficient = 1;
                    }
                    else if (k == 0 && termSplit[0].charAt(0)!='-' ){
                        coefficient = Double.parseDouble(termSplit[0]);
                    }
                    else {
                        coefficient = Double.parseDouble(termSplit[0].substring(1));
                    }

                    if (termSplit[0].charAt(0)=='-') {
                        coefficient *= -1;
                    }

                    if (termSplit.length == 1 &&
                            polynomialLine.substring(k,i).charAt(polynomialLine.substring(k,i).length()-1)=='x') {
                        exponent = 1;
                    }
                    else if (termSplit.length == 1) {
                        exponent = 0;
                    }
                    else {
                        exponent = Integer.parseInt(termSplit[1]);
                    }


                    update = update.add(createTerm(coefficient,exponent));
                    k = i;
                }
            }

            this.polynomialCoefficients = update.polynomialCoefficients;
            this.polynomialExponents = update.polynomialExponents;
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }






    }

    private Polynomial createTerm(double coefficient, int exponent) {
        double[] coefficients = new double[1];
        coefficients[0] = coefficient;

        int[] exponents = new int[1];
        exponents[0] = exponent;

        Polynomial polynomial = new Polynomial(coefficients, exponents);

        return polynomial;
    }


    public Polynomial add(Polynomial polynomial) {

        ArrayList<Double> coefficientArray = new ArrayList<Double>();
        ArrayList<Integer> exponentArray = new ArrayList<Integer>();

        for (int i = 0; i < this.polynomialExponents.length; i++) {
            coefficientArray.add(this.polynomialCoefficients[i]);
            exponentArray.add(this.polynomialExponents[i]);
        }

        int index;

        for (int i = 0; i < polynomial.polynomialExponents.length; i++) {
            index = exponentArray.indexOf(polynomial.polynomialExponents[i]);
            if (index==-1) {
                coefficientArray.add(polynomial.polynomialCoefficients[i]);
                exponentArray.add(polynomial.polynomialExponents[i]);

            }
            else if (coefficientArray.get(index)+polynomial.polynomialCoefficients[i] == 0){
                coefficientArray.remove(index);
                exponentArray.remove(index);
            }
            else {
                coefficientArray.set(index,
                        coefficientArray.get(index)+polynomial.polynomialCoefficients[i]);
            }
        }

        double[] coefficientList = new double[exponentArray.size()];
        int[] exponentList = new int[exponentArray.size()];

        for (int i = 0; i < exponentArray.size(); i++) {
            coefficientList[i] = coefficientArray.get(i);
            exponentList[i] = exponentArray.get(i);
        }

        Polynomial polynomialSum = new Polynomial(coefficientList, exponentList);

        return polynomialSum;
    }


    public Polynomial Multiply(Polynomial polynomial) {
        if (this.polynomialExponents.length == 1) {
            return singleTermMultiply(this.polynomialCoefficients[0],
                    this.polynomialExponents[0], polynomial);
        }
        else if (polynomial.polynomialExponents.length == 1){
            return singleTermMultiply(polynomial.polynomialCoefficients[0],
                    polynomial.polynomialExponents[0], this);
        }

        Polynomial subPolynomial1 = singleTermMultiply(this.polynomialCoefficients[0],
                this.polynomialExponents[0], polynomial);


        Polynomial subPolynomial2 = this.add(createTerm((-1)*this.polynomialCoefficients[0],
                this.polynomialExponents[0]));



        return subPolynomial1.add(subPolynomial2.Multiply(polynomial));

    }


    private Polynomial singleTermMultiply(double coefficient, int exponent,Polynomial polynomial) {


        double[] coefficients = new double[polynomial.polynomialExponents.length];
        int[] exponents = new int[polynomial.polynomialExponents.length];

        for (int i = 0; i < polynomial.polynomialExponents.length; i++) {
            exponents[i] = polynomial.polynomialExponents[i] + exponent;
            coefficients[i] = polynomial.polynomialCoefficients[i] * coefficient;
        }

        Polynomial newPolynomial = new Polynomial(coefficients, exponents);

        return newPolynomial;

    }



    public double evaluate(double x) {
        double sum = 0;
        for (int i = 0; i < this.polynomialExponents.length; i++) {
            sum += this.polynomialCoefficients[i] * (Math.pow(x,this.polynomialExponents[i]));
        }
        return sum;
    }

    public boolean hasRoot(double x) {
        return evaluate(x)==0;
    }

    public void saveToFile(String filename) {
        String polynomial = "";

        for (int i = 0; i < this.polynomialExponents.length; i++) {
            if (this.polynomialCoefficients[i] < 0) {
                polynomial += "-";
            }
            else {
                polynomial += "+";
            }

            if (this.polynomialCoefficients[i] != 1) {
                polynomial += Double.toString(Math.abs(this.polynomialCoefficients[i]));
            }

            if (this.polynomialExponents[i] > 1) {
                polynomial += "x" + Integer.toString(Math.abs(this.polynomialExponents[i]));
            }
            else if (this.polynomialExponents[i] == 1) {
                polynomial += "x";
            }
        }

        if (polynomial.charAt(0)=='+') {
            polynomial = polynomial.substring(1);
        }

        try {
            FileWriter writefile = new FileWriter(filename);
            writefile.write(polynomial);
            writefile.close();

        }
        catch (Exception e){
            e.printStackTrace();

        }



    }

	/*
	public void test() {
		String polynomial = "";

		for (int i = 0; i < this.polynomialExponents.length; i++) {
			if (this.polynomialCoefficients[i] < 0) {
				polynomial += "-";
			}
			else {
				polynomial += "+";
			}

			if (this.polynomialCoefficients[i] != 1) {
				polynomial += Double.toString(Math.abs(this.polynomialCoefficients[i]));
			}

			if (this.polynomialExponents[i] > 1) {
				polynomial += "x" + Integer.toString(Math.abs(this.polynomialExponents[i]));
			}
			else if (this.polynomialExponents[i] == 1) {
				polynomial += "x";
			}
		}

		if (polynomial.charAt(0)=='+') {
			polynomial = polynomial.substring(1);
		}

		System.out.println(polynomial);
	}
	*/
}