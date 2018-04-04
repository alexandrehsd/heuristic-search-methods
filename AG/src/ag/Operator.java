package ag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public final class Operator {
    
    //Seleção para crossover -> Torneio
    public static Chromosome[] selection(Chromosome pop[]){
        Chromosome[] sel = new Chromosome[18];
        int i1, i2, i3;
        for(int i=0; i<18; i++){
            Random rand = new Random();
            /// SELECIONA 3 INDIVÍDUOS PARA DISPUTAR O TORNEIO i
            i1 = rand.nextInt(pop.length);
            i2 = rand.nextInt(pop.length);
            i3 = rand.nextInt(pop.length);
            if(pop[i1].getFitness() >= pop[i2].getFitness() && pop[i1].getFitness() >= pop[i3].getFitness()){
                sel[i] = pop[i1];
            }
            else if(pop[i2].getFitness() >= pop[i1].getFitness() && pop[i2].getFitness() >= pop[i3].getFitness()){
                sel[i] = pop[i2];
            }
            else{
                sel[i] = pop[i3];
            }
        }
        return sel;
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
            sons[i].setBits(8, 9, selected[i].getBits(8, 9));
            sons[i+1].setBits(8, 9, selected[i+1].getBits(8, 9));

            sons[i].setBits(10, 11, selected[i+1].getBits(10, 11));
            sons[i+1].setBits(10, 11, selected[i].getBits(10, 11));

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
            // SORTEAR QUALQUER ACADEMIA
            ind = rand.nextInt(database.length);
            son = database[ind];
        }
        return son;
    }
}
