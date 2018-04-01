package ag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
            
            //concatenando todas as linhas do arquivo na string str
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
    
    public static int distance(String lat, String lng, String address) throws MalformedURLException, IOException, JSONException{
        //String para request dos dados do endereço do usuário
        String addr = "https://maps.googleapis.com/maps/api/geocode/json?"
                + "key=AIzaSyC8zOWqLGn1N-V_UAB4QQGI7QTdQnZTEeo&address=" + address;
        //Criando uma URL a partir de addr
        URL url = new URL(addr);
        
        //Objeto Scanner que vai receber a stream da url
        Scanner scan = new Scanner(url.openStream());
        
        //concatenando todas as linhas do arquivo na String jfile
        String jfile = new String();
        while (scan.hasNext()) {
            jfile += scan.nextLine();
        }
        scan.close();
        
        //Pegando as coordenadas do local do usuário
        JSONObject obj = new JSONObject(jfile);
        JSONArray array = obj.getJSONArray("results");
        JSONObject loc = array.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
        
        //String de origem do usuário
        String origins = loc.getString("lat") + "," + loc.getString("lng");
        //String de destino -> academia
        String destinations =  lat + "," + lng;
        
        //String de url para fazer o request dos dados entre a origem e o destino
        String geoinfo = "https://maps.googleapis.com/maps/api/distancematrix/json?"
                + "mode=driving&key=AIzaSyC8zOWqLGn1N-V_UAB4QQGI7QTdQnZTEeo&"
                + "origins=" + origins + "&destinations=" + destinations;
        
        //Criando url output a partir de geoinfo
        URL output = new URL(geoinfo);
        //Scanner para guardar a stream de output
        Scanner geoscan = new Scanner(output.openStream());
        //Concatenando as linhas de geoscan na string jgeo
        String jgeo = new String();
        while (geoscan.hasNext()) {
            jgeo += geoscan.nextLine();
        }
        geoscan.close();
        
        //Obtendo a distância entre a origem (usuário) e o destino (academia)
        JSONObject geojson = new JSONObject(jgeo);
        JSONArray rows = geojson.getJSONArray("rows");
        JSONArray elements = rows.getJSONObject(0).getJSONArray("elements");
        JSONObject dist = elements.getJSONObject(0).getJSONObject("distance");
        int distance = dist.getInt("value");
        
        return distance;
    }
}
