package controllers;

import java.util.ArrayList;
import java.util.HashMap;

/* Listens for calls to its update() method that change the value of a key's "pressed" status (boolean), and informs
 * clients (listeners) about the new status of the keymap. */
public class KeyPressInformer {
    private HashMap<String, Boolean> keyMap;
    private ArrayList<KeyPressListener> clients;

    public KeyPressInformer(HashMap<String, Boolean> keyMap){
        this.keyMap = keyMap;
        this.clients = new ArrayList<>();
    }

    protected void registerClient(KeyPressListener client){
        clients.add(client);
    }

    public void update(String keyname, Boolean status){
        keyMap.put(keyname,status);
        for(int i = 0; i < clients.size(); i++){
            clients.get(i).updateKeysPressed(keyMap);
        }
    }
}