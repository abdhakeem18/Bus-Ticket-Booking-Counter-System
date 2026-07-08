public class K2534816TicketPrinter {
    public static String generate(K2534816Booking booking) {
        K2534816FareBreakdown fare = booking.getFareBreakdown();
        return "BUS TICKET BOOKING COUNTER SYSTEM\n"
                + "---------------------------------\n"
                + "Booking ID      : " + booking.getBookingId() + "\n"
                + "Reservation Date: " + booking.getReservationDate() + "\n"
                + "NIC/Passport    : " + booking.getCustomer().getNicPassport() + "\n"
                + "Customer Name   : " + booking.getCustomer().getName() + "\n"
                + "Contact Number  : " + booking.getCustomer().getContactNumber() + "\n"
                + "Destination     : " + booking.getRoute().getDisplayName() + "\n"
                + "Travel Date     : " + booking.getTravelDate() + "\n"
                + "Bus Type        : " + booking.getBusType().getDisplayName() + "\n"
                + "Travel Type     : " + booking.getTravelType().getDisplayName() + "\n"
                + "Seat Number     : " + booking.getSeatNumber() + "\n"
                + "Status          : " + booking.getStatus().getDisplayName() + "\n"
                + "---------------------------------\n"
                + String.format("Base Fare       : LKR %.2f\n", fare.getBaseFare())
                + String.format("Return Discount : LKR %.2f\n", fare.getDiscount())
                + String.format("Booking Fee     : LKR %.2f\n", fare.getBookingFee())
                + String.format("Tax/VAT         : LKR %.2f\n", fare.getTax())
                + String.format("Total           : LKR %.2f\n", fare.getTotal());
    }
}
