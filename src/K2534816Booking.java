import java.time.LocalDate;

public class K2534816Booking implements K2534816TicketPrintable {
    private String bookingId;
    private K2534816Customer customer;
    private K2534816Route route;
    private LocalDate travelDate;
    private K2534816BusType busType;
    private K2534816TravelType travelType;
    private int seatNumber;
    private LocalDate reservationDate;
    private K2534816FareBreakdown fareBreakdown;
    private K2534816BookingStatus status;

    public K2534816Booking(String bookingId, K2534816Customer customer, K2534816Route route, LocalDate travelDate,
                           K2534816BusType busType, K2534816TravelType travelType, int seatNumber,
                           LocalDate reservationDate, K2534816FareBreakdown fareBreakdown) {
        this(bookingId, customer, route, travelDate, busType, travelType, seatNumber, reservationDate, fareBreakdown, K2534816BookingStatus.ACTIVE);
    }

    public K2534816Booking(String bookingId, K2534816Customer customer, K2534816Route route, LocalDate travelDate,
                           K2534816BusType busType, K2534816TravelType travelType, int seatNumber,
                           LocalDate reservationDate, K2534816FareBreakdown fareBreakdown, K2534816BookingStatus status) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.route = route;
        this.travelDate = travelDate;
        this.busType = busType;
        this.travelType = travelType;
        this.seatNumber = seatNumber;
        this.reservationDate = reservationDate;
        this.fareBreakdown = fareBreakdown;
        this.status = status;
    }

    public String getBookingId() { return bookingId; }
    public K2534816Customer getCustomer() { return customer; }
    public K2534816Route getRoute() { return route; }
    public LocalDate getTravelDate() { return travelDate; }
    public K2534816BusType getBusType() { return busType; }
    public K2534816TravelType getTravelType() { return travelType; }
    public int getSeatNumber() { return seatNumber; }
    public LocalDate getReservationDate() { return reservationDate; }
    public K2534816FareBreakdown getFareBreakdown() { return fareBreakdown; }
    public K2534816BookingStatus getStatus() { return status; }

    public void setCustomer(K2534816Customer customer) { this.customer = customer; }
    public void setRoute(K2534816Route route) { this.route = route; }
    public void setTravelDate(LocalDate travelDate) { this.travelDate = travelDate; }
    public void setBusType(K2534816BusType busType) { this.busType = busType; }
    public void setTravelType(K2534816TravelType travelType) { this.travelType = travelType; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
    public void setFareBreakdown(K2534816FareBreakdown fareBreakdown) { this.fareBreakdown = fareBreakdown; }
    public void setStatus(K2534816BookingStatus status) { this.status = status; }

    @Override
    public String generateTicket() {
        return K2534816TicketPrinter.generate(this);
    }
}
