import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class K2534816DeleteBookingPanel extends JPanel {
    private final K2534816BookingService service;
    private final JTextField bookingIdField = new JTextField(24);
    private final JTextArea detailsArea = new JTextArea();

    public K2534816DeleteBookingPanel(K2534816BookingService service) {
        this.service = service;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));
        add(K2534816FormHelper.title("Delete Booking"), BorderLayout.NORTH);
        add(createTopPanel(), BorderLayout.CENTER);
        detailsArea.setEditable(false);
        add(detailsArea, BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(bookingIdField);
        JButton searchButton = K2534816FormHelper.primaryButton("Find");
        searchButton.addActionListener(event -> findBooking());
        panel.add(searchButton);
        JButton deleteButton = K2534816FormHelper.primaryButton("Delete");
        deleteButton.addActionListener(event -> deleteBooking());
        panel.add(deleteButton);
        return panel;
    }

    private void findBooking() {
        java.util.Optional<K2534816Booking> result = service.findById(bookingIdField.getText());
        if (result.isPresent()) {
            detailsArea.setText(result.get().generateTicket());
        } else {
            detailsArea.setText("Booking not found.");
        }
    }

    private void deleteBooking() {
        int confirmation = JOptionPane.showConfirmDialog(this, "Cancel this reservation?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            if (service.deleteBooking(bookingIdField.getText().trim())) {
                detailsArea.setText("Booking marked as cancelled and seat released.");
            } else {
                detailsArea.setText("Booking not found.");
            }
        }
    }
}
