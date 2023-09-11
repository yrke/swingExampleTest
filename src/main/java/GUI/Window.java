package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Comparator;
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
                DeleteSelection(list, listModel);
            }
        });
    }};
    
    JButton sortAsc = new JButton() {{
        setAction(new AbstractAction("Sort Ascending") {
            @Override
            public void actionPerformed(ActionEvent e) {
                SortAscending(listModel);
            }
        });
    }};

    JButton showDialogMinMaxAndAvg = new JButton() {{
        setAction(new AbstractAction("Show min, max and average value") {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowDialogPopup(listModel);
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
     * Deletes a selection from the list assuming sequential order.
     * 
     * @param {JList<String>} list The JList. 
     * @param {DefaultListModel<String>} listModel The model of the list.
     * 
     * @returns {void} Nothing.
     */
    private void DeleteSelection(JList<String> list, DefaultListModel<String> listModel) {
        List<String> listSelection = list.getSelectedValuesList(); // Get selected list (it is always in sequential order)

        int selectionSize = listSelection.size();

        if (selectionSize == 0) return; // Early exit if list is empty

        int i = 0;
        int j = 0;

        // While we did not delete entire selection
        while (j < selectionSize) {
            // If current index on model is equal to current index of selection
            if (listModel.getElementAt(i) == listSelection.get(j)) {
                // Remove it from the list and look for next selected value 
                listModel.remove(i); // (I is not incremented since a element is deleted)
                ++j;
            }
            else {
                // Increment to next index
                ++i;
            }
        }
    }

    /**
     * Sorts the list in ascending order.
     * 
     * @param {DefaultListModel<String>} listModel The model of the list.
     * 
     * @returns {void} Nothing.
     */
    private void SortAscending(DefaultListModel<String> listModel) {
        // Save as string list
        List<String> listCollection = Collections.list(listModel.elements());
        
        Comparator<String> numericallyCompare = new Comparator<String>() {
            @Override
            public int compare(String strA, String strB) {
                int intA = Integer.parseInt(strA);
                int intB = Integer.parseInt(strB);

                return Integer.compare(intA, intB);
            }
        };

        Collections.sort(listCollection, numericallyCompare);
        
        listModel.clear();
        
        for (int i = 0; i < listCollection.size(); ++i) {
            listModel.add(i, listCollection.get(i));
        }
    }

    /**
     * Shows popup window displaying min value, max value, and avg value of list.
     * 
     * @param {DefaultListModel<String>} listModel The model of the list.
     * 
     * @returns {void} Nothing.
     */
    private void ShowDialogPopup(DefaultListModel<String> listModel) {
        JFrame popup = new JFrame("Popup");
        popup.setSize(300, 300);

        // Make int array from string list
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
