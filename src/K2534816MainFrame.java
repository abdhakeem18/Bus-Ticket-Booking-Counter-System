import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class K2534816MainFrame extends JFrame {
    public K2534816MainFrame(K2534816BookingService service) {
        setTitle("K2534816 Bus Ticket Booking Counter System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 720);
        setMinimumSize(new Dimension(950, 650));
        setLocationRelativeTo(null);

        add(createTopArea(), BorderLayout.NORTH);
        add(new K2534816ViewBookingsPanel(service), BorderLayout.CENTER);
    }

    private JPanel createTopArea() {
        JPanel topArea = new JPanel(new BorderLayout());
        topArea.add(createHeader(), BorderLayout.NORTH);
        topArea.add(createMenuBar(), BorderLayout.SOUTH);
        return topArea;
    }

    private JLabel createHeader() {
        JLabel header = new JLabel("Bus Ticket Booking Counter System - K2534816", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(22, 78, 99));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setBorder(BorderFactory.createEmptyBorder(18, 10, 18, 10));
        return header;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(241, 245, 249));
        menuBar.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        exitItem.addActionListener(event -> dispose());
        menuBar.add(exitItem);
        return menuBar;
    }
}
