public class K2534816FareCalculator {
    private static final double BOOKING_FEE_RATE = 0.035;
    private static final double TAX_RATE = 0.015;
    private static final double RETURN_DISCOUNT_RATE = 0.05;

    public K2534816FareBreakdown calculate(K2534816Route route, K2534816BusType busType, K2534816TravelType travelType) {
        double baseFare = route.getFare(busType);
        double chargedBaseFare = baseFare;
        double discount = 0.00;

        if (travelType == K2534816TravelType.RETURN) {
            chargedBaseFare = baseFare * 2;
            discount = chargedBaseFare * RETURN_DISCOUNT_RATE;
            chargedBaseFare -= discount;
        }

        double bookingFee = chargedBaseFare * BOOKING_FEE_RATE;
        double tax = (chargedBaseFare + bookingFee) * TAX_RATE;
        double total = chargedBaseFare + bookingFee + tax;

        return new K2534816FareBreakdown(chargedBaseFare, discount, bookingFee, tax, total);
    }
}
