
package ag;

import java.util.Random;

public class AG {
    public static void main(String[] args) {
        
        int N = 20; //Número de indivíduos
        Chromosome[] pop = new Chromosome[N]; //Vetor de indivíduos -> população
        
        //Inicializando cada cromossomo da população de cromossomos
        for(int i=0;i<pop.length;i++){
            pop[i] = new Chromosome();
        }
        
        //vetores auxiliares para seleção e crossover
        Chromosome[] selected;
        Chromosome[] sons;
         
        //ordenando a população para definir quem são os 2 melhores indivíduos
        //que passarão para a próxima geração
        Sort.BubbleSort(pop);
        
        //variável responsável por limitar o número de iterações
        int iterations = 0;
        //Variável randômica utilizada para selecionar o indivíduo que sofrerá mutação 
        Random rand = new Random();
        while(iterations < 40){
            //Seleciona os indivíduos para crossover, exceto os 2 melhores indivíduos
            selected = Operator.selection(pop, pop.length-2);
            
            //Cruza os indivíduos que foram selecionados
            sons = Operator.crossover(selected);
            
            //ordenando a população para substituir os N-2 piores indivíduos
            //pelos novos indivíduos que foram criados no crossover
            Sort.BubbleSort(pop);
            
            //substituindo os N-2 indivíduos piores pelos N-2 gerados
            System.arraycopy(sons, 0, pop, 0, pop.length-2);
            
            //Aplicando mutação a um individuo aleatório entre
            //tendo o cuidado de não selecionar os individuos 18 e 19
            //do vetor de população, que têm os melhores fitness
            Operator.mutation(pop[rand.nextInt(19)]);
            
            iterations++;
        }
        
    }
    
}
