public class K2534816Customer extends K2534816Person {
    private String nicPassport;

    public K2534816Customer(String nicPassport, String name, String contactNumber) {
        super(name, contactNumber);
        this.nicPassport = nicPassport;
    }

    public String getNicPassport() {
        return nicPassport;
    }

    public void setNicPassport(String nicPassport) {
        this.nicPassport = nicPassport;
    }
}
