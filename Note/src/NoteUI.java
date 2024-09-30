import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NoteUI extends JFrame {
    private final JList<String> noteList;
    private final DefaultListModel<String> listModel;
    private final NoteDatabase db;

    public NoteUI() {
        db = new NoteDatabase();

        setTitle("メモ帳");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        noteList = new JList<>(listModel);
        loadNotes();

        JScrollPane scrollPane = new JScrollPane(noteList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = getjPanel();

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel getjPanel() {
        JButton addButton = new JButton("add");
        JButton editButton = new JButton("edit");
        JButton deleteButton = new JButton("delete");

        addButton.addActionListener(e -> openEditDialog(null));
        editButton.addActionListener(e -> openEditDialog(noteList.getSelectedValue()));
        deleteButton.addActionListener(e -> deleteNote());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        return buttonPanel;
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
