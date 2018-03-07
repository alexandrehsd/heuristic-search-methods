/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static java.lang.Math.pow;
/**
 *
 * @author alexandre
 */
public class Evaluation {

    private static double[] Gbest = new double[2];
    
    //Evaluation is based on Rosenbrock's function
    public static double Evaluate(double[] pos){
        return pow((1-pos[0]),2) + 100*pow((pos[1] - pow(pos[0],2)),2);
    }
    
    public static void Gbest_update(double[] pbest){
        if( Evaluation.Evaluate(pbest) < Evaluation.Evaluate(Evaluation.Gbest)){
            Evaluation.setGbest(pbest);
        }
    }

    public static void setGbest(double[] Gbest) {
        Evaluation.Gbest = Gbest;
    }

    public static double[] getGbest() {
        return Gbest;
    }
    
    
}
