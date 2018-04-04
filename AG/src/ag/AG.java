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
        Chromosome[] pop = new Chromosome[20];
        Chromosome[] sons = new Chromosome[18];
        Chromosome[] elites = new Chromosome[2];
        Chromosome[] database = new Chromosome[77];
        
//        System.out.printf("Descreva com um número inteiro de 1 a 5, o quanto você se importa com a "
//                + "avaliação dos demais usuários acerca das academia: ");
//        Scanner input1 = new Scanner(System.in);
//        int rat = input1.nextInt();
//        
//        System.out.printf("\nPor favor, Entre com o seu endereço: ");
//        Scanner input2 = new Scanner(System.in);
//        String end = input2.nextLine();
//        
//        System.out.printf("\nIndique qual o horário mínimo em que você está disponível para "
//                + "malhar durante a semana, no formato XXhYY: ");
//        Scanner input3 = new Scanner(System.in);
//        String minw = input3.nextLine();
//        
//        System.out.printf("\nIndique qual o horário máximo em que você está disponível para "
//                + "malhar durante a semana, no formato XXhYY: ");
//        Scanner input4 = new Scanner(System.in);
//        String maxw = input4.nextLine();
//        
//        System.out.printf("\nIndique qual o horário mínimo em que você está disponível para "
//                + "malhar durante o fim de semana, no formato XXhYY: ");
//        Scanner input5 = new Scanner(System.in);
//        String minwe = input5.nextLine();
//        
//        System.out.printf("\nIndique qual o horário máximo em que você está disponível para "
//                + "malhar durante o fim de semana, no formato XXhYY: ");
//        Scanner input6 = new Scanner(System.in);
//        String maxwe = input6.nextLine();
        
//        Chromosome.setCr(rat);
//        System.arraycopy(GymBuilder.Gyms(end, minw, maxw, minwe, maxwe), 
//                0, database, 0, 77);

        System.arraycopy(GymBuilder.Gyms("Rua Mipibu, 419", "15h00","20h00" , "13h00", "15h00"),
                0, database, 0, 77);
        
        // GERANDO A POPULAÇÃO INICIAL ALEATORIAMENTE
        for(int i=0; i<20; i++){
            pop[i] = new Chromosome();
            pop[i] = Operator.validate(pop[i], database);
            pop[i].setFitness();
        }
        
        int it=0;
        while(it<100){
            // REALIZANDO O CRUZAMENTO E PRODUZINDO FILHOS
            System.arraycopy(Operator.crossover(Operator.selection(pop)), 0, sons, 0, 18);

            System.out.println("");
            for (int i=0;i<sons.length;i++){
                sons[i] = Operator.validate(sons[i], database);
                sons[i].setFitness();
            }
            
            // OPERAÇÃO DE ELITISMO
            Sort.BubbleSort(pop); // Ordena o vetor de população antiga de modo que os E indivíduos com maior fitness fiquem no final do vetor
            // REALIZANDO MUTAÇÃO
            Random rand = new Random();
            int index = rand.nextInt(18);
            sons[index] = Operator.mutation(sons[index]); // Seleciona um dos filhos gerados para aplicar a mutação
            sons[index] = Operator.validate(sons[index], database);
            sons[index].setFitness();
            
            // SUBSTITUINDO A GERAÇÃO ANTIGA PELA NOVA GERAÇÃO
            System.arraycopy(sons, 0, pop, 0, 18); // Substitui a população pelos novos filhos
            it++;
        }
               
//        Sort.BubbleSort(pop);
//        pop[19].show();
//        pop[18].show();
//        pop[17].show();
//        System.out.println(pop[19].getFitness());
//        System.out.println(pop[18].getFitness());
//        System.out.println(pop[17].getFitness());
//        pop[0].show();
//        pop[1].show();
//        pop[2].show();
//        System.out.println(pop[0].getFitness());
//        System.out.println(pop[1].getFitness());
//        System.out.println(pop[2].getFitness());
//        
//        int i;
//        for(i=0;i<79;i++){
//            if(Arrays.equals(pop[19].getGenCode(), database[i].getGenCode())){
//                break;
//            }
//        }
//        
//        String placeid = Files.readAllLines(Paths.get("/home/alexandre/Documentos/GitHub/AI-Algorithms/AG/placeids.txt")).get(i);
//        
//        String s = "https://maps.googleapis.com/maps/api/place/details/json?"
//                + "key=AIzaSyDZxoYnGOB_BCKxNnPBDzKAIFk1PYw3Yto&placeid=" + placeid;
//        String str = "";
//            
//        //criando uma url com a string concatenada
//        URL url = new URL(s);
//            
//        //criando um objeto scanner para receber o arquivo gerado pela url
//        Scanner scan = new Scanner(url.openStream());
//            
//        //concatenando todas as linhas do arquivo na string str
//        while (scan.hasNext()) {
//            str += scan.nextLine();
//        }
//        scan.close();
//            
//        JSONObject obj = new JSONObject(str);
//        System.out.println(obj.getJSONObject("result").getString("name"));
//        
//        System.out.println(obj.getJSONObject("result").getString("formatted_address"));
//        System.out.println(obj.getJSONObject("result").getString("website"));
        
    }
}
