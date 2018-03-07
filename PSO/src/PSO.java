
import java.util.Scanner;
import java.util.Random;

public class PSO {

    public static void main(String[] args) {
        
        System.out.print("Enter the number of particles: ");
        Scanner Entry = new Scanner(System.in);
        
        //Setting the number of particles
        int Nparticles = Entry.nextInt();
        
        //Declaring an array of particles
        Particle[] individuals = new Particle[Nparticles];
        
        /**
         * Defining the range in which the particles' position will be generated.
         * Here, for simplicity, We have random values
         * x = [0,20] and y = [0,20]
         */
        Particle.setRangeMax(20);
        Particle.setRangeMin(0);
        
        //Constructing each individual (particle)
        for(int i=0;i<Nparticles;i++){
            individuals[i] = new Particle();
        }
        
        //Defining the Global Best position based on the pbest values so far
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
        
        //Counter for the iterations
        int it = 1;
        
        while(it < 230){
            
            /*Evaluation of the current position of the particles based
              on Rosenbrock's function, Updating for each particle his best position and
              Updating the global best reached so far.
            */
            for(int i=0; i<Nparticles; i++){
                if(Evaluation.Evaluate(individuals[i].getPosition()) < 
                        Evaluation.Evaluate(individuals[i].getPbest())){
                    
                    individuals[i].setPbest(individuals[i].getPosition());
                }
                
                Evaluation.Gbest_update(individuals[i].getPbest());
            }
            
            //Setting the new position and velocity of each particle
            for(int i=0;i<Nparticles;i++){
                individuals[i].setVelocity();
                individuals[i].setPosition();
            }
            
            //incrementing the counter
            it++;
        }
        
        System.out.printf("The global minima is approximately: %.5f \n", Evaluation.Evaluate(Evaluation.getGbest()));
        System.out.printf("and it occurs in the point (%.4f.%.4f)",Evaluation.getGbest()[0],Evaluation.getGbest()[1] );
        
        /*By decommentig this code, one can see the position of each particle at the end of the program
        for(int i=0;i<Nparticles;i++){
            System.out.println("Particle " + i + ": (" + individuals[i].getPosition()[0] +
                    ", "  + individuals[i].getPosition()[1]);
        }
        */
    }
    
}
