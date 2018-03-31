package ag;

import java.util.Random;


public final class Operator {
    
    //Seleção para crossover -> Torneio
    public static Chromosome[] selection(Chromosome pop[], int k){
        //Supondo que sempre serão selecionados 18/20 indivíduos, temos que
        //k = pop.length-2 deve ter sempre esse valor.
         
        //Ordenação em ordem crescente de fitness do vetor de população
        Sort.BubbleSort(pop);
        
        //Iniciando o torneio
        Random rand = new Random();
        
        //vetor de guardará os cromossomos selecionados
        Chromosome[] selected = new Chromosome[k];
        
        //Índices dos cromossomos do vetor da população que serão utilizados
        //para o torneio
        int crom1, crom2, crom3;
        
        //Variáveis que vão guardar os valores de fitness dos lutadores
        double f1, f2, f3;
        for(int i=0;i<pop.length-2;i++){
            //Gerando um índice aleatório de 0 a 17
            crom1 = rand.nextInt(18);
            crom2 = rand.nextInt(18);
            crom3 = rand.nextInt(18);
            //atribuindo o valor de fitness de cada lutador
            f1 = pop[crom1].fitness();
            f2 = pop[crom2].fitness();
            f3 = pop[crom3].fitness();
            
            //Atribuindo o vencedor à posição i do vetor de selected
            if(f1 >= f2 && f1 >= f3){
                selected[i] = pop[crom1];
            } else if (f2 >= f1 && f2 >= f3) {
                selected[i] = pop[crom2];
            } else if (f3 >= f1 && f3 >= f2){
                selected[i] = pop[crom3];
            }
        }
        
        return selected;
    }
    
    //Este método é responsável por aplicar uma mutação a um cromossomo A
    //selecionando um bit aleatório do seu gene e invertendo seu valor
    public static Chromosome mutation(Chromosome A){
        Random rand = new Random();
        int index = rand.nextInt(12);
        if(A.getBit(index) == 0){
            A.setBit(index, 1);
        } else {
            A.setBit(index, 0);
        }
        return A;   
    }
    
    //Este método é responsável pelo cruzamento entre os indivíduos selecionados
    //Inicialmente, os indivíduos são escolhidos pelo método selection
    public static Chromosome[] crossover(Chromosome selected[]){
        
        //Vetor de cromossomos auxiliar que vai guardar os novos indivíduos gerados
        Chromosome sons[] = new Chromosome[selected.length];
        //Inicializando cada índice do vetor
        for(int i=0;i<selected.length;i++){
            sons[i] = new Chromosome();
        }
        
        //O cruzamento é feito aos pares, entre os indivíduos,
        //por exemplo, individuo 0 com individuo 1, individuo 2 com
        //o individuo 3 e assim por diante...
        
        //O cruzamento toma a forma
        //[001010110101] x [011101100001] pais
        //[001110100001] x [011001110101] filhos
        for(int i=0;i<selected.length; i=i+2){
            //Cruzando os bits responsáveis pela distância [0,3]
            sons[i].setBits(0, 1, selected[i].getBits(0, 1));
            sons[i+1].setBits(0, 1, selected[i+1].getBits(0, 1));

            sons[i].setBits(2, 3, selected[i+1].getBits(2, 3));
            sons[i+1].setBits(2, 3, selected[i].getBits(2, 3));

            //Cruzando os bits responsáveis pelo rating [4,7]
            sons[i].setBits(4, 5, selected[i].getBits(4, 5));
            sons[i+1].setBits(4, 5, selected[i+1].getBits(4, 5));

            sons[i].setBits(6, 7, selected[i+1].getBits(6, 7));
            sons[i+1].setBits(6, 7, selected[i].getBits(6, 7));

            //Cruzando os bits responsáveis pelo preço [8,9]
            sons[i].setBit(8, selected[i].getBit(8));
            sons[i+1].setBit(8, selected[i+1].getBit(8));

            sons[i].setBit(9, selected[i+1].getBit(9));
            sons[i+1].setBit(9, selected[i].getBit(9));

            //Cruzando os bits responsáveis pelo horário de funcionamento [10,11]
            sons[i].setBit(10, selected[i].getBit(10));
            sons[i+1].setBit(10, selected[i+1].getBit(10));

            sons[i].setBit(11, selected[i+1].getBit(11));
            sons[i+1].setBit(11, selected[i].getBit(11));
        }
        
        return sons;
    }
}
