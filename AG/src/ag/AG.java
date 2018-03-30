
package ag;

public class AG {
    public static void main(String[] args) {
        
        int N = 20; //Número de indivíduos
        Chromosome[] pop = new Chromosome[20]; //Vetor de indivíduos -> população
        
        for(int i=0;i<pop.length;i++){
            pop[i] = new Chromosome();
        }
        
        Chromosome[] selected;
        Chromosome[] sons;
        
        selected = Operator.selection(pop, pop.length-2);
        sons = Operator.crossover(selected);
        
        Sort.BubbleSort(pop);
        System.arraycopy(sons, 0, pop, 0, pop.length-2);
//        father1.show();
//        System.out.printf("%s"," x ");
//        father2.show();
//        
//        System.out.println();
//        
//        sons[0].show();
//        System.out.printf("%s"," x ");
//        sons[1].show();
//        for(int i = 0; i < 20; i++){
//            pop[i] = new Chromosome(); //Inicializando a população
//            System.out.println("Crom " + i + ": ");
//            pop[i].show();
//
//        }
    }
    
}
