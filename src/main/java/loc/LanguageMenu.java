package loc;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LanguageMenu extends JFrame implements ActionListener {

    private JButton confirmButton;
    private JButton cancelButton;
    private Container contentPane;
    private JPanel buttonPane;

    public LanguageMenu() {

        confirmButton = new JButton("Confirm");
        cancelButton = new JButton("Cancel");
        buttonPane = new JPanel();
        buttonPane.add(confirmButton);
        buttonPane.add(cancelButton);
        contentPane = this.getContentPane();
        LanguageCheckboxTable languageTab = new LanguageCheckboxTable();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(languageTab), BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.SOUTH);
        pack();
        setLocation(150, 150);
        setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {

        } else if (e.getSource() == cancelButton) {

        }
    }

}
