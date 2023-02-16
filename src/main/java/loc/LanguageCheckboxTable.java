package loc;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LanguageCheckboxTable extends JFrame {

    private JTable table;

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
        table = new JTable(tableModel) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);

    }


}
