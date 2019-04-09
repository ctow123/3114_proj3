import java.io.IOException;
import student.TestCase;

/**
 * tests the methods of the buffer class
 * 
 * @version 1.0 - using git so the versions are stored there
 * @author connor
 *
 */
public class BufferTest extends TestCase {
    private Buffer buff;


    /**
     * Setup variables for the test methods
     */
    public void setUp() {
        buff = new Buffer(10);
    }


    /**
     * tests fields are set up correctly
     */
    public void testFields() {
        assertEquals(0, buff.getSize());
        assertTrue(buff.isEmpty());
        assertFalse(buff.isFull());

    }


    /**
     * tests the methods insert, getRecord, getRecordFront, Peek
     */
    public void testMethods() {
        // test nulls
        assertNull(buff.getRecord());
        assertNull(buff.getRecordFront());
        assertNull(buff.peek());
        // test insert
        int i = 0;
        while (i < 10) {
            buff.insert(new Record(i, i));
            i++;
        }
        assertEquals(10, buff.getSize());
        assertFalse(buff.isEmpty());
        assertTrue(buff.isFull());
        // test Peek
        assertEquals(9, buff.peek().getKey(), .1);
        assertEquals(10, buff.getSize());
        // test getRecord
        assertEquals(buff.peek(), buff.getRecord());
        assertEquals(9, buff.getSize());
        // test getRecordFront
        assertEquals(0, buff.getRecordFront().getKey(), .1);
        assertEquals(8, buff.getSize());
        assertEquals(1, buff.getRecordFront().getKey(), .1);
        assertEquals(2, buff.getRecordFront().getKey(), .1);
        assertEquals(6, buff.getSize());
    }


    /**
     * tests the dump function
     */
    public void testDump() {

        int i = 0;
        while (i < 10) {
            buff.insert(new Record(i, i));
            i++;
        }
        // pre dump
        assertNotNull(buff.peek());
        assertEquals(10, buff.getSize());
        assertFalse(buff.isEmpty());
        assertTrue(buff.isFull());
        // post dump
        try {
            buff.dumpBuffer("test.txt", "test.bin");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        assertNull(buff.peek());
        assertEquals(0, buff.getSize());
        assertTrue(buff.isEmpty());
        assertFalse(buff.isFull());
    }

}
