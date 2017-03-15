package views;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Properties;

public class KeyBindingConfig {
    Properties prop;// = new Properties();
    InputStream input;// = null;
    HashMap<String, Integer> keyNamesToCodes;// = makeKeycodeMap();
    HashMap<String, int[]> customMappings;// = new HashMap<>();

    //Returns a hashmap that maps control commands to an array of the key event ints that should trigger the command
    public HashMap<String, int[]> getMappings(){
        return this.customMappings;
    }
    public KeyBindingConfig(){
        prop = new Properties();
        input = null;
        input = readConfigFile(input,"KeyBindings.properties");

        keyNamesToCodes = makeKeycodeMap();     //Makes a map of all of KeyEvent's constants (e.g. VK_RIGHT) to their int values

        customMappings = new HashMap<>();       //Will hold the mappings for game controls to keys as specified

        try {
            // load a properties file
            prop.load(input);

            //Call setMapping for each game control
            setMapping("previous_mode");
            setMapping("next_mode");

            setMapping("previous_type");
            setMapping("next_type");

            setMapping("previous_instance");
            setMapping("next_instance");

            setMapping("previous_command");
            setMapping("next_command");

        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    //Sets the value of customMappings to the integer that the property value corresponds to in KeyEvent
    private void setMapping(String controlName){
        String[] sKeys = prop.getProperty(controlName).split(" ");
        try{
            int[] iKeys = getKeyCodes(sKeys);
            customMappings.put(controlName , iKeys);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("ERROR: KeyBindings.properties contains invalid key mappings. Please check them again.");
            System.exit(1);
        };
    }

    //Gets the ints that correspond to KeyEvent constant names
    private int[] getKeyCodes(String[] sKeys) throws Exception{
        int[] iKeys = new int[sKeys.length];
        for(int i = 0; i < sKeys.length; i++)
            iKeys[i] = keyNamesToCodes.get(sKeys[i]);
        return iKeys;
    }

    //Generate a map of all of KeyEvent's constants
    private HashMap<String, Integer> makeKeycodeMap(){
        HashMap<String, Integer> keyNamesToCodes = new HashMap<String,Integer>();

        try {
            Field[] fields = KeyEvent.class.getFields();

            for (int i = 0; i < fields.length; i++) {
                String fieldName = fields[i].getName();

                /* Only get field names corresponding to key codes */
                if (fieldName.substring(0,2).equals("VK")) {
                    // Use reflection to get the actual key code value
                    int keyCode = fields[i].getInt(null);

                    // Add the code and name values to the maps
                    keyNamesToCodes.put(fieldName,keyCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return keyNamesToCodes;
    }

    //Read the config file and save it in an InputStream for property reading
    private InputStream readConfigFile(InputStream stream, String filename){
        try {
            stream = new FileInputStream(System.getProperty("user.dir") + "/src/configs/" + filename);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return stream;
    }
}
