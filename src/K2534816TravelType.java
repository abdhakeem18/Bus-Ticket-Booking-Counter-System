public enum K2534816TravelType {
    ONE_WAY("One-way"),
    RETURN("Return");

    private final String displayName;

    K2534816TravelType(String displayName) {
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
