import student.TestCase;

/**
 * tests the methods of record class
 * 
 * @version 1.0 - using git so the versions are stored there
 * @author connor
 *
 */
public class RecordTest extends TestCase {
    /**
     * Setup and simple tests
     */
    public void testRecord() {
        Record record1 = new Record(1, 20);
        assertEquals(20.0, record1.getKey(), .1);
        assertEquals(1.0, record1.getID(), .1);
    }
}
