
/*
*This class is responsible to manage the coefficients C1, C2, Ro1, Ro2 and w in the equation
    v(n+1) = w*v(n) + C1*Ro1*(pbest(n) - position(n)) + C2*Ro2*(Gbest(n) - position(n))
Reminder: This equation is applied in all dimensions.

where:
C1 is the acceleration constant for the cognitive component
C2 is the acceleration constant for the social component
Ro1 and Ro2 are the stochastic components of the algorithm, e. g., a random value in the interval [0,1]
w is the inertial coefficient
*/
public final class Coeff {
    //Declaring all coeffs as class' variables
    private static double C1, C2;
    private static double w;
    private static double ro1, ro2;

    //Setters and getters
    public static double getC1() {
        return C1;
    }

    public static void setC1(double C1) {
        Coeff.C1 = C1;
    }

    public static double getC2() {
        return C2;
    }

    public static void setC2(double C2) {
        Coeff.C2 = C2;
    }

    public static double getW() {
        return w;
    }

    public static void setW(double w) {
        Coeff.w = w;
    }

    public static double getRo1() {
        return ro1;
    }

    public static void setRo1(double ro1) {
        Coeff.ro1 = ro1;
    }

    public static double getRo2() {
        return ro2;
    }

    public static void setRo2(double ro2) {
        Coeff.ro2 = ro2;
    }
    
    
}
