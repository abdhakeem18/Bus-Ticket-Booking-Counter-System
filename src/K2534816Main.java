import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class K2534816Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Default look and feel is acceptable if the system theme is unavailable.
            }
            K2534816BookingService service = new K2534816BookingService(
                    new K2534816BookingFileRepository(), new K2534816FareCalculator());
            new K2534816MainFrame(service).setVisible(true);
        });
    }
}
