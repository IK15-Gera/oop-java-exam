package exam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by fricke on 29.06.2016.
 */
public class ToDoListFrame2 extends JFrame {
    protected JButton buttonAdd ;
    protected JButton buttonDelete ;
    protected JButton buttonDeleteAll ;

    protected JList<String> listToDo ;

    protected JTextField inputToDo ;

    protected Container content ;

    public ToDoListFrame2(){
        content = this.getContentPane() ;

        JPanel buttonPanel = new JPanel(new FlowLayout()) ;
        buttonAdd = new JButton("Hinzufügen") ;
        buttonAdd.addActionListener(e -> addEntry());

        buttonDelete = new JButton("Erledigt") ;
        buttonDelete.addActionListener(e -> delete());

        buttonDeleteAll = new JButton("Liste löschen") ;
        buttonDeleteAll.addActionListener(e -> deleteAll());

        buttonPanel.add(buttonDeleteAll) ;
        buttonPanel.add(buttonDelete) ;
        buttonPanel.add(buttonAdd) ;

        listToDo = new JList<>(new DefaultListModel<>()) ;
        listToDo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_DELETE)
                    delete();
            }
        });

        inputToDo = new JTextField() ;
        inputToDo.setText("Aufgabe eingeben...") ;
    }

    private void deleteAll() {
        DefaultListModel<String> model = (DefaultListModel<String>)listToDo.getModel();
        model.removeAllElements();
    }

    private void delete() {
        DefaultListModel<String> model = (DefaultListModel<String>)listToDo.getModel();
        if (listToDo.getSelectedIndex() != -1) {
            model.remove(listToDo.getSelectedIndex());
        }
    }

    private void addEntry() {
        DefaultListModel<String> model = (DefaultListModel<String>)listToDo.getModel();
        int index = listToDo.getSelectedIndex();
        if (index == -1) {
            model.add(model.getSize() + 1, inputToDo.getText());
        } else {
            model.add(index, inputToDo.getText());
        }
    }
}
