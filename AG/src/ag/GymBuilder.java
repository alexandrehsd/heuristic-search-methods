package ag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GymBuilder {
    
    public static Chromosome[] Gyms(String addr, String minw, String maxw, String minwe, String maxwe) throws MalformedURLException, IOException, JSONException{
        //String[] placeids = new String[199];
        //String padrão para fazer o request dos placeids
        String s = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyC8zOWqLGn1N-V_UAB4QQGI7QTdQnZTEeo&placeid=";
        
        //Leitura do arquivo placeid
        File f = new File("/home/alexandre/Documentos/GitHub/AI-Algorithms/AG/placeids.txt");
        BufferedReader b = new BufferedReader(new FileReader(f));
        String readLine = "";
        
        Chromosome[] database = new Chromosome[131];
        for(int i=0;i<131;i++){
            database[i] = new Chromosome();
        }
        //Contador de academias
        int i=0;
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
            
        JSONObject obj = new JSONObject(str);
        JSONObject loc = obj.getJSONObject("result").getJSONObject("geometry").getJSONObject("location");
        double lat = loc.getDouble("lat");
        double lng = loc.getDouble("lng");
        int dist = GymBuilder.distance(Double.toString(lat), Double.toString(lng), addr);
        database[i].setBits(0, 3, DistEncode(dist));
            
        double rating = GymBuilder.Rating(readLine);
        database[i].setBits(4, 7, RatEncode(rating));
        
        database[i].setBits(8, 11, PriceCode());
        database[i].setBits(12, 13, HourEncode(minw, maxw, minwe, maxwe, readLine));
        //padronizando a string s;
        s = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyC8zOWqLGn1N-V_UAB4QQGI7QTdQnZTEeo&placeid=";
        i++;
            System.out.println(i);
        }
        return database;
    }
    
    public static int distance(String lat, String lng, String address) throws MalformedURLException, IOException, JSONException{
        //String para request dos dados do endereço do usuário
        address = address.replace(" ", "+");
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
        double lat1 = loc.getDouble("lat");
        double lng1 = loc.getDouble("lng");
        String origins = Double.toString(lat1) + "," + Double.toString(lng1);
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
    
    public static int[] DistEncode(int dist){
        int id=0;
        int sum = 0;
        while(sum < dist){
            if(id/5 == 0){
                sum += 300;
            }else if(id/5 == 1){
                sum += 500;
            }else{
                sum += 1000;
            }
            id++;
        }        
        StringBuilder binary = new StringBuilder();
        binary.append(Integer.toBinaryString(id-1));
        for(int n=binary.length(); n<4; n++) {
              binary.insert(0, "0");
        }
        
        int[] bits = new int[binary.length()];
        String bin = binary.toString();
        
        for(int i =0;i<binary.length();i++){
            bits[i] = bin.charAt(i) - 48;
        }
        return bits;
        
    }
    
    public static double Rating(String placeid) throws MalformedURLException, IOException, JSONException{
        String s = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyC8zOWqLGn1N-V_UAB4QQGI7QTdQnZTEeo&placeid=";
        s += placeid;
        URL url = new URL(s);
            
        //criando um objeto scanner para receber o arquivo gerado pela url
        Scanner scan = new Scanner(url.openStream());
            
        //concatenando todas as linhas do arquivo na string str
        String str = new String();
        while (scan.hasNext()) {
            str += scan.nextLine();
        }
        scan.close();
        
        JSONObject obj = new JSONObject(str);
        JSONObject result = obj.getJSONObject("result");
        double rating = result.getDouble("rating");
        return rating;
    }
    
    public static int[] RatEncode(double rating){
        int pace=0; 
        double sum=0.6;
        if(rating <= 0.5){
            pace=0;
        }
        else{
            while(sum <= rating){
                sum += 0.3;
                pace++;
            } 
        }
        StringBuilder binary = new StringBuilder();
        binary.append(Integer.toBinaryString(pace));
        for(int n=binary.length(); n<4; n++) {
              binary.insert(0, "0");
        }
        
        int[] bits = new int[binary.length()];
        String bin = binary.toString();
        
        for(int i =0;i<binary.length();i++){
            bits[i] = bin.charAt(i) - 48;
        }
        return bits;
    }
    
    public static int[] PriceCode(){
        int price, pace=0, sum=50;
        Random rand = new Random();
        price = 50 + 5*rand.nextInt(71);
        while(sum < price){
            if(pace/5 == 0){
                sum += 10;
            }else if((pace/5 == 1) && (pace/10 == 0)){
                sum += 20;
            }else if((pace/10 == 1) && (pace/14 == 0)){
                sum += 30;
            }
            else{
                sum += 40;
            }
            pace++;
        }
        StringBuilder binary = new StringBuilder();
        binary.append(Integer.toBinaryString(pace-1));
        for(int n=binary.length(); n<4; n++) {
              binary.insert(0, "0");
        }
        int[] bits = new int[binary.length()];
        String bin = binary.toString();
        
        for(int i =0;i<binary.length();i++){
            bits[i] = bin.charAt(i) - 48;
        }
        return bits;
    }
    
    //A String de entrada tem o seguinte formato XXhYY
    public static int[] HourEncode(String minw, String maxw, String minwe, String maxwe, String placeid) throws MalformedURLException, IOException, JSONException{
        minw = minw.replace("h", "");
        maxw = maxw.replace("h", "");
        minwe = minwe.replace("h", "");
        maxwe = maxwe.replace("h", "");
        
        int hminw = Integer.parseInt(minw);
        int hmaxw = Integer.parseInt(maxw);
        int hminwe = Integer.parseInt(minwe);
        int hmaxwe = Integer.parseInt(maxwe);
        
        String s = "https://maps.googleapis.com/maps/api/place/details/json?"
                + "key=AIzaSyC8zOWqLGn1N-V_UAB4QQGI7QTdQnZTEeo&placeid=" + placeid;            
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
        
        // Construindo um objeto json
        JSONObject obj = new JSONObject(str);
        
        //Capturando os horários de abertura e fechamento durante a semana, através de um longo caminho...
        JSONObject array = obj.getJSONObject("result").getJSONObject("opening_hours");
        JSONArray periods = array.getJSONArray("periods");
        JSONObject whourOpen = periods.getJSONObject(periods.length()-2).getJSONObject("open");
        JSONObject whourClose = periods.getJSONObject(periods.length()-2).getJSONObject("close");
        
        int wOpen = whourOpen.getInt("time");
        int wClose = whourClose.getInt("time");
        
        JSONObject wEhourOpen = periods.getJSONObject(periods.length()-1).getJSONObject("open");
        JSONObject wEhourClose = periods.getJSONObject(periods.length()-1).getJSONObject("close");
        
        int wEOpen = wEhourOpen.getInt("time");
        int wEClose = wEhourClose.getInt("time");
        
        int []code = new int[2];
        if((hminw>wOpen && hmaxw<wClose) && (hminwe>wEOpen && hmaxwe<wEClose)){
            code[0]=0;
            code[1]=0;
        }
        else if((hminw>wOpen && hmaxw<wClose) && !(hminwe>wEOpen && hmaxwe<wEClose))
        {
            code[0]=0;
            code[1]=1;
        }
        else if(!(hminw>wOpen && hmaxw<wClose) && (hminwe>wEOpen && hmaxwe<wEClose)){
            code[0]=1;
            code[1]=0;
        }
        else{
            code[0]=1;
            code[1]=1;
        }
        
        return code;
    }
    
}

