package loc;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedHashSet;
import java.util.Set;

public class LanguageCheckboxTable extends JTable implements TableModelListener {


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
        setPreferredScrollableViewportSize(getPreferredSize());
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//        getModel().addTableModelListener(this);


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


    @Override
    public void tableChanged(TableModelEvent e) {
    super.tableChanged(e); // invoking base method so that everything works; otherwise the table is blank
        int row = e.getFirstRow();
        int column = e.getColumn();
        String languageName;
        if (column == 1) {
            TableModel model = (TableModel)e.getSource();
            int languageNamePosition = column - 1;
            languageName = (String) model.getValueAt(row, languageNamePosition);
            Boolean languageChecked = (Boolean) model.getValueAt(row, column);
            if (languageChecked == true) {
                IOManager.getInstance().getSetOfUniqueLanguages().add(languageName);

            } else {
                IOManager.getInstance().getSetOfUniqueLanguages().remove(languageName);
            }
        }
    }


}
