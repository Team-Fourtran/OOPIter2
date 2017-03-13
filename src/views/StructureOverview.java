package views;

import javax.swing.*;
import java.awt.*;

public class StructureOverview extends JTable {
    private JPanel strOVPanel;

    public StructureOverview(Dimension d){
        setPreferredSize(d);
        strOVPanel = new JPanel(new BorderLayout());
        strOVPanel.setPreferredSize(d);
        strOVPanel.add(new JScrollPane(this), BorderLayout.CENTER);
    }

    public JPanel getPanel(){
        return strOVPanel;
    }
}
