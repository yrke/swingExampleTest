package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Window extends JFrame {

    JList<String> list = new JList<>();
    DefaultListModel<String> listModel = new DefaultListModel<>();
    JScrollPane jsp = new JScrollPane();

    JButton add = new JButton() {{
        setAction(new AbstractAction("Add Random") {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.addElement("" + RandomBoundedInt(100));
            }
        });
    }};
    JButton delete = new JButton() {{
        setAction(new AbstractAction("Delete selection") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] listArr = listModel.toArray();
                Object[] listSelection = list.getSelectedValuesList().toArray();

                int j = 0;

                // Loop while there is elements in array to iterate over or until reached end of selection
                for (int i = 0; i < listArr.length || j == listSelection.length - 1; ++i) {
                    if (listArr[i] == listSelection[j]) {
                        listModel.remove(i);
                        ++j;
                    }
                }
            }
        });
    }};
    
    JButton sortAsc = new JButton() {{
        setAction(new AbstractAction("Sort Ascending") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save as string list
                List<String> listCollection = Collections.list(listModel.elements());
                

                // Convert to list of integers to sort numerically instead of alphabetically
                List<Integer> listInt = new ArrayList<>();

                for (String listEntry : listCollection) {
                    listInt.add(Integer.parseInt(listEntry));
                }

                Collections.sort(listInt);

                // Update data
                listModel.clear();

                for (int listIntEntry : listInt) {
                    listModel.addElement(Integer.toString(listIntEntry));
                }
            }
        });
    }};

    JButton showDialogMinMaxAndAvg = new JButton() {{
        setAction(new AbstractAction("Show min, max and average value") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame popup = new JFrame("Popup");
                popup.setSize(300, 300);

                // Make int array from list
                int[] listArr = new int[listModel.size()];

                for (int i = 0; i < listModel.size(); ++i) {
                    listArr[i] = Integer.parseInt(listModel.getElementAt(i));
                }

                // Set min to max and max to min value to ensure a number from the list is assigned
                int min = Integer.MAX_VALUE;
                int max = Integer.MIN_VALUE;

                int sum = 0;

                // Get min, max and sum
                for (int i = 0; i < listArr.length; ++i) {
                    if (listArr[i] <= min) {
                        min = listArr[i];
                    }
                    
                    if (listArr[i] >= max) {
                        max = listArr[i];
                    }

                    sum += listArr[i];
                }

                int avg = sum / listArr.length;

                JLabel minLabel = new JLabel("Min: " + min);
                JLabel maxLabel = new JLabel("Max: " + max);
                JLabel avgLabel = new JLabel("Avg: " + avg);

                popup.setLayout(new FlowLayout());

                popup.add(minLabel);
                popup.add(maxLabel);
                popup.add(avgLabel);

                popup.setVisible(true);
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
        gbc.gridy = 3;
        getContentPane().add(showDialogMinMaxAndAvg, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;

        for (int i = 0; i < 10; i++) {
            listModel.addElement("" + RandomBoundedInt(100));
        }

        list.setModel(listModel);

        jsp.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
        getContentPane().add(jsp, gbc);

        // Close program when main frame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Function that generates a psuedu-random integer bounded between 0 and upper bound
     * 
     * @param {int} upper Upper bound of random number.
     * 
     * @returns {int} randomInt Random int between bounds.
     */
    private int RandomBoundedInt(int upperBound) {
        Random random = new Random();
        return random.nextInt(upperBound);
    }
}
