package exam;

import javax.swing.*;
import java.awt.*;
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

        buttonDelete = new JButton("Erledigt") ;

        buttonDeleteAll = new JButton("Liste löschen") ;
        buttonPanel.add(buttonDeleteAll) ;
        buttonPanel.add(buttonDelete) ;
        buttonPanel.add(buttonAdd) ;
        content.add(buttonPanel, BorderLayout.SOUTH) ;

        JScrollPane listScrollPane = new JScrollPane() ;
        listToDo = new JList() ;
        listScrollPane.add(listToDo) ;
        content.add(listScrollPane, BorderLayout.CENTER) ;

        inputToDo = new JTextField() ;
        inputToDo.setText("Aufgabe eingeben...") ;
        content.add(inputToDo, BorderLayout.NORTH) ;

        this.setSize(350, 300) ;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
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
        String br = System.getProperty("line.separator"); ;
        String tmp = "" ;
        ListModel model = listToDo.getModel() ;


        for(int i = 0; i < model.getSize(); i++){
            tmp = (String) model.getElementAt(i) ;
            try {
                out.write(tmp) ;
                out.write(br) ;
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Fehler beim speichern der Liste.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return false ;
            }
        }

        return true ;
    }

    public boolean loadList() {
        return this.saveList("") ;
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
        String tmp = "" ;
        DefaultListModel<String> model = (DefaultListModel<String>) listToDo.getModel() ;

        model.removeAllElements() ;
        try {
            while ((tmp = in.readLine()) != null) {
                model.addElement(tmp) ;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Fehler beim lesen der Liste.", "Fehler", JOptionPane.ERROR_MESSAGE);
        }

        return true ;

    }
}
