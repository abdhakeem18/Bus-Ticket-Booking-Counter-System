import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class K2534816BookingFileRepository implements K2534816BookingRepository {
    private static final Path DATA_FILE = Paths.get("data", "bookings.csv");
    private static final String HEADER = "bookingId,nicPassport,customerName,contactNumber,destination,travelDate,busType,travelType,seatNumber,reservationDate,baseFare,discount,bookingFee,tax,total,status";

    @Override
    public List<K2534816Booking> loadBookings() {
        ensureFileExists();
        List<K2534816Booking> bookings = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(DATA_FILE);
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (!line.isEmpty()) {
                    bookings.add(fromCsv(line));
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to load bookings file", ex);
        }
        return bookings;
    }

    @Override
    public void saveBookings(List<K2534816Booking> bookings) {
        ensureFileExists();
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        for (K2534816Booking booking : bookings) {
            lines.add(toCsv(booking));
        }
        try {
            Files.write(DATA_FILE, lines);
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to save bookings file", ex);
        }
    }

    private void ensureFileExists() {
        try {
            Files.createDirectories(DATA_FILE.getParent());
            if (!Files.exists(DATA_FILE)) {
                List<String> lines = new ArrayList<>();
                lines.add(HEADER);
                Files.write(DATA_FILE, lines);
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to prepare data file", ex);
        }
    }

    private String toCsv(K2534816Booking booking) {
        K2534816FareBreakdown fare = booking.getFareBreakdown();
        return String.join(",",
                escape(booking.getBookingId()),
                escape(booking.getCustomer().getNicPassport()),
                escape(booking.getCustomer().getName()),
                escape(booking.getCustomer().getContactNumber()),
                booking.getRoute().name(),
                booking.getTravelDate().toString(),
                booking.getBusType().name(),
                booking.getTravelType().name(),
                String.valueOf(booking.getSeatNumber()),
                booking.getReservationDate().toString(),
                String.valueOf(fare.getBaseFare()),
                String.valueOf(fare.getDiscount()),
                String.valueOf(fare.getBookingFee()),
                String.valueOf(fare.getTax()),
                String.valueOf(fare.getTotal()),
                booking.getStatus().name()
        );
    }

    private K2534816Booking fromCsv(String line) {
        String[] value = line.split(",", -1);
        K2534816Customer customer = new K2534816Customer(unescape(value[1]), unescape(value[2]), unescape(value[3]));
        K2534816FareBreakdown fare = new K2534816FareBreakdown(
                Double.parseDouble(value[10]), Double.parseDouble(value[11]), Double.parseDouble(value[12]),
                Double.parseDouble(value[13]), Double.parseDouble(value[14]));
        K2534816BookingStatus status = value.length > 15 && !value[15].trim().isEmpty()
                ? K2534816BookingStatus.valueOf(value[15])
                : K2534816BookingStatus.ACTIVE;
        return new K2534816Booking(unescape(value[0]), customer, K2534816Route.valueOf(value[4]), LocalDate.parse(value[5]),
                K2534816BusType.valueOf(value[6]), K2534816TravelType.valueOf(value[7]), Integer.parseInt(value[8]),
                LocalDate.parse(value[9]), fare, status);
    }

    private String escape(String text) {
        return text == null ? "" : text.replace(",", " ").trim();
    }

    private String unescape(String text) {
        return text == null ? "" : text.trim();
    }
}
