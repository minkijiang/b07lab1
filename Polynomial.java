import java.lang.Math;

public class Polynomial {
    double[] polynomialCoefficients;

    public Polynomial() {
        this.polynomialCoefficients = new double[1];
        this.polynomialCoefficients[0] = 0;

    }

    public Polynomial(double[] polynomialCoefficients) {
        this.polynomialCoefficients = polynomialCoefficients;
    }

    public Polynomial add(Polynomial polynomial) {
        Polynomial polynomialSum;
        double[] polynomialToAdd;

        polynomialSum = new Polynomial(this.polynomialCoefficients);
        polynomialToAdd =  polynomial.polynomialCoefficients;
        if (polynomial.polynomialCoefficients.length > this.polynomialCoefficients.length) {
            polynomialSum = new Polynomial(polynomial.polynomialCoefficients);
            polynomialToAdd =  this.polynomialCoefficients;
        }

        for (int i = 0; i < polynomialToAdd.length; i++) {
            polynomialSum.polynomialCoefficients[i] += polynomialToAdd[i];
        }
        return polynomialSum;
    }

    public double evaluate(double x) {
        double sum = 0;
        for (int i = 0; i < this.polynomialCoefficients.length; i++) {
            sum += this.polynomialCoefficients[i] * (Math.pow(x,i));
        }
        return sum;
    }

    public boolean hasRoot(double x) {
        return evaluate(x)==0;
    }
}