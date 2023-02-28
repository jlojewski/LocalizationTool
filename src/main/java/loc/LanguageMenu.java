package loc;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LanguageMenu extends JFrame implements ActionListener {

    private JButton confirmButton;
    private JButton cancelButton;
    private Container contentPane;
    private JPanel buttonPane;

    public LanguageMenu() {

        confirmButton = new JButton("Confirm");
        cancelButton = new JButton("Cancel");
        confirmButton.addActionListener(this);
        cancelButton.addActionListener(this);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            TranslationSettings currentSettings = new TranslationSettings(IOManager.getInstance().getSetOfUniqueLanguages());
            IOManager.getInstance().saveTranslationSettings(currentSettings);
            setVisible(false);

        } else if (e.getSource() == cancelButton) {
            setVisible(false);

        }
    }

}
