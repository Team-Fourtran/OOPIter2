package views;

import javax.swing.*;
import java.awt.*;

public class UnitOverview extends JPanel {
    private String[] unitColumnStats = {"UnitID", "Unit Type", "Offensive Damage",
            "Defensive Damage", "Armor",
            "Max Health", "Current Health", "Upkeep", "Location"};

    public UnitOverview(Dimension d){
        JTable unitTable = new JTable();
        String[][] unitData = new String[25][9];
        unitData[0][0] = "YO";
        NonEditableTable table = new NonEditableTable(unitData, unitColumnStats);
        unitTable.setModel(table);
        unitTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        unitTable.setPreferredSize(d);

        this.setLayout(new BorderLayout());
        this.setPreferredSize(d);
        this.add(new JScrollPane(unitTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        Dimension buttonDimension = new Dimension(100, 30);
        JButton createArmyButton = new JButton("Create Army");
        buttonPanel.add(createArmyButton);
        createArmyButton.setPreferredSize(buttonDimension);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}
