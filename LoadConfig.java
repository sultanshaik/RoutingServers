import java.util.*;
import java.net.URL;
import java.io.*;
import java.lang.*;


public class LoadConfig{

    public Map<String,Map<String, Map<String ,Map<String,String> > > > customerMap;

    LoadConfig() throws FileNotFoundException{


        String localDir = System.getProperty("user.dir");
        File file =   new File(localDir + "//config.txt");
        Scanner sc = new Scanner(file);


        customerMap = new HashMap<String,Map<String, Map<String ,Map<String,String>>>>();
        while (sc.hasNextLine()){
            String [] serverInfo = sc.nextLine().split("=");
            String[] serverIds = serverInfo[0].split("\\.");

            Map<String , Map<String,Map<String,String>>> countryMap;
            Map<String , Map<String,String>> stateMap;
            Map<String , String> cityMap;

            if(customerMap.containsKey(serverIds[0])){

                if(customerMap.get(serverIds[0]).containsKey(serverIds[1])){

                    if(customerMap.get(serverIds[0]).get(serverIds[1]).containsKey(serverIds[2])){
                        customerMap.get(serverIds[0]).get(serverIds[1]).get(serverIds[2]).put(serverIds[3] , serverInfo[1]);
                    }
                    else{
                        cityMap = new HashMap<String,String>();
                        cityMap.put(serverIds[3] , serverInfo[1]);
                        customerMap.get(serverIds[0]).get(serverIds[1]).put(serverIds[2],cityMap);
                    }
                }
                else{

                    cityMap = new HashMap<String,String>();
                    cityMap.put(serverIds[3] , serverInfo[1]);

                    stateMap = new HashMap<String ,Map<String,String>>();
                    stateMap.put(serverIds[2],cityMap);

                    customerMap.get(serverIds[0]).put(serverIds[1],stateMap);


                }


            }
            else{

                cityMap = new HashMap<String,String>();
                cityMap.put(serverIds[3] , serverInfo[1]);

                stateMap = new HashMap<String ,Map<String,String>>();
                stateMap.put(serverIds[2],cityMap);

                countryMap = new HashMap<String, Map<String ,Map<String,String>>>();
                countryMap.put(serverIds[1],stateMap);

                customerMap.put(serverIds[0],countryMap);
            }



        }

    }

}