
package ag;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class AG {
    public static void main(String[] args) {
        System.out.printf("Descreva com um inteiro de 1 a 5 o quanto você leva em conta\nas avaliações "
                + "dadas pelos outros usuários sobre as academias: ");
        
        Scanner input = new Scanner(System.in);
        
        //Entrando com a variável que determinará o peso das avaliações na função fitness
        int rweight = input.nextInt();
        Chromosome.setCr(rweight);
        
        System.out.printf("\nPor favor, indique a faixa de preços que você estaria disposto a pagar\n"
                + "mensalmente, entrando com o valor máximo e o mínimo, respectivamente.\nMax: ");
        
        //Entrando com as variáveis que vão determinar Cp na função de avaliação
        int maxp = input.nextInt();
        System.out.printf("Min: ");
        int minp = input.nextInt();
        Chromosome.setCp(maxp, minp);
        
        
//        
//        
//        int N = 20; //Número de indivíduos
//        Chromosome[] pop = new Chromosome[N]; //Vetor de indivíduos -> população
//       
//        //Inicializando cada cromossomo da população de cromossomos
//        for(int i=0;i<pop.length;i++){
//            pop[i] = new Chromosome();
//        }
        
//        //Mostrando alguns cromossomos
//        pop[0].show();
//        pop[1].show();
//        pop[2].show();
//        //Mostrando o fitness de cada um
//        System.out.println(pop[0].fitness());
//        System.out.println(pop[1].fitness());
//        System.out.println(pop[2].fitness());

//        
//        //vetores auxiliares para seleção e crossover
//        Chromosome[] selected;
//        Chromosome[] sons;
//         
//        //ordenando a população para definir quem são os 2 melhores indivíduos
//        //que passarão para a próxima geração
//        Sort.BubbleSort(pop);
//        
//        //variável responsável por limitar o número de iterações
//        int iterations = 0;
//        //Variável randômica utilizada para selecionar o indivíduo que sofrerá mutação 
//        Random rand = new Random();
//        while(iterations < 40){
//            //Seleciona os indivíduos para crossover, exceto os 2 melhores indivíduos
//            selected = Operator.selection(pop, pop.length-2);
//            
//            //Cruza os indivíduos que foram selecionados
//            sons = Operator.crossover(selected);
//            
//            //ordenando a população para substituir os N-2 piores indivíduos
//            //pelos novos indivíduos que foram criados no crossover
//            Sort.BubbleSort(pop);
//            
//            //substituindo os N-2 indivíduos piores pelos N-2 gerados
//            System.arraycopy(sons, 0, pop, 0, pop.length-2);
//            
//            //Aplicando mutação a um individuo aleatório entre
//            //tendo o cuidado de não selecionar os individuos 18 e 19
//            //do vetor de população, que têm os melhores fitness
//            Operator.mutation(pop[rand.nextInt(19)]);
//            
//            iterations++;
//        }

    }
}
    
