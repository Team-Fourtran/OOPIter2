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
    }

    @Override
    public void update(Player currentPlayer) {
        TableFormatVisitor vis = new TableFormatVisitor(currentPlayer);
        vis.visitStructureManager(currentPlayer.getStructures());
        NonEditableTable table = vis.getFormattedTable();
        if(table == null){
            table.setRowCount(0);
            return;
        }
        structureTable.setModel(table);
        structureTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        structureTable.setPreferredSize(d);
    }
}

