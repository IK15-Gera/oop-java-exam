package exam;

import javax.swing.*;

public interface IToDo {
    void done(JList list);
    void deleteList(JList list);
    void add(JList list, String entry);
}
