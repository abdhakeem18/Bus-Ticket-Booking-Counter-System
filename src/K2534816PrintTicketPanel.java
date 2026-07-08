import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class K2534816PrintTicketPanel extends JPanel {
    private final K2534816BookingService service;
    private final JTextField bookingIdField = new JTextField(24);
    private final JTextArea ticketArea = new JTextArea();

    public K2534816PrintTicketPanel(K2534816BookingService service) {
        this.service = service;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));
        ticketArea.setEditable(false);
        add(K2534816FormHelper.title("Print Ticket"), BorderLayout.NORTH);
        add(createTopPanel(), BorderLayout.CENTER);
        add(new JScrollPane(ticketArea), BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(bookingIdField);
        JButton printButton = K2534816FormHelper.primaryButton("Show Ticket");
        printButton.addActionListener(event -> printTicket());
        panel.add(printButton);
        return panel;
    }

    private void printTicket() {
        java.util.Optional<K2534816Booking> result = service.findById(bookingIdField.getText());
        if (result.isPresent()) {
            ticketArea.setText(result.get().generateTicket());
        } else {
            ticketArea.setText("Booking not found.");
        }
    }
}
