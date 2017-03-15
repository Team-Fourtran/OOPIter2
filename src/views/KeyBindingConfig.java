package views;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class KeyBindingConfig {
    Properties prop = new Properties();
    InputStream input = null;

    public void readConfig(){
        input = null;
        readFileForReal(input,"configs/KeyBindings.properties");

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

    public void readFileForReal(InputStream stream, String filepath){
        try {stream = new FileInputStream(filepath);}
        catch (IOException ex) {ex.printStackTrace();}
    }
}
