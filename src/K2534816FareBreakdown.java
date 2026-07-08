public class K2534816FareBreakdown {
    private double baseFare;
    private double discount;
    private double bookingFee;
    private double tax;
    private double total;

    public K2534816FareBreakdown(double baseFare, double discount, double bookingFee, double tax, double total) {
        this.baseFare = baseFare;
        this.discount = discount;
        this.bookingFee = bookingFee;
        this.tax = tax;
        this.total = total;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public double getDiscount() {
        return discount;
    }

    public double getBookingFee() {
        return bookingFee;
    }

    public double getTax() {
        return tax;
    }

    public double getTotal() {
        return total;
    }
}
