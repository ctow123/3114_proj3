
public class Record {
    private long id;
    private double key;


    public Record(long idparam, double keyparam) {
        id = idparam;
        key = keyparam;
    }


    // getters
    public long getID() {
        return id;
    }


    public double getKey() {
        return key;
    }
}
