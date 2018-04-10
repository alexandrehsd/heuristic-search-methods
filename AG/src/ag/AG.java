package ag;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;

public class AG {
    public static void main(String[] args) throws IOException, MalformedURLException, JSONException {
        // INICIALIZANDO AS VARIÁVEIS
        int iteracao = 0;
        Chromosome[] database = new Chromosome[76];
        
        System.out.printf("Descreva qual o valor máximo que você pode pagar, mensalmente, em uma academia: ");
        Scanner input1 = new Scanner(System.in);
        int price = input1.nextInt();
        Chromosome.setpmax(price);
        
        System.out.printf("Descreva com um número inteiro de 1 a 5, o quanto você se importa com a "
            + "avaliação dos demais usuários acerca das academias: ");
        Scanner input2 = new Scanner(System.in);
        int rat = input2.nextInt();
        
        System.out.printf("\nPor favor, Entre com o seu endereço: ");
        Scanner input3 = new Scanner(System.in);
        String end = input3.nextLine();
        
        System.out.printf("\nIndique qual o horário mínimo em que você está disponível para "
                + "malhar durante a semana, no formato XXhYY: ");
        Scanner input4 = new Scanner(System.in);
        String minw = input4.nextLine();
        
        System.out.printf("\nIndique qual o horário máximo em que você está disponível para "
                + "malhar durante a semana, no formato XXhYY: ");
        Scanner input5 = new Scanner(System.in);
        String maxw = input5.nextLine();
        
        System.out.printf("\nIndique qual o horário mínimo em que você está disponível para "
                + "malhar durante o fim de semana, no formato XXhYY: ");
        Scanner input6 = new Scanner(System.in);
        String minwe = input6.nextLine();
        
        System.out.printf("\nIndique qual o horário máximo em que você está disponível para "
                + "malhar durante o fim de semana, no formato XXhYY: ");
        Scanner input7 = new Scanner(System.in);
        String maxwe = input7.nextLine();
        
        Chromosome.setCr(rat);
        System.arraycopy(GymBuilder.Gyms(end, minw, maxw, minwe, maxwe), 
                0, database, 0, 76);

        System.out.println("BANCO DE DADOS");
        for(int i=0; i<76; i++){
            database[i].show();
            System.out.println("Fitness da academia " + (i+1) + " do banco de dados: " + database[i].getFitness());
        }
        
        while(iteracao<=5){
            int it=0;
            Chromosome[] pop = new Chromosome[20];
            Chromosome[] sons = new Chromosome[18];
            Chromosome[] elites = new Chromosome[2];
            Chromosome[] aux = new Chromosome[20];
            // GERANDO A POPULAÇÃO INICIAL ALEATORIAMENTE
            for(int i=0; i<20; i++){
                pop[i] = new Chromosome();
                pop[i] = Operator.validate(pop[i], database);
                pop[i].setFitness();
            }
       
        while(it<50){
           
            // OPERAÇÃO DE CRUZAMENTO
            System.arraycopy(Operator.crossover(Operator.selection(pop)), 0, sons, 0, 18);
            for (int i=0;i<sons.length;i++){ // Faz a validação dos filhos
                sons[i] = Operator.validate(sons[i], database);
                sons[i].setFitness();
            }

            // OPERAÇÃO DE ELITISMO
            aux = Sort.BubbleSort(pop);
            elites[0] = aux[18];
            elites[1] = aux[19];
           
            // SUBSTITUINDO A GERAÇÃO
            for(int i=0; i<18; i++){
                pop[i] = sons[i];
            }
            pop[18] = elites[0];
            pop[19] = elites[1];
            
            // REALIZANDO MUTAÇÃO
//            Random rand = new Random();
//            int index = rand.nextInt(20);
//            pop[index] = Operator.mutation(sons[index]);
//            pop[index].setBits (0, 13, Operator.validate(pop[index], database).getBits(0, 13));
//            pop[index].setFitness();
            
            it++;
        }
        pop = Sort.BubbleSort(pop);
        System.out.println("");
        System.out.println("Fitness da melhor academia: " + pop[19].getFitness());
        
        int i;
        for(i=0;i<76;i++){
            if(Arrays.equals(pop[19].getGenCode(), database[i].getGenCode())){
                break;
            }
        }
        
        String placeid = Files.readAllLines(Paths.get("/home/alexandre/Documentos/GitHub/AI-Algorithms/AG/placeids.txt")).get(i);
        
        String s = "https://maps.googleapis.com/maps/api/place/details/json?"
                + "key=AIzaSyCwYJvP0YHepfjOIiz0orFYjzlxn6AJrOQ&placeid=" + placeid;
        String str = "";
            
        //criando uma url com a string concatenada
        URL url = new URL(s);
            
        //criando um objeto scanner para receber o arquivo gerado pela url
        Scanner scan = new Scanner(url.openStream());
            
        //concatenando todas as linhas do arquivo na string str
        while (scan.hasNext()) {
            str += scan.nextLine();
        }
        scan.close();
            
        JSONObject obj = new JSONObject(str);
        System.out.println(obj.getJSONObject("result").getString("name"));
        
        System.out.println(obj.getJSONObject("result").getString("formatted_address"));
        //System.out.println(obj.getJSONObject("result").getString("website"));
        
        iteracao++;
        }
    }
}
