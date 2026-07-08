import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class K2534816CreateBookingPanel extends JPanel {
    private final K2534816BookingService service;
    private final Runnable afterSave;
    private final JTextField nicField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField contactField = new JTextField();
    private final K2534816DatePicker datePicker = new K2534816DatePicker();
    private final JComboBox<K2534816Route> routeBox = new JComboBox<>(K2534816Route.values());
    private final JComboBox<K2534816BusType> busBox = new JComboBox<>(K2534816BusType.values());
    private final JComboBox<K2534816TravelType> travelBox = new JComboBox<>(K2534816TravelType.values());
    private final JTextArea resultArea = new JTextArea(11, 35);

    public K2534816CreateBookingPanel(K2534816BookingService service) {
        this(service, null);
    }

    public K2534816CreateBookingPanel(K2534816BookingService service, Runnable afterSave) {
        this.service = service;
        this.afterSave = afterSave;
        setPreferredSize(new Dimension(820, 470));
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));
        add(K2534816FormHelper.title("Create Booking"), BorderLayout.NORTH);
        add(createForm(), BorderLayout.CENTER);
        resultArea.setEditable(false);
        add(resultArea, BorderLayout.EAST);
    }

    private JPanel createForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        K2534816FormHelper.addRow(panel, gbc, 0, "NIC/Passport", nicField);
        K2534816FormHelper.addRow(panel, gbc, 1, "Customer Name", nameField);
        K2534816FormHelper.addRow(panel, gbc, 2, "Contact Number", contactField);
        K2534816FormHelper.addRow(panel, gbc, 3, "Destination", routeBox);
        K2534816FormHelper.addRow(panel, gbc, 4, "Travel Date", datePicker);
        K2534816FormHelper.addRow(panel, gbc, 5, "Bus Type", busBox);
        K2534816FormHelper.addRow(panel, gbc, 6, "Travel Type", travelBox);

        JButton createButton = K2534816FormHelper.primaryButton("Create Booking");
        createButton.addActionListener(event -> createBooking());
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(createButton, gbc);
        return panel;
    }

    private void createBooking() {
        try {
            K2534816Booking booking = service.createBooking(nicField.getText().trim(), nameField.getText().trim(), contactField.getText().trim(),
                    (K2534816Route) routeBox.getSelectedItem(), K2534816InputValidator.parseFutureDate(datePicker.getDateText()),
                    (K2534816BusType) busBox.getSelectedItem(), (K2534816TravelType) travelBox.getSelectedItem());
            resultArea.setText(booking.generateTicket());
            clearForm();
            notifyAfterSave();
            JOptionPane.showMessageDialog(this, "Booking created successfully.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void notifyAfterSave() {
        if (afterSave != null) {
            afterSave.run();
        }
    }

    private void clearForm() {
        nicField.setText("");
        nameField.setText("");
        contactField.setText("");
        datePicker.setDate(LocalDate.now().plusDays(1));
        routeBox.setSelectedIndex(0);
        busBox.setSelectedIndex(0);
        travelBox.setSelectedIndex(0);
    }
}
