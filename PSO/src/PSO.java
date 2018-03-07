/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Scanner;
import java.util.Random;
/**
 *
 * @author alexandre
 */
public class PSO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.print("Enter the number of desired particles: ");
        Scanner Entry = new Scanner(System.in);
        int Nparticles = Entry.nextInt();
        
        Particle[] individuals = new Particle[Nparticles];
        
        
        /**
         * Defining the analysis domain of the function
         * Here, for simplicity, We have random values
         * x = [0,20] and y = [0,20]
         */
        Particle.setRangeMax(20);
        Particle.setRangeMin(0);
        
        //Constructing the individuals
        for(int i=0;i<Nparticles;i++){
            individuals[i] = new Particle();
        }
        
        //Defining the Global Best position
        Evaluation.setGbest(individuals[0].getPbest());
        for(int i=1;i<Nparticles;i++){
            if(Evaluation.Evaluate(individuals[i].getPosition()) <
                    Evaluation.Evaluate(Evaluation.getGbest())){
                Evaluation.setGbest(individuals[i].getPosition());
            }
        }
        
        //Setting the coefficients C1, C2, w
        Coeff.setC1(0.5);
        Coeff.setC2(0.5);
        Coeff.setW(0.8);    
        
        int it = 1;
        while(it < 200){
            
            for(int i=0; i<Nparticles; i++){
                if(Evaluation.Evaluate(individuals[i].getPosition()) < 
                        Evaluation.Evaluate(individuals[i].getPbest())){
                    
                    individuals[i].setPbest(individuals[i].getPosition());
                }
                
                Evaluation.Gbest_update(individuals[i].getPbest());
            }
            
            for(int i=0;i<Nparticles;i++){
                individuals[i].setVelocity();
                individuals[i].setPosition();
            }
            
            it++;
        }
        System.out.println("The global minima is: " + Evaluation.Evaluate(Evaluation.getGbest()));
        //System.out.println("(" + Evaluation.getGbest()[0] + "," + Evaluation.getGbest()[1] + ")");
        
        for(int i=0;i<Nparticles;i++){
            System.out.println("Particle" + i + ": (" + individuals[i].getPosition()[0] +
                    ", "  + individuals[i].getPosition()[1]);
        }
    }
    
}
