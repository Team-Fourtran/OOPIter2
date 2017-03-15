package tests;

import views.KeyBindingConfig;

public class TestKeyBinding {
    public static void main(String[] args){
        KeyBindingConfig kbc = new KeyBindingConfig();
        kbc.readConfig();
    }
}
