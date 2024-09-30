import javax.swing.*;
import java.awt.*;

public class EditNoteUI extends JDialog {
    private final JTextField noteField;
    private final String originalNote;
    private final NoteUI parent;

    public EditNoteUI(NoteUI parent, String existingNote) {
        this.parent = parent;
        this.originalNote = existingNote;

        setTitle(existingNote == null ? "add" : "edit");
        setSize(300, 150);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        noteField = new JTextField(existingNote != null ? existingNote : "");
        add(noteField, BorderLayout.CENTER);

        JButton saveButton = new JButton("save");
        saveButton.addActionListener(e -> saveNote());
        add(saveButton, BorderLayout.SOUTH);
    }

    private void saveNote() {
        String newNote = noteField.getText();
        if (!newNote.isEmpty()) {
            parent.saveOrUpdate(originalNote, newNote);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error");
        }
    }
}
