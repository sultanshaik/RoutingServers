import java.io.*;
import java.util.*;

public class FindServer {

    private static LoadConfig loadConfig;
    public static void main(String[] args){
        try{
            String result;
            loadConfig = new LoadConfig();

            String localDir = System.getProperty("user.dir");
            File file =   new File(localDir + "//findServersInput.txt");
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                result  = findRoute(sc.nextLine());
                System.out.println(result);
            }
        }

        catch(FileNotFoundException e){
            System.out.println("File not found" + e);
        }
    }

    static String  findRoute(String input){

        String customer, country, state,city;
        String[] inputs =  input.split("\\.");
        customer = inputs[0];
        country = inputs[1];
        state = inputs[2];
        city = inputs[3];
        String lastMatchedPosition = findLastMatchPosition(loadConfig , customer, country,state,city);

        switch(lastMatchedPosition){
            case "customer" :
                customer = country = state = city = "*";
                break;
            case "country":
                country = state = city = "*";
                break;
            case "state":
                state = city = "*";
                break;
            case "city":
                city = "*";
                break;

        }
        try{
            return loadConfig.customerMap.get(customer).get(country).get(state).get(city);
        }
        catch(NullPointerException e){
            return "No Server Found";
        }

    }

    static String findLastMatchPosition(LoadConfig loadConfig, String customer , String country , String state , String city){
        if(!loadConfig.customerMap.containsKey(customer)){
            return "customer";
        }
        else{
            if(!loadConfig.customerMap.get(customer).containsKey(country)){
               return "country";
            }
            else{
                 if(!loadConfig.customerMap.get(customer).get(country).containsKey(state)){
                    return "state";
                 }
                 else{
                     if(!loadConfig.customerMap.get(customer).get(country).get(state).containsKey(city)){
                        return "city";
                     }
                 }
            }
        }

      return "";

    }
}