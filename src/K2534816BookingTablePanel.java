import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class K2534816BookingTablePanel extends JPanel {
    private final DefaultTableModel model;
    private final JTable table;

    public K2534816BookingTablePanel() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new String[]{"Booking ID", "NIC/Passport", "Name", "Destination", "Date", "Bus", "Travel", "Seat", "Total", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void showBookings(List<K2534816Booking> bookings) {
        model.setRowCount(0);
        for (K2534816Booking booking : bookings) {
            model.addRow(new Object[]{
                    booking.getBookingId(),
                    booking.getCustomer().getNicPassport(),
                    booking.getCustomer().getName(),
                    booking.getRoute().getDisplayName(),
                    booking.getTravelDate(),
                    booking.getBusType().getDisplayName(),
                    booking.getTravelType().getDisplayName(),
                    booking.getSeatNumber(),
                    String.format("LKR %.2f", booking.getFareBreakdown().getTotal()),
                    booking.getStatus().getDisplayName()
            });
        }
    }

    public String getSelectedBookingId() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            return null;
        }
        return String.valueOf(model.getValueAt(table.convertRowIndexToModel(selectedRow), 0));
    }
}
