import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class K2534816ViewBookingsPanel extends JPanel {
    private final K2534816BookingService service;
    private final K2534816BookingTablePanel tablePanel = new K2534816BookingTablePanel();
    private final JTextField nicField = new JTextField(18);
    private final JComboBox<String> routeBox = new JComboBox<>();

    public K2534816ViewBookingsPanel(K2534816BookingService service) {
        this.service = service;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));
        routeBox.addItem("All Destinations");
        for (K2534816Route route : K2534816Route.values()) {
            routeBox.addItem(route.name());
        }

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.add(K2534816FormHelper.title("View All Bookings"));
        headerPanel.add(createSearchPanel());

        add(headerPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(createActionPanel(), BorderLayout.SOUTH);
        refresh();
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        panel.add(new JLabel("NIC/Passport:"));
        panel.add(nicField);
        panel.add(new JLabel("Destination:"));
        panel.add(routeBox);

        JButton searchButton = K2534816FormHelper.primaryButton("Search");
        searchButton.addActionListener(event -> searchBookings());
        panel.add(searchButton);

        JButton showAllButton = K2534816FormHelper.primaryButton("Show All");
        showAllButton.addActionListener(event -> resetSearch());
        panel.add(showAllButton);

        return panel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton createButton = K2534816FormHelper.primaryButton("Create");
        createButton.addActionListener(event -> openCreateDialog());
        panel.add(createButton);

        JButton updateButton = K2534816FormHelper.primaryButton("Update");
        updateButton.addActionListener(event -> openUpdateDialog());
        panel.add(updateButton);

        JButton deleteButton = K2534816FormHelper.primaryButton("Cancel");
        deleteButton.addActionListener(event -> cancelSelectedBooking());
        panel.add(deleteButton);

        JButton printButton = K2534816FormHelper.primaryButton("Print Ticket");
        printButton.addActionListener(event -> printSelectedTicket());
        panel.add(printButton);

        JButton refreshButton = K2534816FormHelper.primaryButton("Refresh");
        refreshButton.addActionListener(event -> refresh());
        panel.add(refreshButton);

        return panel;
    }

    private void openCreateDialog() {
        K2534816CreateBookingPanel panel = new K2534816CreateBookingPanel(service, new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        });
        showFormDialog("Create Booking", panel, 880, 560);
        refresh();
    }

    private void openUpdateDialog() {
        K2534816Booking booking = getSelectedBooking();
        if (booking == null) {
            return;
        }
        if (booking.getStatus() == K2534816BookingStatus.CANCELLED) {
            JOptionPane.showMessageDialog(this, "Cancelled bookings cannot be updated.", "Update Booking", JOptionPane.WARNING_MESSAGE);
            return;
        }
        K2534816UpdateBookingPanel panel = new K2534816UpdateBookingPanel(service, false, new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        });
        panel.loadBooking(booking);
        showFormDialog("Update Booking", panel, 720, 540);
        refresh();
    }

    private void showFormDialog(String title, JPanel panel, int width, int height) {
        java.awt.Window owner = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(owner, title, java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.add(panel);
        dialog.setSize(width, height);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void cancelSelectedBooking() {
        K2534816Booking booking = getSelectedBooking();
        if (booking == null) {
            return;
        }
        if (booking.getStatus() == K2534816BookingStatus.CANCELLED) {
            JOptionPane.showMessageDialog(this, "This booking is already cancelled.", "Cancel Booking", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int answer = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel booking " + booking.getBookingId() + "?",
                "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            service.cancelBooking(booking.getBookingId());
            refresh();
            JOptionPane.showMessageDialog(this, "Booking marked as cancelled.");
        }
    }

    private void printSelectedTicket() {
        K2534816Booking booking = getSelectedBooking();
        if (booking == null) {
            return;
        }
        JTextArea area = new JTextArea(booking.generateTicket(), 24, 48);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        content.add(new JScrollPane(area), BorderLayout.CENTER);

        JButton closeButton = K2534816FormHelper.primaryButton("Close");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);
        content.add(buttonPanel, BorderLayout.SOUTH);

        java.awt.Window owner = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(owner, "Ticket Preview", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        closeButton.addActionListener(event -> dialog.dispose());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.add(content);
        dialog.setSize(520, 620);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private K2534816Booking getSelectedBooking() {
        String bookingId = tablePanel.getSelectedBookingId();
        if (bookingId == null) {
            JOptionPane.showMessageDialog(this, "Please select a booking from the table first.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        java.util.Optional<K2534816Booking> result = service.findById(bookingId);
        if (!result.isPresent()) {
            JOptionPane.showMessageDialog(this, "Selected booking was not found.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            refresh();
            return null;
        }
        return result.get();
    }

    private void refresh() {
        tablePanel.showBookings(service.getAllBookings());
    }

    private void searchBookings() {
        K2534816Route route = routeBox.getSelectedIndex() == 0 ? null : K2534816Route.valueOf((String) routeBox.getSelectedItem());
        tablePanel.showBookings(service.search(nicField.getText(), route));
    }

    private void resetSearch() {
        nicField.setText("");
        routeBox.setSelectedIndex(0);
        refresh();
    }
}
