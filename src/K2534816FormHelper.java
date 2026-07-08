import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class K2534816FormHelper {
    public static JLabel title(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 22));
        label.setForeground(new Color(15, 23, 42));
        return label;
    }

    public static JButton primaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(14, 116, 144));
        button.setForeground(Color.BLACK);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorder(javax.swing.BorderFactory.createEmptyBorder(9, 12, 9, 12));
        return button;
    }

    public static void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, java.awt.Component field) {
        if (field instanceof JTextField || field instanceof JComboBox || field instanceof K2534816DatePicker) {
            field.setPreferredSize(new Dimension(340, 30));
            field.setMinimumSize(new Dimension(300, 30));
            field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        }

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.insets = new Insets(6, 6, 6, 12);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
    }
}
