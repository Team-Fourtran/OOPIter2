package views;

/**
 * Created by TK on 3/13/17.
 */

import javax.swing.*;
import java.awt.event.WindowEvent;

public class UnitCreationDialog {
    public static final String[] units = {"Explorer", "Colonist", "Melee", "Ranged"};
    private String unitType;
    private JFrame frame;

    public void createDialog(){
        frame = new JFrame("Unit Type Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        unitType = (String) JOptionPane.showInputDialog(frame,
                "Select a unit type to create",
                "Unit Type Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                units,
                units[0]);

    }
    public String returnStructureType(){
        if(unitType == null)
            return "null";
        else
            return unitType.toLowerCase();
    }
    public void closeDialog(){
        frame.setVisible(false);
        frame.dispose();
    }
}
