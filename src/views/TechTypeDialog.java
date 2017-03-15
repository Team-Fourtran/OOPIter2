package views;


import javax.swing.*;

public class TechTypeDialog {
    public static final String[] types = {"attack", "defense", "armor", "health", "movement",
                                            "efficiency", "visibility", "density", "radius", "food",
                                            "ore", "energy"};
    private String techType;
    private JFrame frame;

    public void createDialog(){
        frame = new JFrame("Tech Type Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        techType = (String) JOptionPane.showInputDialog(frame,
                "Select a Tech type to research",
                "Tech Type Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                types,
                types[0]);

    }

    public String returnTechType(){
        if(techType == null)
            return "null";
        else
            return techType.toLowerCase();
    }
    public void closeDialog(){
        frame.setVisible(false);
        frame.dispose();
    }
}
