package views;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class KeyBindingConfig {
    Properties prop;// = new Properties();
    InputStream input;// = null;

    public KeyBindingConfig(){
        prop = new Properties();
        input = null;
    }

    public void readConfig(){
        input = null;
        input = readConfigFile(input,"KeyBindings.properties");

        System.out.println(input);

        try {
            // load a properties file
            prop.load(input);

            // get the property value and print it out
            System.out.println(prop.getProperty("database"));
            System.out.println(prop.getProperty("dbuser"));
            System.out.println(prop.getProperty("dbpassword"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private InputStream readConfigFile(InputStream stream, String filename){
        try {
            stream = new FileInputStream(System.getProperty("user.dir") + "/src/configs/" + filename);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return stream;
    }
}
