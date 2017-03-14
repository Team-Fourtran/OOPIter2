package views;

import models.playerAsset.Assets.Player;
import models.visitor.TableFormatVisitor;

import javax.swing.*;
import java.awt.*;

public class StructureOverview extends JPanel implements DataTable {
    JTable structureTable;
    Dimension d;

    public StructureOverview(Dimension d){
        this.structureTable = new JTable();
        this.d = d;
        this.setLayout(new BorderLayout());
        //this.setPreferredSize(d);
        this.add(new JScrollPane(structureTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        Dimension buttonDimension = new Dimension(100, 30);
        JButton createArmyButton = new JButton("Create Army");
        buttonPanel.add(createArmyButton);
        createArmyButton.setPreferredSize(buttonDimension);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void update(Player currentPlayer) {
        TableFormatVisitor vis = new TableFormatVisitor();
        vis.visitStructureManager(currentPlayer.getStructures());
        NonEditableTable table = vis.getFormattedTable();
        if(table == null){
            return;
        }
        structureTable.setModel(table);
        structureTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        structureTable.setPreferredSize(d);
    }
}

