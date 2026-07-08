import java.util.List;

public interface K2534816BookingRepository {
    List<K2534816Booking> loadBookings();
    void saveBookings(List<K2534816Booking> bookings);
}
