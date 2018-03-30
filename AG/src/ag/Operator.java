package ag;

import java.util.Random;


public final class Operator {
    
    //Seleção para crossover -> Torneio
    public static Chromosome[] selection(Chromosome pop[], int k){
        //Supondo que sempre serão selecionados 14/20 indivíduos
        k = pop.length-2;
        //Ordenação em ordem crescente de fitness do vetor de população
        Sort.BubbleSort(pop);
        
        //Iniciando o torneio
        Random rand = new Random();
        Chromosome[] selected = new Chromosome[k];
        int crom1, crom2, crom3;
        double f1, f2, f3;
        for(int i=0;i<pop.length-2;i++){
            crom1 = rand.nextInt(18);
            crom2 = rand.nextInt(18);
            crom3 = rand.nextInt(18);
            f1 = pop[crom1].fitness();
            f2 = pop[crom2].fitness();
            f3 = pop[crom3].fitness();
            
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
    
    public static Chromosome[] crossover(Chromosome selected[]){
        
        Chromosome sons[] = new Chromosome[selected.length];
        for(int i=0;i<selected.length;i++){
            sons[i] = new Chromosome();
        }
        
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
