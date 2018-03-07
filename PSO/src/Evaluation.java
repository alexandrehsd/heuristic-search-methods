import static java.lang.Math.pow;

/*
This class is responsible to evaluate the current particles' positions and 
manage the global best value reached by the nth particle at an instant t.
*/
public class Evaluation {
    //Declaring the global best variable (Gbest)
    private static double[] Gbest = new double[2];
    
    
    //From a given position in the plane, evaluation is based on Rosenbrock's function,
    //where the paramenters a and b are 1 and 100, respectively.
    public static double Evaluate(double[] pos){
        return pow((1-pos[0]),2) + 100*pow((pos[1] - pow(pos[0],2)),2);
    }
    
    //Updating the Gbest based in the pbest of a particle
    public static void Gbest_update(double[] pbest){
        if( Evaluation.Evaluate(pbest) < Evaluation.Evaluate(Evaluation.Gbest)){
            Evaluation.setGbest(pbest);
        }
    }

    //Setter and Getter for Gbest variable
    public static void setGbest(double[] Gbest) {
        Evaluation.Gbest = Gbest;
    }

    public static double[] getGbest() {
        return Gbest;
    }
    
    
}
