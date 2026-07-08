public enum K2534816Route {
    JAFFNA("Colombo -> Jaffna", 1600.00, 2500.00),
    MATARA("Colombo -> Matara", 800.00, 1200.00),
    BADULLA("Colombo -> Badulla", 1000.00, 1800.00);

    private final String displayName;
    private final double standardFare;
    private final double luxuryFare;

    K2534816Route(String displayName, double standardFare, double luxuryFare) {
        this.displayName = displayName;
        this.standardFare = standardFare;
        this.luxuryFare = luxuryFare;
    }

    public double getFare(K2534816BusType busType) {
        return busType == K2534816BusType.LUXURY ? luxuryFare : standardFare;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
