/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Random;
/**
 *
 * @author alexandre
 */
public class Particle {
    private double[] position = new double[2];
    private double[] velocity = new double[2];
    
    private static int RangeMin;
    private static int RangeMax;
    
    private double[] pbest = new double[2];
    
    public Particle(){
        Random rand = new Random();
        for(int i=0;i<2;i++){
            this.position[i] = RangeMin + (RangeMax - RangeMin)*rand.nextDouble();
            this.velocity[i] = 10*rand.nextDouble();
            this.pbest[i] = this.position[i];
        }
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition() {
        this.position[0] = this.position[0] + this.velocity[0];
        this.position[1] = this.position[1] + this.velocity[1];
    }

    public double[] getVelocity() {
        return velocity;
    }

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
