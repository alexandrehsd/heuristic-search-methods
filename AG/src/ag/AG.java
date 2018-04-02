package ag;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import org.json.JSONException;

public class AG {
    public static void main(String[] args) throws IOException, MalformedURLException, JSONException {
        // INICIALIZANDO AS VARIÁVEIS
        Chromosome[] pop = new Chromosome[20];
        Chromosome[] sons = new Chromosome[18];
        Chromosome[] elites = new Chromosome[2];
        GymBuilder.Gyms("Rua Alexandrino de Alencar, 1618", "18h00","22h00" , "13h00", "15h00"); // Gera database
        // GERANDO A POPULAÇÃO INICIAL ALEATORIAMENTE
        for(int i=0; i<20; i++){
            pop[i] = new Chromosome();
        }
        // REALIZANDO O CRUZAMENTO E PRODUZINDO FILHOS
        sons = Operator.crossover(Operator.selection(pop));
        
        // OPERAÇÃO DE ELITISMO
        Sort.BubbleSort(pop); // Ordena o vetor de população antiga de modo que os E indivíduos com maior fitness fiquem no final do vetor
        // REALIZANDO MUTAÇÃO
        Random rand = new Random();
        int index = rand.nextInt(18);
        sons[index] = Operator.mutation(sons[index]); // Seleciona um dos filhos gerados para aplicar a mutação
        // SUBSTITUINDO A GERAÇÃO ANTIGA PELA NOVA GERAÇÃO
        for(int i=0; i<18; i++){ // Substitui a população pelos novos filhos
            pop[i] = sons[i]; 
        }
    }
}
    
