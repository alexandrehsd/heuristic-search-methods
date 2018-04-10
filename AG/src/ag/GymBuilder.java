package ag;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GymBuilder {
    
    public static Chromosome[] Gyms(String addr, String minw, String maxw, String minwe, String maxwe) throws MalformedURLException, IOException, JSONException{
        //String[] placeids = new String[199];
        //String padrão para fazer o request dos placeids
        String s = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyCwYJvP0YHepfjOIiz0orFYjzlxn6AJrOQ&placeid=";
        
        //Leitura do arquivo placeid
        File file = new File("/home/alexandre/Documentos/GitHub/AI-Algorithms/AG/placeids.txt");
        Scanner input = new Scanner(file);
        List<String> list = new ArrayList<>();
        
        double lat, lng, rating;
        int dist, wOpen, wEOpen, wClose, wEClose;
        
        Chromosome[] database = new Chromosome[76];
        for(int i=0;i<76;i++){
            database[i] = new Chromosome();
        }
        //Contador de academias
        int i=0;
        String str="";
        //O código executará enquanto houverem linhas restantes no placeids.txt
       while (input.hasNextLine()) {
            
            //concatenando a string padrão com o placeid atual
            list.add(input.nextLine());
            s += list.get(i);
            
            //criando uma url com a string concatenada
            URL url = new URL(s);
            
            //criando um objeto scanner para receber o arquivo gerado pela url
            Scanner scan = new Scanner(url.openStream());
            
            //concatenando todas as linhas do arquivo na string str
            str = "";
            while (scan.hasNext()) {
                str += scan.nextLine();
            }
            scan.close();
            
            
        JSONObject obj = new JSONObject(str);
        JSONObject loc = obj.getJSONObject("result").getJSONObject("geometry").getJSONObject("location");
        lat = loc.getDouble("lat");
        lng = loc.getDouble("lng");
        dist = GymBuilder.distance(Double.toString(lat), Double.toString(lng), addr);
        database[i].setBits(0, 3, DistEncode(dist));
        
        rating = obj.getJSONObject("result").getDouble("rating");
        database[i].setBits(4, 7, RatEncode(rating));
        
        database[i].setBits(8, 11, PriceCode());
        
        //Capturando os horários de abertura e fechamento durante a semana, através de um longo caminho...
        JSONObject array = obj.getJSONObject("result").getJSONObject("opening_hours");
        JSONArray periods = array.getJSONArray("periods");
        JSONObject whourOpen = periods.getJSONObject(periods.length()-2).getJSONObject("open");
        JSONObject whourClose = periods.getJSONObject(periods.length()-2).getJSONObject("close");
        
        wOpen = whourOpen.getInt("time");
        wClose = whourClose.getInt("time");
        
        JSONObject wEhourOpen = periods.getJSONObject(periods.length()-1).getJSONObject("open");
        JSONObject wEhourClose = periods.getJSONObject(periods.length()-1).getJSONObject("close");
        
        wEOpen = wEhourOpen.getInt("time");
        wEClose = wEhourClose.getInt("time");
        
        database[i].setBits(12, 13, HourEncode(minw, maxw, minwe, maxwe, wOpen, wClose, wEOpen, wEClose));
        database[i].setFitness();
        //padronizando a string s;
        s = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyCwYJvP0YHepfjOIiz0orFYjzlxn6AJrOQ&placeid=";
        
        i++;
        System.out.println(i);
            
        }
        
        return database;
    }
    
    public static int distance(String lat, String lng, String address) throws MalformedURLException, IOException, JSONException{
        //String para request dos dados do endereço do usuário
        address = address.replace(" ", "+");
        String addr = "https://maps.googleapis.com/maps/api/geocode/json?"
                + "key=AIzaSyCwYJvP0YHepfjOIiz0orFYjzlxn6AJrOQ&address=" + address;
        //Criando uma URL a partir de addr
        URL url = new URL(addr);
        
        //Objeto Scanner que vai receber a stream da url
        Scanner scan = new Scanner(url.openStream());
        
        //concatenando todas as linhas do arquivo na String jfile
        String jfile = "";
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
                + "mode=driving&key=AIzaSyCwYJvP0YHepfjOIiz0orFYjzlxn6AJrOQ&"
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
        int pace=0;
        int sum = 0;
        
        /// CALCULA O VALOR DE PACE, QUE IRÁ DEFINIR O NÚMERO DO INTERVALO PARA CODIFICAÇÃO DO RATING (0000 -> Intervalo 1) ; (1000 -> Intervalo 9)
        while(sum < dist){
            if(pace/5 == 0){
                sum += 300;
            }else if(pace/5 == 1){
                sum += 500;
            }else{
                sum += 1000;
            }
            pace++;
        }       
        if(dist<9000){ // Corrige erros de cálculo de distância
            pace = pace-2; 
        }
        
        /// CONSTRÓI A STRING NO FORMATO (0101)
        StringBuilder binary = new StringBuilder();
        binary.append(Integer.toBinaryString(pace-1));
        for(int n=binary.length(); n<4; n++) {
              binary.insert(0, "0");
        }
        int[] bits = new int[binary.length()];
        String bin = binary.toString();
        
        /// MAPEIA A STRING EM UM VETOR DE INTEIROS, NO FORMATO [0, 1, 0, 1]
        for(int i =0;i<binary.length();i++){
            bits[i] = bin.charAt(i) - 48;
        }
        
        return bits;
    }
    
    public static int[] RatEncode(double rating){
        int pace=0; 
        double sum=0.6;
        
        /// CALCULA O VALOR DE PACE, QUE IRÁ DEFINIR O NÚMERO DO INTERVALO PARA CODIFICAÇÃO DO RATING (0000 -> Intervalo 1) ; (1000 -> Intervalo 9)
        if(rating <= 0.5){
            pace=0;
        }
        else{
            while(sum <= rating){
                sum += 0.3;
                pace++;
            } 
        }
        
        /// CONSTRÓI A STRING NO FORMATO (0101)
        StringBuilder binary = new StringBuilder();
        binary.append(Integer.toBinaryString(pace));
        for(int n=binary.length(); n<4; n++) {
              binary.insert(0, "0");
        }
        int[] bits = new int[binary.length()];
        String bin = binary.toString();
        
        /// MAPEIA A STRING EM UM VETOR DE INTEIROS, NO FORMATO [0, 1, 0, 1]
        for(int i =0;i<binary.length();i++){
            bits[i] = bin.charAt(i) - 48;
        }
        
        return bits;
    }
    
    public static int[] PriceCode(){
        int aux, price, pace=0, sum=49;
        Random rand = new Random();
        
        aux = rand.nextInt(10); // Variável auxiliar para definir a faixa de preço
        switch (aux) {
            case 0: case 1: case 2: case 3:// A academia se enquadra na faixa de academias baratas (50-100)R$
                price = 50 + rand.nextInt(51);
                break;
            case 4: case 5: case 6:// A academia se enquadra na faixa de academias acessíveis (100-200)R$
                price = 100 + rand.nextInt(101);
                break;
            case 7: case 8: // A academia se enquadra na faixa de academias caras (200-320)R$
                price = 200 + rand.nextInt(121); 
                break;
            default: // A academia se enquadra na faixa de academias de luxo (320-400)R$
                price = 320 + rand.nextInt(81);
                break;
        }
        
        /// CALCULA O VALOR DE PACE, QUE IRÁ DEFINIR O NÚMERO DO INTERVALO PARA CODIFICAÇÃO DO PREÇO (0000 -> Intervalo 1) ; (1000 -> Intervalo 9)
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
        
        /// CONSTRÓI A STRING NO FORMATO (0101)
        StringBuilder binary = new StringBuilder();
        binary.append(Integer.toBinaryString(pace-1));
        for(int n=binary.length(); n<4; n++) {
              binary.insert(0, "0");
        }
        int[] bits = new int[binary.length()];
        String bin = binary.toString();
        
        /// MAPEIA A STRING EM UM VETOR DE INTEIROS, NO FORMATO [0, 1, 0, 1]
        for(int i =0;i<binary.length();i++){
            bits[i] = bin.charAt(i) - 48;
        }
        return bits;
    }
    
    //A String de entrada tem o seguinte formato XXhYY
    public static int[] HourEncode(String minw, String maxw, String minwe, String maxwe, int wOpen, int wClose, int wEOpen, int wEClose) throws MalformedURLException, IOException, JSONException{
        boolean wflag = false, weflag = false; // Variáveis que indicam se o usuário pode malhar na academia em questão durante os dias de semana e o fim de semana, respectivamente
        int []code = new int[2];
       
        /// SUBSTITUI O "h" DAS ENTRADAS (17h00) POR VAZIO -> (1700)
        minw = minw.replace("h", "");
        maxw = maxw.replace("h", "");
        minwe = minwe.replace("h", "");
        maxwe = maxwe.replace("h", "");
        
        /// CONVERTE OS VALORES ANTERIORES PARA UM INTEIRO (1700) -> 17
        int hminw = Integer.parseInt(minw);
        int hmaxw = Integer.parseInt(maxw);
        int hminwe = Integer.parseInt(minwe);
        int hmaxwe = Integer.parseInt(maxwe);  
      
        /// VERIFICA SE O USUÁRIO PODE MALHAR NA ACADEMIA EM QUESTÃO DURANTE OS DIAS DE SEMANA
        for(int i=wOpen; i<=wClose; i++){
            for(int j=hminw; j<=hmaxw; j++){
                if(j==i){
                    wflag = true;
                    break;
                }
            }
        }
        
        /// VERIFICA SE O USUÁRIO PODE MALHAR NA ACADEMIA EM QUESTÃO DURANTE O FIM DE SEMANA
        for(int i=wEOpen; i<=wEClose; i++){
            for(int j=hminwe; j<=hmaxwe; j++){
                if(j==i){
                    weflag = true;
                    break;
                }
            }
        }
        
        if((wflag) && (weflag)){ // Se ele pode malhar em ambos, dias de semana e fim de semana
            code[0]=0;
            code[1]=0;
        }
        else if((wflag) && !(weflag)) // Se ele pode malhar apenas nos dias de semana
        {
            code[0]=0;
            code[1]=1;
        }
        else if(!(wflag) && (weflag)){ // Se ele pode malhar apenas no fim de semana
            code[0]=1;
            code[1]=0;
        }
        else{ // Se ele não pode malhar nessa academia
            code[0]=1;
            code[1]=1;
        }
        
        return code;
    }
    
}
