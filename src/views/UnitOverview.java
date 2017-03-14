package views;

import models.playerAsset.Assets.Player;
import models.visitor.TableFormatVisitor;

import javax.swing.*;
import java.awt.*;

public class UnitOverview extends JPanel implements DataTable{
    JTable unitTable;
    Dimension d;

    public UnitOverview(Dimension d){
        this.unitTable = new JTable();
        this.d = d;
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

    @Override
    public void update(Player currentPlayer) {
        TableFormatVisitor vis = new TableFormatVisitor(currentPlayer);
        vis.visitUnitManager(currentPlayer.getUnits());
        NonEditableTable table = vis.getFormattedTable();
        if(table == null){
            return;
        }
        unitTable.setModel(table);
        unitTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        unitTable.setPreferredSize(d);
    }
}
