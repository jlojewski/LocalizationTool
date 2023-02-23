package loc;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LanguageCheckboxTable extends JTable {


    public LanguageCheckboxTable() {
        String[] tableColumnNames = {"Language",
                "Included"};


        Object[][] tableData = {
                {"English", false},
                {"Polish", false},
                {"German", false},
                {"French", false},
                {"Spanish", false},
                {"Chinese", false},
                {"Russian", false}
        };

        DefaultTableModel tableModel = new DefaultTableModel(tableData, tableColumnNames);
        setModel(tableModel);
        {

        }
        ;
        setPreferredScrollableViewportSize(getPreferredSize());
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

    }

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 0:
                return String.class;
            default:
                return Boolean.class;
        }
    }


}
