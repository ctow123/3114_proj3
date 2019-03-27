import java.util.Arrays;
import student.TestCase;

/**
 * This tests the methods of the MinHeap class
 * 
 * @author connor
 * @version 1.0 - using git so the versions are stored there
 *
 */
public class MinHeapTest extends TestCase {
    private MinHeap mino;


    /**
     * Setup variables for the test methods
     */
    public void setUp() {
        mino = new MinHeap(7);
        //mino.insert(29);
        mino.insert(31);
        mino.insert(35);
        mino.insert(40);
        mino.insert(56);
        mino.insert(51);
        mino.insert(52);
    }


    /**
     * basic tests for the methods, most of them are empty so don't need testing
     */
    public void testMethods() {
 mino.specialInsert(14);
        mino.print();
        System.out.println(Arrays.toString(mino.mainHeap()));
        mino.remove();
        mino.print();
        mino.specialInsert(14);
        assertFalse(mino.specialInsert(14));
        assertFalse(mino.insert(80));
        mino.remove();
        mino.insert(52);
        mino.print();
        System.out.println(Arrays.toString(mino.mainHeap()));
    }
}
