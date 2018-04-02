package ag;

import java.util.Arrays;
import java.util.Random;
import static java.lang.Math.pow;

public final class Chromosome {
    private int[] GenCode = new int[14];
    private double fitness;
    
    //Variável da classe, não vinculadas a nenhum cromossomo em particular
    //Constantes definidas com base na entrada do usuário
    private static double Cr=1.5, Cp=2;
    
    //Inicializando os cromossomos como um vetor de bits randômicos
    Chromosome(){
        Random rand = new Random();
        for(int i = 0; i<14; i++){
            this.GenCode[i] = rand.nextInt(2);
        }
        this.setFitness();
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
        System.out.println(Arrays.toString(this.GenCode).replaceAll("\\[|\\]|,|\\s", ""));
    }
    
    public static void setCr(int ratweight){
        switch(ratweight){
            case 1:
                Chromosome.Cr = 0.5;
                break;
            case 2:
                Chromosome.Cr = 1.0;
                break;
            case 3:
                Chromosome.Cr = 1.5;
                break;
            case 4:
                Chromosome.Cr = 2.0;
                break;
            case 5:
                Chromosome.Cr = 2.5;
                break;
            default:
                System.out.println("Esta entrada é inválida!");
        }
    }
    
    public static void setCp(int maxp){
        if (maxp < 120){
            Chromosome.Cp = 2.5;
        } else if (maxp < 250){
            Chromosome.Cp = 2;
        } else {
            Chromosome.Cp = 1.25;
        }
    }
    
    public void setFitness(){
        double d, h, r, p;
        //the gencode has a form [ddddrrrrpppphh]
        //d stands for distance, r for rate, p for price and h for hour.
        
        d = this.setd(this.getBits(0, 3));
        r = this.setr(this.getBits(4, 7));
        p = this.setp(this.getBits(8, 11));
        h = this.seth(this.getBits(12, 13));
        
        this.fitness = 7/pow(d,0.75) + 2*pow(h,2) + Chromosome.Cr*pow(r,1.5) + Chromosome.Cp*2/p;
    }
    
    public double getFitness(){
        return this.fitness;
    }
    public double setd(int bits[]){
        double d=2;
        switch(Arrays.toString(bits).replaceAll("\\[|\\]|,|\\s", "")){
            case "0000":
                d = 0.25;
                break;
            case "0001":
                d = 0.5;
                break;
            case "0010":
                d = 0.75;
                break;
            case "0011":
                d = 1.0;
                break;
            case "0100":
                d = 1.25;
                break;
            case "0101":
                d = 1.50;
                break;
            case "0110":
                d = 1.75;
                break;
            case "0111":
                d = 2.0;
                break;
            case "1000":
                d = 2.25;
                break;
            case "1001":
                d = 2.50;
                break;
            case "1010":
                d = 2.75;
                break;
            case "1011":
                d = 3.0;
                break;
            case "1100":
                d = 3.25;
                break;
            case "1101":
                d = 3.50;
                break;
            case "1110":
                d = 3.75;
                break;
            case "1111":
                d = 4.0;
                break;
            default:
                System.out.println("Esta string de distância é inválida");
        }
        return d;
    }
    
    public double setr(int bits[]){
        double r=2;
        switch(Arrays.toString(bits).replaceAll("\\[|\\]|,|\\s", "")){
            case "0000":
                r = 0.25;
                break;
            case "0001":
                r = 0.5;
                break;
            case "0010":
                r = 0.75;
                break;
            case "0011":
                r = 1.0;
                break;
            case "0100":
                r = 1.25;
                break;
            case "0101":
                r = 1.50;
                break;
            case "0110":
                r = 1.75;
                break;
            case "0111":
                r = 2.0;
                break;
            case "1000":
                r = 2.25;
                break;
            case "1001":
                r = 2.50;
                break;
            case "1010":
                r = 2.75;
                break;
            case "1011":
                r = 3.0;
                break;
            case "1100":
                r = 3.25;
                break;
            case "1101":
                r = 3.50;
                break;
            case "1110":
                r = 3.75;
                break;
            case "1111":
                r = 4.0;
                break;
            default:
                System.out.println("Esta string de rating é inválida");
        }
        return r;
    }
    
    public double setp(int bits[]){
        double p=1;
        switch(Arrays.toString(bits).replaceAll("\\[|\\]|,|\\s", "")){
            case "0000":
                p = 0.25;
                break;
            case "0001":
                p = 0.5;
                break;
            case "0010":
                p = 0.75;
                break;
            case "0011":
                p = 1.0;
                break;
            case "0100":
                p = 1.25;
                break;
            case "0101":
                p = 1.50;
                break;
            case "0110":
                p = 1.75;
                break;
            case "0111":
                p = 2.0;
                break;
            case "1000":
                p = 2.25;
                break;
            case "1001":
                p = 2.50;
                break;
            case "1010":
                p = 2.75;
                break;
            case "1011":
                p = 3.0;
                break;
            case "1100":
                p = 3.25;
                break;
            case "1101":
                p = 3.50;
                break;
            case "1110":
                p = 3.75;
                break;
            case "1111":
                p = 4.0;
                break;
            default:
                System.out.println("Esta string de preço é inválida");
        }
        return p;
    }
    public double seth(int bits[]){
        double h=1;
        switch(Arrays.toString(bits).replaceAll("\\[|\\]|,|\\s", "")){
            case "00":
                h = 4.0;
                break;
            case "01":
                h = 3.0;
                break;
            case "10":
                h = 1.5;
                break;
            case "11":
                h = 0;
                break;
            default:
                System.out.println("Esta string de horário é inválida");
        }
        return h;
    }
    
}
