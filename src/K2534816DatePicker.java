import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
// import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.YearMonth;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class K2534816DatePicker extends JPanel {
    private final JTextField dateField = new JTextField();
    private YearMonth visibleMonth = YearMonth.from(LocalDate.now());

    public K2534816DatePicker() {
        setLayout(new BorderLayout(6, 0));
        dateField.setEditable(false);
        dateField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dateField.setPreferredSize(new Dimension(260, 30));
        setDate(LocalDate.now().plusDays(1));

        JButton calendarButton = K2534816FormHelper.primaryButton("Select Date");
        calendarButton.addActionListener(event -> showCalendarDialog());

        add(dateField, BorderLayout.CENTER);
        add(calendarButton, BorderLayout.EAST);
    }

    public String getDateText() {
        return dateField.getText();
    }

    public void setDate(LocalDate date) {
        dateField.setText(date.toString());
        visibleMonth = YearMonth.from(date);
    }

    private void showCalendarDialog() {
        java.awt.Window owner = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(owner, "Select Travel Date", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(380, 360);
        dialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout(8, 8));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        JLabel monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JPanel calendarGrid = new JPanel();

        JButton previousButton = K2534816FormHelper.primaryButton("<");
        JButton nextButton = K2534816FormHelper.primaryButton(">");
        JPanel header = new JPanel(new BorderLayout(8, 0));
        header.add(previousButton, BorderLayout.WEST);
        header.add(monthLabel, BorderLayout.CENTER);
        header.add(nextButton, BorderLayout.EAST);

        Runnable refreshCalendar = new Runnable() {
            @Override
            public void run() {
                buildCalendarGrid(calendarGrid, monthLabel, dialog);
            }
        };

        previousButton.addActionListener(event -> {
            visibleMonth = visibleMonth.minusMonths(1);
            refreshCalendar.run();
        });
        nextButton.addActionListener(event -> {
            visibleMonth = visibleMonth.plusMonths(1);
            refreshCalendar.run();
        });

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(calendarGrid, BorderLayout.CENTER);
        dialog.add(mainPanel);
        refreshCalendar.run();
        dialog.setVisible(true);
    }

    private void buildCalendarGrid(JPanel calendarGrid, JLabel monthLabel, JDialog dialog) {
        calendarGrid.removeAll();
        calendarGrid.setLayout(new GridLayout(0, 7, 4, 4));
        monthLabel.setText(visibleMonth.getMonth() + " " + visibleMonth.getYear());

        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (String day : days) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            calendarGrid.add(label);
        }

        LocalDate firstDay = visibleMonth.atDay(1);
        int blankDays = firstDay.getDayOfWeek().getValue() - 1;
        for (int i = 0; i < blankDays; i++) {
            calendarGrid.add(new JLabel(""));
        }

        LocalDate today = LocalDate.now();
        for (int day = 1; day <= visibleMonth.lengthOfMonth(); day++) {
            LocalDate date = visibleMonth.atDay(day);
            JButton button = new JButton(String.valueOf(day));
            button.setFocusPainted(false);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            if (date.isBefore(today)) {
                button.setEnabled(false);
            }
            if (date.equals(today)) {
                button.setBackground(new Color(186, 230, 253));
            }
            button.addActionListener(event -> {
                setDate(date);
                dialog.dispose();
            });
            calendarGrid.add(button);
        }

        calendarGrid.revalidate();
        calendarGrid.repaint();
    }
}
