import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
// import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class K2534816UpdateBookingPanel extends JPanel {
    private final K2534816BookingService service;
    private final Runnable afterSave;
    private final JTextField bookingIdField = new JTextField();
    private final JTextField nicField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField contactField = new JTextField();
    private final K2534816DatePicker datePicker = new K2534816DatePicker();
    private final JComboBox<K2534816Route> routeBox = new JComboBox<>(K2534816Route.values());
    private final JComboBox<K2534816BusType> busBox = new JComboBox<>(K2534816BusType.values());
    private final JComboBox<K2534816TravelType> travelBox = new JComboBox<>(K2534816TravelType.values());
    private final boolean showLoadButton;

    public K2534816UpdateBookingPanel(K2534816BookingService service) {
        this(service, true, null);
    }

    public K2534816UpdateBookingPanel(K2534816BookingService service, boolean showLoadButton) {
        this(service, showLoadButton, null);
    }

    public K2534816UpdateBookingPanel(K2534816BookingService service, boolean showLoadButton, Runnable afterSave) {
        this.service = service;
        this.afterSave = afterSave;
        this.showLoadButton = showLoadButton;
        setPreferredSize(new Dimension(620, 430));
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));
        add(K2534816FormHelper.title("Update Booking"), BorderLayout.NORTH);
        add(createForm(), BorderLayout.CENTER);
    }

    private JPanel createForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        K2534816FormHelper.addRow(panel, gbc, 0, "Booking ID", bookingIdField);
        K2534816FormHelper.addRow(panel, gbc, 1, "NIC/Passport", nicField);
        K2534816FormHelper.addRow(panel, gbc, 2, "Customer Name", nameField);
        K2534816FormHelper.addRow(panel, gbc, 3, "Contact Number", contactField);
        K2534816FormHelper.addRow(panel, gbc, 4, "Destination", routeBox);
        K2534816FormHelper.addRow(panel, gbc, 5, "Travel Date", datePicker);
        K2534816FormHelper.addRow(panel, gbc, 6, "Bus Type", busBox);
        K2534816FormHelper.addRow(panel, gbc, 7, "Travel Type", travelBox);

        if (showLoadButton) {
            JButton loadButton = K2534816FormHelper.primaryButton("Load Booking");
            loadButton.addActionListener(event -> loadBooking());
            gbc.gridx = 0;
            gbc.gridy = 8;
            panel.add(loadButton, gbc);
        }

        JButton updateButton = K2534816FormHelper.primaryButton("Save Changes");
        updateButton.addActionListener(event -> updateBooking());
        gbc.gridx = showLoadButton ? 1 : 0;
        gbc.gridwidth = showLoadButton ? 1 : 2;
        gbc.gridy = 8;
        panel.add(updateButton, gbc);
        return panel;
    }

    private void loadBooking() {
        java.util.Optional<K2534816Booking> result = service.findById(bookingIdField.getText());
        if (result.isPresent()) {
            loadBooking(result.get());
        } else {
            JOptionPane.showMessageDialog(this, "Booking not found.", "Search", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void loadBooking(K2534816Booking booking) {
        bookingIdField.setText(booking.getBookingId());
        bookingIdField.setEditable(showLoadButton);
        nicField.setText(booking.getCustomer().getNicPassport());
        nameField.setText(booking.getCustomer().getName());
        contactField.setText(booking.getCustomer().getContactNumber());
        routeBox.setSelectedItem(booking.getRoute());
        datePicker.setDate(booking.getTravelDate());
        busBox.setSelectedItem(booking.getBusType());
        travelBox.setSelectedItem(booking.getTravelType());
    }

    private void updateBooking() {
        try {
            service.updateBooking(bookingIdField.getText().trim(), nicField.getText().trim(), nameField.getText().trim(), contactField.getText().trim(),
                    (K2534816Route) routeBox.getSelectedItem(), K2534816InputValidator.parseFutureDate(datePicker.getDateText()),
                    (K2534816BusType) busBox.getSelectedItem(), (K2534816TravelType) travelBox.getSelectedItem());
            notifyAfterSave();
            JOptionPane.showMessageDialog(this, "Booking updated successfully.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void notifyAfterSave() {
        if (afterSave != null) {
            afterSave.run();
        }
    }
}
