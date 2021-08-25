package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class Window extends JFrame {

    JList<String> list = new JList<>();
    DefaultListModel<String> listModel = new DefaultListModel<>();
    JScrollPane jsp = new JScrollPane();

    JButton add = new JButton() {{
        setAction(new AbstractAction("Add Random") {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.addElement("" + (int)(Math.random() * 100));
            }
        });
    }};

    JButton delete = new JButton("Delete selection") {{
        setAction(new AbstractAction("Delete selection") {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> selectedValuesList = list.getSelectedValuesList();

                for(int i = 0; i < selectedValuesList.size(); i++) {
                    listModel.removeElement(selectedValuesList.get(i));
                }

            }
        });
    }};


    // Works, but not very pretty
    JButton sortAsc = new JButton("Sort Ascending") {{
        setAction(new AbstractAction("Sort Ascending") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] intArray = new int[listModel.getSize()];
                for(int i = 0; i < listModel.getSize(); i++) {
                    intArray[i] = Integer.parseInt(listModel.get(i));
                }

                Arrays.sort(intArray);

                for(int i = 0; i < listModel.getSize(); i++) {
                    listModel.set(i,Integer.toString(intArray[i]));
                }

            }
        });
    }};

    JButton showDialogMinMaxAndAvg = new JButton("Show min, max and average value") {{
        setAction(new AbstractAction("Show min, max and average value") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calculations ....
                int max, min;
                double avg = 0;

                max = min = Integer.parseInt(listModel.get(0));

                for(int i = 0; i < listModel.getSize(); i++) {
                    avg += Integer.parseInt(listModel.get(i));

                    if(Integer.parseInt(listModel.get(i)) > max) {
                        max = Integer.parseInt(listModel.get(i));
                    }

                    if(Integer.parseInt(listModel.get(i)) < min) {
                        min = Integer.parseInt(listModel.get(i));
                    }
                }

                avg = avg / listModel.getSize();


                // Dialog window
                java.awt.Window parentWindow = SwingUtilities.windowForComponent(showDialogMinMaxAndAvg);
                JDialog dialog = new JDialog(parentWindow);
                dialog.setLocationRelativeTo(showDialogMinMaxAndAvg);
                dialog.setModal(true);
                dialog.add(new JLabel("Min: " + min + " Max: " + max + " Avg: " + avg));
                dialog.pack();
                dialog.setVisible(true);

            }
        });
    }};

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
        gbc.gridx = 1;
        gbc.gridy = 2;
        getContentPane().add(showDialogMinMaxAndAvg, gbc);


        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;

        for (int i = 0; i < 10; i++) {
            listModel.addElement(""+(int)(Math.random()*100));
        }

        list.setModel(listModel);

        jsp.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
        getContentPane().add(jsp, gbc);

    }
}
