import java.util.*;
import java.net.URL;
import java.io.*;
import java.lang.*;


public class LoadConfig{

    public Map<String,Map<String, Map<String ,Map<String,String> > > > customerMap;

    LoadConfig() throws FileNotFoundException{

        //Reading the config file
        String localDir = System.getProperty("user.dir");
        File file =   new File(localDir + "//config.txt");
        Scanner sc = new Scanner(file);

        //Creating a map that containers mapping of all rules
        customerMap = new HashMap<String,Map<String, Map<String ,Map<String,String>>>>();


        while (sc.hasNextLine()){
            String [] serverInfo = sc.nextLine().split("="); //Separate the server and input
            String[] serverIds = serverInfo[0].split("\\."); //Separate the input details

            Map<String , Map<String,Map<String,String>>> countryMap;
            Map<String , Map<String,String>> stateMap;
            Map<String , String> cityMap;

            String customerType, country , state , city ,server;

            customerType = serverIds[0];
            country = serverIds[1];
            state    =   serverIds[2];
            city    = serverIds[3];
            server = serverInfo[1];

            if(!customerMap.containsKey(customerType)){ // If no customer Type is found

                cityMap = new HashMap<String,String>();
                cityMap.put(city , server);

                stateMap = new HashMap<String ,Map<String,String>>();
                stateMap.put(state,cityMap);

                countryMap = new HashMap<String, Map<String ,Map<String,String>>>();
                countryMap.put(country,stateMap);

                customerMap.put(customerType,countryMap);
            }
            else{
                if(!customerMap.get(customerType).containsKey(country)){ // If country for customer type does not exist

                    cityMap = new HashMap<String,String>();
                    cityMap.put(city , server);

                    stateMap = new HashMap<String ,Map<String,String>>();
                    stateMap.put(state,cityMap);

                    customerMap.get(customerType).put(country,stateMap);


                }
                else{
                    if(! customerMap.get(customerType).get(country).containsKey(state)){ // If state for a country does not exist

                        cityMap = new HashMap<String,String>();
                        cityMap.put(city , server);
                        customerMap.get(customerType).get(country).put(state,cityMap);

                    }
                    else{ // State found, then add the city
                        customerMap.get(customerType).get(country).get(state).put(city , server);
                    }

                }
            }




        }

    }

}