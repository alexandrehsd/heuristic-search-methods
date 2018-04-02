package ag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public final class Operator {
    
    //Seleção para crossover -> Torneio
    public static Chromosome[] selection(Chromosome pop[]){
        double soma =0;
        Chromosome[] selected = new Chromosome[pop.length-2];
        for (int i = 0; i < selected.length; i++) {
            selected[i] = new Chromosome();
        }
        
        for(int i=0;i<pop.length; i++){
            soma += pop[i].getFitness();
        }
        
        double[] aux = new double[pop.length];
        
        for(int i=0;i<pop.length; i++){
            aux[i] = 1000*pop[i].getFitness()/soma;
        }
        
        Random generator = new Random();
        
        int k = pop.length-2;
        for(int i=0;i<k; i++){
            int pos = 0;
            int aleatorio = generator.nextInt(1000);
            double somaAux = 0;
            while(somaAux <= aleatorio){
                somaAux += aux[pos];
                pos++;
            }
            pos--;
            selected[i] = pop[pos];
            
        }
        return selected;

        
        //Supondo que sempre serão selecionados 18/20 indivíduos, temos que
        //k = pop.length-2 deve ter sempre esse valor.
         
        //Ordenação em ordem crescente de fitness do vetor de população
        //Sort.BubbleSort(pop);
        
        //Iniciando o torneio
//        Random rand = new Random();
//        
//        //vetor de guardará os cromossomos selecionados
//        Chromosome[] selected = new Chromosome[pop.length-2];
//        
//        //Índices dos cromossomos do vetor da população que serão utilizados
//        //para o torneio
//        int crom1, crom2, crom3;
//        
//        //Variáveis que vão guardar os valores de fitness dos lutadores
//        double f1, f2, f3;
//        for(int i=0;i<pop.length-2;i++){
//            //Gerando um índice aleatório de 0 a 19
//            crom1 = rand.nextInt(20);
//            crom2 = rand.nextInt(20);
//            crom3 = rand.nextInt(20);
//            //atribuindo o valor de fitness de cada lutador
//            f1 = pop[crom1].getFitness();
//            f2 = pop[crom2].getFitness();
//            f3 = pop[crom3].getFitness();
//            
//            //Atribuindo o vencedor à posição i do vetor de selected
//            if(f1 >= f2 && f1 >= f3){
//                selected[i] = pop[crom1];
//            } else if (f2 >= f1 && f2 >= f3) {
//                selected[i] = pop[crom2];
//            } else if (f3 >= f1 && f3 >= f2){
//                selected[i] = pop[crom3];
//            }
//        }
//        
//        return selected;
    }
    
    //Este método é responsável por aplicar uma mutação a um cromossomo A
    //selecionando um bit aleatório do seu gene e invertendo seu valor
    public static Chromosome mutation(Chromosome A){
        Random rand = new Random();
        int index = rand.nextInt(14);
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
            sons[i].setBits(8, 11, selected[i].getBits(8, 11));
            sons[i+1].setBits(8, 11, selected[i+1].getBits(8, 11));

            sons[i].setBits(8, 11, selected[i+1].getBits(8, 11));
            sons[i+1].setBits(8, 11, selected[i].getBits(8, 11));

            //Cruzando os bits responsáveis pelo horário de funcionamento [10,11]
            sons[i].setBit(12, selected[i].getBit(12));
            sons[i+1].setBit(12, selected[i+1].getBit(12));

            sons[i].setBit(13, selected[i+1].getBit(13));
            sons[i+1].setBit(13, selected[i].getBit(13));
        }   
        return sons;
    }
    
    public static Chromosome validate(Chromosome son, Chromosome[] database){
        int[] aux = new int[database.length];
        for(int i=0; i<database.length; i++){
            aux[i] = 0;
            if(Arrays.equals(database[i].getBits(0, 3), son.getBits(0, 3))){
                aux[i]+=5;
            }
            if(Arrays.equals(database[i].getBits(4, 7), son.getBits(4, 7))){
                aux[i]+=1;
            }
            if(Arrays.equals(database[i].getBits(8, 11), son.getBits(8, 11))){
                aux[i]+=3;
            }
            if(Arrays.equals(database[i].getBits(12, 13), son.getBits(12, 13))){
                aux[i]+=7;
            }
        }
        List<Integer> indexes3 = new ArrayList<>();
        List<Integer> indexes2 = new ArrayList<>();
        List<Integer> indexes1 = new ArrayList<>();
        for(int i=0; i<database.length; i++){
            if(aux[i]==16){
                return database[i];
            }else if(aux[i]==15 || aux[i]==13 || aux[i]==11 || aux[i]==9){
                indexes3.add(i);
            }else if(aux[i]==12 || aux[i]==10 || aux[i]==8 || aux[i]==6 || aux[i]==4){
                indexes2.add(i);
            }else if(aux[i]==5 || aux[i]==1 || aux[i]==3 || aux[i]==7){
                indexes1.add(i);
            }
        }
        Random rand = new Random();
        int ind;
        if(!(indexes3.isEmpty())){
            // SORTEAR ALGUMA ACADEMIA COM 3 PARAMETROS
            ind = rand.nextInt(indexes3.size());
            son = database[indexes3.get(ind)];
        }
        else if(!(indexes2.isEmpty())){
            // SORTEAR ALGUMA ACADEMIA COM 2 PARAMETROS
            ind = rand.nextInt(indexes2.size());
            son = database[indexes2.get(ind)];
        }
        else if(!(indexes1.isEmpty())){
            // SORTEAR ALGUMA ACADEMIA COM 1 PARAMETRO
            ind = rand.nextInt(indexes1.size());
            son = database[indexes1.get(ind)];
        } else{
            int[] data = new int[]{1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1};
            son.setBits(0, 13, data);
            son.setFitness();
        }
        return son;
    }
}
