package exam;

import javax.swing.*;
import java.awt.*;

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

        listToDo = new JList() ;

        inputToDo = new JTextField() ;
        inputToDo.setText("Aufgabe eingeben...") ;
    }
}
