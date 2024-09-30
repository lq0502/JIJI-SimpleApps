import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditNoteUI extends JDialog {
    private JTextField noteField;
    private String originalNote;
    private NoteUI parent;

    public EditNoteUI(NoteUI parent, String existingNote) {
        this.parent = parent;
        this.originalNote = existingNote;

        setTitle(existingNote == null ? "添加记事本条目" : "编辑记事本条目");
        setSize(300, 150);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        noteField = new JTextField(existingNote != null ? existingNote : "");
        add(noteField, BorderLayout.CENTER);

        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(e -> saveNote());
        add(saveButton, BorderLayout.SOUTH);
    }

    private void saveNote() {
        String newNote = noteField.getText();
        if (!newNote.isEmpty()) {
            parent.saveOrUpdate(originalNote, newNote);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "请输入有效的条目");
        }
    }
}
