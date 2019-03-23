import student.TestCase;

/**
 * This tests the methods of the BaseNode class
 * 
 * @author connor and wesley
 * @version 1.0 - using git so the versions are stored there
 *
 */
public class BaseNodeTest extends TestCase {

    private BaseNode base;


    /**
     * Setup variables for the test methods
     */
    public void setUp() {
        base = new BaseNode();

    }


    /**
     * basic tests for the methods, most of them are empty so don't need testing
     */
    public void testMethods() {
        assertFalse(base.isFly());
        assertFalse(base.isInternal());
        assertFalse(base.isLeaf());
        assertNull(base.getbyNum(0));
        assertNull(base.getSeqname());
        assertNull(base.get('A'));
        assertEquals(0, base.printCount(1));

    }
}
