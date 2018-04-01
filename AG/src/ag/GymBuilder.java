package ag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GymBuilder {
    
    public static void Gyms() throws MalformedURLException, IOException, JSONException{
        //String[] placeids = new String[199];
        //String padrão para fazer o request dos placeids
        String s = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyC8zOWqLGn1N-V_UAB4QQGI7QTdQnZTEeo&placeid=";
        
        //Leitura do arquivo placeid
        File f = new File("/home/alexandre/Documentos/GitHub/AI-Algorithms/AG/placeids.txt");
        BufferedReader b = new BufferedReader(new FileReader(f));
        String readLine = "";
        
        //Contador de academias
        int i=1;
        //O código executará enquanto houverem linhas restantes no placeids.txt
        while ((readLine = b.readLine()) != null) {
            //concatenando a string padrão com o placeid atual
            s += readLine;
            
            //criando uma url com a string concatenada
            URL url = new URL(s);
            
            //criando um objeto scanner para receber o arquivo gerado pela url
            Scanner scan = new Scanner(url.openStream());
            
            //concatenando todas as linhas do arquivo json
            String str = new String();
            while (scan.hasNext()) {
                str += scan.nextLine();
            }
            scan.close();
            
            System.out.println("---- Gym " + i +" characteristcs ----");
            // Construindo um objeto json
            JSONObject obj = new JSONObject(str);
            
            //Capturando a localização, a partir do caminho result->geometry->location do arquivo json
            JSONObject loc = obj.getJSONObject("result").getJSONObject("geometry").getJSONObject("location");
            System.out.println("lat: " + loc.getDouble("lat") + ", lng: " + loc.getDouble("lng"));
            
            //Capturando o rate, a partir do caminho result->rating
            JSONObject rate = obj.getJSONObject("result");
            System.out.println("rate: " + rate.getDouble("rating"));
            
            //Capturando os horários de abertura e fechamento durante a semana, através de um longo caminho...
            JSONObject array = obj.getJSONObject("result").getJSONObject("opening_hours");
            JSONArray periods = array.getJSONArray("periods");
            JSONObject whourOpen = periods.getJSONObject(1).getJSONObject("open");
            JSONObject whourClose = periods.getJSONObject(1).getJSONObject("close");
            System.out.println("opens at " + whourOpen.getInt("time"));
            System.out.println("closes at " + whourClose.getInt("time"));
            i++;
            //padronizando a string s;
            s = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyC8zOWqLGn1N-V_UAB4QQGI7QTdQnZTEeo&placeid=";
        }
        
    }
}
