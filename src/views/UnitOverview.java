package views;

import javax.swing.*;
import java.awt.*;

public class UnitOverview extends JTable {
    private JPanel unitOVPanel;

    public UnitOverview(Dimension d){
        setPreferredSize(d);
        unitOVPanel = new JPanel(new BorderLayout());
        unitOVPanel.setPreferredSize(d);
        unitOVPanel.add(new JScrollPane(this), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        Dimension buttonDimension = new Dimension(100, 30);
        JButton createArmyButton = new JButton("Create Army");
        buttonPanel.add(createArmyButton);
        createArmyButton.setPreferredSize(buttonDimension);
        unitOVPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public JPanel getPanel(){
        return unitOVPanel;
    }
}
