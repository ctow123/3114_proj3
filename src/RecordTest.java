import student.TestCase;

public class RecordTest extends TestCase {
    /**
     * Setup variables for the test methods
     */
    public void testRecord() {
        Record record1 = new Record(1, 20);
        assertEquals(20.0, record1.getKey(),.1);
        assertEquals(1.0, record1.getID(),.1);
    }
}
