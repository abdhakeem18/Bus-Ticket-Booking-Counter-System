public enum K2534816BookingStatus {
    ACTIVE("Active"),
    CANCELLED("Cancelled");

    private final String displayName;

    K2534816BookingStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
