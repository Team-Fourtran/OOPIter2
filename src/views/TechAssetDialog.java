package views;

import javax.swing.*;

public class TechAssetDialog {
    public static final String[] assets = {"capital", "farm", "mine", "powerplant", "fort",
                                        "observationtower", "university", "explorer", "colonist",
                                        "melee", "ranged", "worker"};
    private String techAsset;
    private JFrame frame;

    public void createDialog(){
        frame = new JFrame("Unit Type Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        techAsset = (String) JOptionPane.showInputDialog(frame,
                "Select an asset to apply to",
                "Asset Type Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                assets,
                assets[0]);

    }

    public String returnTechAsset(){
        if(techAsset == null)
            return "null";
        else
            return techAsset.toLowerCase();
    }
    public void closeDialog(){
        frame.setVisible(false);
        frame.dispose();
    }
}
