package ag;

public class Sort {
    
    public static void BubbleSort(Chromosome pop[]){
        Chromosome aux;
        for(int i=0; i<pop.length;i++){
            for(int j=0;j<pop.length;j++){
                if(pop[j].fitness() < pop[i].fitness()){
                    aux = pop[i];
                    pop[i] = pop[j];
                    pop[j] = aux;
                }
            }
        }
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
            if (pop[i].fitness() <= pivo.fitness())
                i++;
            else if (pivo.fitness() < pop[f].fitness())
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
