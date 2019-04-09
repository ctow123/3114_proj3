
/**
 * this is a record object with an id and key to be used in sorting
 * 
 * @version 1.0 - using git so the versions are stored there
 * @author connor
 *
 */
public class Record {
    private long id;
    private double key;


    /**
     * default constructor
     * 
     * @param idparam
     *            id of record
     * @param keyparam
     *            key of the record
     */
    public Record(long idparam, double keyparam) {
        id = idparam;
        key = keyparam;
    }


    // getters

    /**
     * 
     * @return the id
     */
    public long getID() {
        return id;
    }


    /**
     * 
     * @return the record key
     */
    public double getKey() {
        return key;
    }
}
