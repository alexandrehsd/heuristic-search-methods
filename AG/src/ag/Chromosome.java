package ag;

import java.util.Random;

public class Chromosome {
    private int[] GenCode = new int[12];
    private double fitness;
    
    //Inicializando os cromossomos como um vetor de bits randômicos
    Chromosome(){
        Random rand = new Random();
        for(int i = 0; i<12; i++){
            this.GenCode[i] = rand.nextInt(2);
        }
    }
    
    //Método destinado à função de avaliação -> não implementado
    public double fitness(){
        Random rand = new Random();
        return rand.nextInt(11);
    }
    
    //Configura um bit no código genético -> Mutação
    public void setBit(int index, int bit){
        this.GenCode[index] = bit;
    }
    
    //Configura uma cadeia de bits no código genético -> crossover
    public void setBits(int min, int max, int bits[]){
        for(int i=min;i<max+1;i++){
            this.GenCode[i] = bits[i-min];
        }
    }
    
    //Retorna um bit do cromossomo localizado no índice index
    public int getBit(int index){
        return this.GenCode[index];
    }
    
    //Retorna uma cadeia de bits do cromossomo, desde o índice min até o max
    public int[] getBits(int min, int max){
        int bits[] = new int[1+max-min];
        for(int i=min;i<max+1;i++){
            bits[i-min] = this.GenCode[i];
        }
        return bits;
    }
    
    //retorna o vetor de código genético
    public int[] getGenCode() {
        return this.GenCode;
    }
    
    //Mostra o código genético de um indivíduo
    public void show(){
        for(int i =0;i<12;i++){
            System.out.printf("%d",this.GenCode[i]);
        }
    }
    
    
}
