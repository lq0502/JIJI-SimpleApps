import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class NoteUI extends JFrame {
    private JList<String> noteList;
    private DefaultListModel<String> listModel;
    private NoteDatabase db;

    public NoteUI() {
        db = new NoteDatabase();

        // 创建UI
        setTitle("记事本应用");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        noteList = new JList<>(listModel);
        loadNotes();

        JScrollPane scrollPane = new JScrollPane(noteList);
        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("添加");
        JButton editButton = new JButton("编辑");
        JButton deleteButton = new JButton("删除");

        addButton.addActionListener(e -> openEditDialog(null));
        editButton.addActionListener(e -> openEditDialog(noteList.getSelectedValue()));
        deleteButton.addActionListener(e -> deleteNote());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadNotes() {
        listModel.clear();
        List<String> notes = db.getAllNotes();
        for (String note : notes) {
            listModel.addElement(note);
        }
    }

    private void openEditDialog(String existingNote) {
        EditNoteUI dialog = new EditNoteUI(this, existingNote);
        dialog.setVisible(true);
    }

    public void saveOrUpdate(String oldNote, String newNote) {
        if (oldNote == null) {
            db.addNote(newNote);
        } else {
            db.updateNote(oldNote, newNote);
        }
        loadNotes();
    }

    private void deleteNote() {
        String selectedNote = noteList.getSelectedValue();
        if (selectedNote != null) {
            db.deleteNote(selectedNote);
            loadNotes();
        }
    }
}
