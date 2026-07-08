import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class K2534816SearchBookingPanel extends JPanel {
    private final K2534816BookingService service;
    private final JTextField nicField = new JTextField(18);
    private final JComboBox<String> routeBox = new JComboBox<>();
    private final K2534816BookingTablePanel tablePanel = new K2534816BookingTablePanel();

    public K2534816SearchBookingPanel(K2534816BookingService service) {
        this.service = service;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));
        routeBox.addItem("All Destinations");
        for (K2534816Route route : K2534816Route.values()) {
            routeBox.addItem(route.name());
        }
        add(K2534816FormHelper.title("Search Booking"), BorderLayout.NORTH);
        add(createSearchPanel(), BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(nicField);
        panel.add(routeBox);
        JButton searchButton = K2534816FormHelper.primaryButton("Search");
        searchButton.addActionListener(event -> search());
        panel.add(searchButton);
        return panel;
    }

    private void search() {
        K2534816Route route = routeBox.getSelectedIndex() == 0 ? null : K2534816Route.valueOf((String) routeBox.getSelectedItem());
        tablePanel.showBookings(service.search(nicField.getText(), route));
    }
}
