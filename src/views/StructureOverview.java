package views;

        import javax.swing.*;
        import java.awt.*;

public class StructureOverview extends JPanel {
    private String[] structureColumnStats = {"StructuresID", "Structure Type", "Offensive Damage",
            "Defensive Damage", "Armor", "Maximum Health",
            "Current Health", "Upkeep", "Location"};

    public StructureOverview(Dimension d){
        JTable structureTable = new JTable();
        String[][] structureData = new String[25][9];
        structureData[0][0] = "YO";
        NonEditableTable table = new NonEditableTable(structureData, structureColumnStats);
        structureTable.setModel(table);
        structureTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        structureTable.setPreferredSize(d);

        this.setLayout(new BorderLayout());
        this.setPreferredSize(d);
        this.add(new JScrollPane(structureTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        Dimension buttonDimension = new Dimension(100, 30);
        JButton createArmyButton = new JButton("Create Army");
        buttonPanel.add(createArmyButton);
        createArmyButton.setPreferredSize(buttonDimension);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}

