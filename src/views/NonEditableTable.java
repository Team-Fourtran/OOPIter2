package views;

import javax.swing.table.DefaultTableModel;
/*
 * Class intended for use in Unit/Structure Overview
 */
public class NonEditableTable extends DefaultTableModel {
    public NonEditableTable (Object[][] tableData, Object[] colNames){
        super(tableData, colNames);
    }
    public boolean isCellEditable(int row, int column){
        return false;
    }

}