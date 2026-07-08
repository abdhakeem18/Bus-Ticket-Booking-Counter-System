import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class K2534816BookingService {
    private final K2534816BookingRepository repository;
    private final K2534816FareCalculator fareCalculator;
    private final List<K2534816Booking> bookings;

    public K2534816BookingService(K2534816BookingRepository repository, K2534816FareCalculator fareCalculator) {
        this.repository = repository;
        this.fareCalculator = fareCalculator;
        this.bookings = new ArrayList<>(repository.loadBookings());
    }

    public K2534816Booking createBooking(String nicPassport, String name, String contact, K2534816Route route,
                                          LocalDate travelDate, K2534816BusType busType, K2534816TravelType travelType) {
        validateCustomer(nicPassport, name, contact);
        validateDuplicate(null, nicPassport, route, travelDate);
        K2534816FareBreakdown fare = fareCalculator.calculate(route, busType, travelType);
        K2534816Booking booking = new K2534816Booking(nextBookingId(), new K2534816Customer(nicPassport, name, contact),
                route, travelDate, busType, travelType, nextSeatNumber(route, travelDate), LocalDate.now(), fare);
        bookings.add(booking);
        save();
        return booking;
    }

    public void updateBooking(String bookingId, String nicPassport, String name, String contact, K2534816Route route,
                              LocalDate travelDate, K2534816BusType busType, K2534816TravelType travelType) {
        validateCustomer(nicPassport, name, contact);
        K2534816Booking booking = findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found."));
        validateDuplicate(bookingId, nicPassport, route, travelDate);
        booking.setCustomer(new K2534816Customer(nicPassport, name, contact));
        booking.setRoute(route);
        booking.setTravelDate(travelDate);
        booking.setBusType(busType);
        booking.setTravelType(travelType);
        booking.setFareBreakdown(fareCalculator.calculate(route, busType, travelType));
        save();
    }

    public boolean cancelBooking(String bookingId) {
        Optional<K2534816Booking> result = findById(bookingId);
        if (result.isPresent()) {
            result.get().setStatus(K2534816BookingStatus.CANCELLED);
            save();
            return true;
        }
        return false;
    }

    public boolean deleteBooking(String bookingId) {
        return cancelBooking(bookingId);
    }

    public Optional<K2534816Booking> findById(String bookingId) {
        return bookings.stream().filter(booking -> booking.getBookingId().equalsIgnoreCase(bookingId.trim())).findFirst();
    }

    public List<K2534816Booking> search(String nicPassport, K2534816Route route) {
        return bookings.stream()
                .filter(booking -> nicPassport == null || nicPassport.trim().isEmpty()
                        || booking.getCustomer().getNicPassport().equalsIgnoreCase(nicPassport.trim()))
                .filter(booking -> route == null || booking.getRoute() == route)
                .sorted(Comparator.comparing(K2534816Booking::getBookingId))
                .collect(Collectors.toList());
    }

    public List<K2534816Booking> getAllBookings() {
        return new ArrayList<>(bookings);
    }

    private void validateCustomer(String nicPassport, String name, String contact) {
        K2534816InputValidator.requireText(nicPassport, "NIC/Passport");
        K2534816InputValidator.requireText(name, "Customer name");
        K2534816InputValidator.validateContact(contact);
    }

    private void validateDuplicate(String currentBookingId, String nicPassport, K2534816Route route, LocalDate travelDate) {
        boolean duplicate = bookings.stream().anyMatch(booking ->
                (currentBookingId == null || !booking.getBookingId().equalsIgnoreCase(currentBookingId))
                        && booking.getStatus() == K2534816BookingStatus.ACTIVE
                        && booking.getCustomer().getNicPassport().equalsIgnoreCase(nicPassport.trim())
                        && booking.getRoute() == route
                        && booking.getTravelDate().equals(travelDate));
        if (duplicate) {
            throw new IllegalArgumentException("This customer already has a seat for the same destination on the same day.");
        }
    }

    private String nextBookingId() {
        int next = bookings.stream()
                .map(K2534816Booking::getBookingId)
                .filter(id -> id.startsWith("K2534816-B"))
                .map(id -> id.substring("K2534816-B".length()))
                .filter(number -> number.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0) + 1;
        return String.format("K2534816-B%03d", next);
    }

    private int nextSeatNumber(K2534816Route route, LocalDate travelDate) {
        return bookings.stream()
                .filter(booking -> booking.getStatus() == K2534816BookingStatus.ACTIVE)
                .filter(booking -> booking.getRoute() == route && booking.getTravelDate().equals(travelDate))
                .mapToInt(K2534816Booking::getSeatNumber)
                .max()
                .orElse(0) + 1;
    }

    private void save() {
        repository.saveBookings(bookings);
    }
}
