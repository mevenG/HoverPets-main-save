package safro.hover.pets.util;

import net.fabricmc.loader.api.FabricLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import safro.hover.pets.HoverPets;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Config {

    public static void init(){
        try {
            File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "hoverpets.json");
            if (configFile.createNewFile()) {
                System.out.println("HoverPets config file created");
            } else {
                //System.out.println("File already exists.");
                JSONParser parser = new JSONParser();
                try {
                    Object obj = parser.parse(new FileReader(configFile));
                    HoverPets.CONF = (JSONObject) obj;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public JSONObject pet(String type){
        return (JSONObject) HoverPets.CONF.get(type);
    }

}
