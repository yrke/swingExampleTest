package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Window extends JFrame {

    JList<String> list = new JList<>();
    DefaultListModel<String> listModel = new DefaultListModel<>();
    JScrollPane jsp = new JScrollPane();

    JButton add = new JButton() {{
        setAction(new AbstractAction("Add Random") {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.addElement("" + Math.random());
            }
        });
    }};
    JButton delete = new JButton("Delete selection"); //TODO
    JButton sortAsc = new JButton("Sort Ascending"); //TODO

    JButton showDialogMinMaxAndAvg = new JButton("Show min, max and average value"); //TODO

    public Window() throws HeadlessException {
        super();
        setSize(800, 600);

        getContentPane().setLayout(new GridBagLayout());

        var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        getContentPane().add(add, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        getContentPane().add(delete, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        getContentPane().add(sortAsc, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;

        for (int i = 0; i < 10; i++) {
            listModel.addElement(""+Math.random());
        }

        list.setModel(listModel);

        jsp.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
        getContentPane().add(jsp, gbc);

    }
}
