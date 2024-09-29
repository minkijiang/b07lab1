public class Driver {
    public static void main(String[] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,5,2};
        int [] e1 = {0,1,3};
        Polynomial p1 = new Polynomial(c1, e1);
        double [] c2 = {10,-2,3};
        int [] e2 = {2,0,1};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        System.out.println("s(1) = " + s.evaluate(1));

        double[] c11 = {2,2};
        int[] e11 = {0,1};
        p1 = new Polynomial(c11, e11);
        double[] c22 = {2,2};
        int[] e22 = {0,1};
        p2 = new Polynomial(c22, e22);
        Polynomial h = p1.Multiply(p2);

        System.out.println("h(1) = " + h.evaluate(1));

    }
}