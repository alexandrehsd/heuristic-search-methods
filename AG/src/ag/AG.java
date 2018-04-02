package ag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
        Chromosome[] database = new Chromosome[131];
        
        System.arraycopy(GymBuilder.Gyms("Rua Alexandrino de Alencar, 1618", "18h00","22h00" , "13h00", "15h00"),
                0, database, 0, 131);
        
        // GERANDO A POPULAÇÃO INICIAL ALEATORIAMENTE
        for(int i=0; i<20; i++){
            pop[i] = new Chromosome();
        }
        int it=0;
        while(it<50){
            // REALIZANDO O CRUZAMENTO E PRODUZINDO FILHOS
            System.arraycopy(Operator.crossover(Operator.selection(pop)), 0, sons, 0, 18); 

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
            // SUBSTITUINDO A GERAÇÃO ANTIGA PELA NOVA GERAÇÃO
            System.arraycopy(sons, 0, pop, 0, 18); // Substitui a população pelos novos filhos
            System.out.println(it);
            it++;
        }
               
        Sort.BubbleSort(pop);
        pop[19].show();
        pop[18].show();
        pop[17].show();
        pop[0].show();
        pop[1].show();
        pop[2].show();
//        int[] IdIndex = new int[3];
//        for(int i=17;i<20;i++){
//            for(int j=0;j<131;j++){
//                if(Arrays.equals(pop[i].getGenCode(), database[j].getGenCode())){
//                    IdIndex[i-17] = j;
//                }
//            }
//        }
        
//        File f = new File("/home/alexandre/Documentos/GitHub/AI-Algorithms/AG/placeids.txt");
//        BufferedReader b = new BufferedReader(new FileReader(f));
//        String readLine = "";
//        String s = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyCLXOfMvJKX6yHVhzXSy1CAFenLjVO6Pfc&placeid=";
//        String str = new String();
//        
//        while ((readLine = b.readLine()) != null){
//            //concatenando a string padrão com o placeid atual
//            s += readLine;
//            
//            //criando uma url com a string concatenada
//            URL url = new URL(s);
//            
//            //criando um objeto scanner para receber o arquivo gerado pela url
//            Scanner scan = new Scanner(url.openStream());
//            
//            //concatenando todas as linhas do arquivo na string str
//            while (scan.hasNext()) {
//                str += scan.nextLine();
//            }
//            scan.close();
//        }
//        
//        JSONObject obj = new JSONObject(str);
//        String name = obj.getJSONObject("result").getString("name");
//        String address = obj.getJSONObject(str).getString("formatted_address");
        
        
        
        

        
    }
}
