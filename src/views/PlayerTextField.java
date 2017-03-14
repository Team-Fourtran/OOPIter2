package views;

import javax.swing.*;

/**
 * Created by TK on 3/13/17.
 */
public class PlayerTextField extends JTextField {
    private String playerName = "";

    public PlayerTextField(){
        this.setText(playerName);
        this.setEditable(false);
    }
}
