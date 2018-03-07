
import java.util.Random;

/*
This class declares and manage the properties of the particles, more specifically,
their position, velocity and best position reached at an instant t.
*/

public class Particle {
    //Intrinsec variables of each particle
    private double[] position = new double[2];
    private double[] velocity = new double[2];
    private double[] pbest = new double[2];
    /*
    These variables defines the range from which the particles
    can be randomly created in the plane x,y.
    */
    private static int RangeMin;
    private static int RangeMax;
    
    //Constructor of particles
    public Particle(){
        Random rand = new Random();
        for(int i=0;i<2;i++){
            this.position[i] = RangeMin + (RangeMax - RangeMin)*rand.nextDouble();
            this.velocity[i] = 10*rand.nextDouble();
            this.pbest[i] = this.position[i];
        }
    }
    
    //Getter for the particle's position and velocity
    public double[] getPosition() {
        return position;
    }

    public double[] getVelocity() {
        return velocity;
    }
    
    //Setter for the particle's position based in the equation pos(n+1) = pos(n) + vel(n)
    public void setPosition() {
        this.position[0] = this.position[0] + this.velocity[0];
        this.position[1] = this.position[1] + this.velocity[1];
    }

    /*
    Setter for the particle's velocity. Reminder: for each dimension,
    v(n+1) = w*v(n) + C1*Ro1*(pbest(n) - position(n)) + C2*Ro2*(Gbest(n) - position(n)),
    where Ro1 and Ro2 are randomly generated for each particle iteration.
    */
    public void setVelocity() {
        Random rand = new Random();
        for(int i=0;i<2;i++){
            Coeff.setRo1(rand.nextDouble());
            Coeff.setRo2(rand.nextDouble());
            this.velocity[i] = Coeff.getW()*this.velocity[i] +
                            Coeff.getC1()*Coeff.getRo1()*(this.pbest[i] - this.position[i]) +
                            Coeff.getC2()*Coeff.getRo2()*(Evaluation.getGbest()[i] - this.position[i]);
        }
    }

    //Setters and Getters for Pbest, RangeMin and RangeMax
    public double[] getPbest() {
        return pbest;
    }

    public void setPbest(double[] pbest) {
        this.pbest = pbest;
    }

    public static void setRangeMin(int RangeMin) {
        Particle.RangeMin = RangeMin;
    }

    public static void setRangeMax(int RangeMax) {
        Particle.RangeMax = RangeMax;
    }

    public int getRangeMin() {
        return RangeMin;
    }

    public int getRangeMax() {
        return RangeMax;
    }
    
    
}
