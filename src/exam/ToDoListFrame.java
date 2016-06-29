package exam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * Created by fricke on 29.06.2016.
 */
public class ToDoListFrame extends JFrame {
    protected JButton buttonAdd ;
    protected JButton buttonDelete ;
    protected JButton buttonDeleteAll ;

    protected JList listToDo ;

    protected JTextField inputToDo ;

    protected Container content ;

    public ToDoListFrame(){
        content = this.getContentPane() ;

        JPanel buttonPanel = new JPanel(new FlowLayout()) ;
        buttonAdd = new JButton("Hinzufügen") ;
        buttonAdd.addActionListener(e -> add());

        buttonDelete = new JButton("Erledigt") ;
        buttonDelete.addActionListener(e -> delete());

        buttonDeleteAll = new JButton("Liste löschen") ;
        buttonDeleteAll.addActionListener(e -> deleteAll());

        buttonPanel.add(buttonDeleteAll) ;
        buttonPanel.add(buttonDelete) ;
        buttonPanel.add(buttonAdd) ;
        content.add(buttonPanel, BorderLayout.SOUTH) ;

        listToDo = new JList<>(new DefaultListModel<>()) ;
        listToDo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listToDo.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_DELETE)
                    delete();
            }
        });
        JScrollPane listScrollPane = new JScrollPane(listToDo) ;
        content.add(listScrollPane, BorderLayout.CENTER) ;

        inputToDo = new JTextField() ;
        inputToDo.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    add();
            }
        });
        content.add(inputToDo, BorderLayout.NORTH) ;

        this.setSize(350, 300) ;
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "ToDo speichern vor dem Schließen?",
                        "Speichern?", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);

                if (confirm == 0) {
                    saveList() ;
                }

                System.exit(0);
            }
        } );
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            return false;
        });

        int confirm = JOptionPane.showOptionDialog(
                null, "Gespeicherte ToDo laden?",
                "Laden?", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (confirm == 0) {
            loadList() ;
        }

        this.setVisible(true);
    }

    private void deleteAll() {
        DefaultListModel<String> model = (DefaultListModel<String>)listToDo.getModel();
        model.removeAllElements();
    }

    private void delete() {
        DefaultListModel<String> model = (DefaultListModel<String>)listToDo.getModel();
        if (listToDo.getSelectedIndex() != -1) {
            model.remove(listToDo.getSelectedIndex());
        }else{
            JOptionPane.showMessageDialog(this, "Bitte wählen sie einen Eintrag aus!", "Eintrag auswählen", JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean saveList() {
        return this.saveList("") ;
    }

    public boolean saveList(String path){
        if(path.equals("")){
            path = "toDo.dat" ;
        }
        FileWriter out ;
        try {
            out = new FileWriter(path);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Fehler beim speichern der Liste.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return false ;
        }

        // Newline operator
        String br = System.getProperty("line.separator");
        String tmp = "" ;
        DefaultListModel<String> model = (DefaultListModel<String>) listToDo.getModel() ;
        BufferedWriter bw = new BufferedWriter(out);

        try {
            bw.write("");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Fehler beim speichern der Liste.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return false ;
        }

        for(int i = 0; i < model.getSize(); i++){
            tmp = model.getElementAt(i) ;
            try {
                bw.append(tmp) ;
                bw.append(br) ;
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Fehler beim speichern der Liste.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return false ;
            }
        }

        try {
            bw.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim speichern der Liste.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return false ;
        }

        return true ;
    }

    public boolean loadList() {
        return this.loadList("") ;
    }

    public boolean loadList(String path) {
        if(path.equals("")){
            path = "toDo.dat" ;
        }
        BufferedReader in ;
        try {
            in = new BufferedReader(new FileReader(path));
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Fehler beim lesen der Liste.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return false ;
        }

        // Newline operator
        String tmp ;
        DefaultListModel<String> model = (DefaultListModel<String>) listToDo.getModel() ;

        this.deleteAll() ;
        try {
            while ((tmp = in.readLine()) != null) {
                if(!tmp.equals("") && !tmp.equals(" ")) {
                    model.addElement(tmp);
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Fehler beim lesen der Liste.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return false ;
        }

        return true ;

    }

    private void add() {
        DefaultListModel<String> model = (DefaultListModel<String>)listToDo.getModel();
        int index = listToDo.getSelectedIndex();
        if (index == -1) {
            int oldSize = model.getSize() ;
            model.setSize(oldSize+1);
            model.add(oldSize, inputToDo.getText());
        } else {
            model.add(index, inputToDo.getText());
        }

        inputToDo.setText("");
        inputToDo.requestFocus(true) ;
    }
}
