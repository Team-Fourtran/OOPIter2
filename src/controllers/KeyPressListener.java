package controllers;

import java.util.HashMap;

/* Defines an interface for listening to updated HashMaps of keypress values */
public interface KeyPressListener {
//    void updateKeysPressed(HashMap<String, Boolean> kp);
    void performAction(String actionName);  //Custom-key replacement for the above legacy option
}