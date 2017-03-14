package views;

import javax.swing.*;

public class StructureCreationDialog {
    public static final String[] structures = {"Capital", "Farm", "Mine", "Power Plant", "Fort", "Observation Tower", "University"};
    private String structureType = "";
    private JFrame frame;

    public void createDialog(){
        frame = new JFrame("Unit Type Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        structureType = (String) JOptionPane.showInputDialog(frame,
                "Select a unit type to create",
                "Unit Type Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                structures,
                structures[0]);

    }
    public String returnStructureType(){
        if(structureType == null)
            return "null";
        else
            return structureType.toLowerCase();
    }
    public void closeDialog(){
        frame.setVisible(false);
        frame.dispose();
    }

}
