package controllers;

import views.KeyBindingConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/* Listens for calls to its update() method that change the value of a key's "pressed" status (boolean), and informs
 * clients (listeners) about the new status of the keymap. */
public class KeyPressInformer {
    private HashMap<String, Boolean> keyMap;
    private ArrayList<KeyPressListener> clients;

    HashMap<String, int[]> customKeyBindings;
    String[] commandsList;

    private ArrayList<Integer> currentlyPressedKeys;

    //Maps command name to the legacy keys that perform it. E.g: "previous_type"->"CONTROL LEFT"

    public KeyPressInformer(){
        //this.keyMap = keyMap; (used to be a parameter)
        this.clients = new ArrayList<>();
        currentlyPressedKeys = new ArrayList<>();
        customKeyBindings = new KeyBindingConfig().getMappings();
        commandsList = new String[customKeyBindings.size()];
        commandsList = customKeyBindings.keySet().toArray(commandsList);

    }

    protected void registerClient(KeyPressListener client){
        clients.add(client);
    }

    //Respond to a notification that a key with the given keyID was pressed
    public void keyPressed(int keyID){
        //If the key isn't being pressed right now, add it to the arraylist of pressed keys
        if(currentlyPressedKeys.indexOf(keyID) < 0){
            currentlyPressedKeys.add(keyID);
        }

        //check if the currently pressed keys constitute a command
        checkKeysForCommand();
    }

    public void keyReleased(int keyID){
        //Go through the currently pressed keys and remove elements matching the given keyID
        for(int i = 0; i < currentlyPressedKeys.size(); i++ ){
            if(currentlyPressedKeys.get(i) == keyID)
                currentlyPressedKeys.remove(i);
        }
    }

    //Check the array of currently pressed keys to see if they constitute a command
    private void checkKeysForCommand(){
        ArrayList<String> validCommands = new ArrayList<>();    //Multiple commands may be triggered. Store all of them here.

        //Iterate through the commandList
        for(int i = 0; i < commandsList.length; i++){
            boolean performCommand = true;  //flag to decide whether to perform the current command or not
            int[] requiredKeys = customKeyBindings.get(commandsList[i]);    //Get the keys required for the current command
            for(int f = 0; f < requiredKeys.length; f++){                   //look through the required keys
                if(!currentlyPressedKeys.contains(requiredKeys[f]))         //if the required key is not pressed
                    performCommand = false;                                 //don't perform the command
            }
            if(performCommand) {
              validCommands.add(commandsList[i]);
            }
        }

        //Handle the case where multiple commands are triggered (e.g, control left triggers previous type as well as previous instance
        //Favor the command with the highest number of keystroke requirements.
        if(validCommands.size() != 0){
            String winner = validCommands.get(0);
            int winnerNumKeys = customKeyBindings.get(winner).length;
            for(int i = 0; i < validCommands.size(); i++){
                int tempLen = customKeyBindings.get(validCommands.get(i)).length;
                if(tempLen > winnerNumKeys)
                    winner = validCommands.get(i);
            }
            alertClient(winner);
        }
    }

    private void alertClient(String commandName){
        for(int i = 0; i < clients.size(); i++){
            //System.out.println("Telling client to perform " + commandName);
            clients.get(i).performAction(commandName);
        }
    }
    //Old way
//    public void update(String keyname, Boolean status){
//        keyMap.put(keyname,status);
////        System.out.println("Setting " + keyname + " to " + status);
//        for(int i = 0; i < clients.size(); i++){
//            clients.get(i).updateKeysPressed(keyMap);
//        }
//    }
}