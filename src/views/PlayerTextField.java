package views;

import javax.swing.*;

public class PlayerTextField extends JTextField {
    private String playerName = "";

    public PlayerTextField(){
    }

    public void update(String playerName){
        this.playerName = playerName;
    }
}
