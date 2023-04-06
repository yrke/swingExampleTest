package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.lang.Integer;
import java.util.List;

public class Window extends JFrame {

    JList<String> list = new JList<>();
    DefaultListModel<String> listModel = new DefaultListModel<>();
    JScrollPane jsp = new JScrollPane();

    JButton add = new JButton() {{
        setAction(new AbstractAction("Add Random") {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.addElement("" + rndInt(100));
            }
        });
    }};


    JButton delete = new JButton() {{
        setAction(new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelection(list);
            }
        });
    }};


    JButton sortAsc = new JButton() {{
        setAction(new AbstractAction("Sort Ascending") {
            @Override
            public void actionPerformed(ActionEvent e) {
                sort(listModel);
            }
        });
    }};

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
            listModel.addElement("" + rndInt(100));
        }

        list.setModel(listModel);

        jsp.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
        getContentPane().add(jsp, gbc);

    }


    /**
     * Returns a pseudorandom integer inclusive 0 and exclusive bound.
     * @param bound upper bound of pseudorandom integer.
     * @return pseudorandom integer.
     */
    private int rndInt(int bound) throws IllegalArgumentException{
        if (bound < 0 ) {
            throw new IllegalArgumentException("Bound is negative!");
        }
        Random rnd = new Random();
        return rnd.nextInt(bound);
    }


    /**
     * Deletes a selection in the supplied list. No action is taken, if no elements have been selected.
     * @param list List with selection.
     */
    private void deleteSelection(JList<String> list) {
        int maxSelectionIdx = list.getMaxSelectionIndex();
        DefaultListModel<String> listModel = (DefaultListModel<String>) list.getModel();
        if (maxSelectionIdx >= 0) {
            listModel.removeRange(list.getSelectedIndex(), maxSelectionIdx);
        }
    }


    /**
     * Sorts a DefaultListModel in ascending order, using the
     * {@link java.util.Collections#sort(List) Collections.sort(List) method}.
     * @param listModel {@link javax.swing.DefaultListModel<String> DefaultListModel<String>} to be sorted.
     */
    private void sort(DefaultListModel<String> listModel) {
        ArrayList<Integer> sortedList = new ArrayList<>();
        for (int i = 0; i < listModel.getSize(); i++) {
            sortedList.add(Integer.parseInt(listModel.getElementAt(i)));
        }

        Collections.sort(sortedList);
        listModel.removeAllElements();

        for (int i = 0; i < sortedList.size(); i++) {
            listModel.addElement(Integer.toString(sortedList.get(i)));
        }
    }
}
