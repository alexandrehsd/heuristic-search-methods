package ag;

public class Sort {
    
    public static Chromosome[] BubbleSort(Chromosome pop[]){
        Chromosome[] sorted = new Chromosome[pop.length];
        
        for (int i = 0; i < pop.length; i++) {
            sorted[i] = pop[i];
        }
        
        Chromosome aux;
        for(int i=0; i<sorted.length;i++){
            for(int j=0;j<sorted.length;j++){
                if(sorted[j].getFitness() > sorted[i].getFitness()){
                    aux = sorted[i];
                    sorted[i] = sorted[j];
                    sorted[j] = aux;
                }
            }
        }
        return sorted;
    }
    
    public static void QuickSort(Chromosome pop[], int inicio, int fim){
        if (inicio < fim){
            int posicaoPivo = separar(pop, inicio, fim);
            Sort.QuickSort(pop, inicio, posicaoPivo - 1);
            Sort.QuickSort(pop, posicaoPivo + 1, fim);
        }
    }
    
    private static int separar(Chromosome pop[], int inicio, int fim) {
        Chromosome pivo = pop[inicio];
        int i = inicio + 1, f = fim;
        while (i <= f) {
            if (pop[i].getFitness() <= pivo.getFitness())
                i++;
            else if (pivo.getFitness() < pop[f].getFitness())
                f--;
            else {
                Chromosome troca = pop[i];
                pop[i] = pop[f];
                pop[f] = troca;
                i++;
                f--;
            }
        }
        pop[inicio] = pop[f];
        pop[f] = pivo;
        return f;
    }
}
