public enum K2534816BusType {
    STANDARD("Standard"),
    LUXURY("Luxury");

    private final String displayName;

    K2534816BusType(String displayName) {
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
