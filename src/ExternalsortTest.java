import java.io.IOException;
import student.TestCase;

/**
 * tests the methods of Externalsort
 * 
 * @version 1.0 - using git so the versions are stored there
 * @author connor
 *
 */
public class ExternalsortTest extends TestCase {

    private MinHeapRecord minheap;


    /**
     * Setup variables for the test methods
     * 
     * @throws IOException
     */
    public void setUp() throws IOException {
        minheap = new MinHeapRecord(10);
        // Externalsort sortingobj = new Externalsort();
        Externalsort.main(new String[] { "testing" });

    }


    /**
     * test the external sort did sort something
     */
    public void testBasic() {
        MinHeapRecord minheap2 = Externalsort.getHeap();
        assertNotNull(minheap2);
        assertEquals(0, minheap2.size());
    }


    /**
     * testing mergecalc and merge done
     */
    public void testMergeCalc() {
        int[] marray = { 0, 5, 15, 600 };
        int[] mcopy = { 0, 5, 15, 600 };
        int[] mdone = { 5, 15, 600, 600 };
        assertEquals(5, Externalsort.calcMergeAmount(marray, mcopy, 0));
        assertEquals(10, Externalsort.calcMergeAmount(marray, mcopy, 1));
        assertEquals(512, Externalsort.calcMergeAmount(marray, mcopy, 2));

        // test mergeDone

        assertTrue(Externalsort.mergeDone(mdone, mcopy, 3));
        assertFalse(Externalsort.mergeDone(marray, mcopy, 3));

    }


    /**
     * tests basic sorting operations with the heap
     */
    public void testHeap() {
        int i = 0;
        while (i < 10) {
            minheap.specialInsert(new Record(i + 5, i + 5));
            i++;
        }
        Externalsort.reorderHeap(minheap);
        assertEquals(10, minheap.size());
        assertEquals(10, minheap.realsize());
        assertEquals(10, minheap.maxsize());
        assertEquals(10, minheap.getSpecialInsert());
        assertTrue(minheap.isFull());
    }

// public void testMin() throws IOException {
// Externalsort.initialHeapFill(heaparray);
// Externalsort.fillBuffer(0, inputbuf);
// inputbuf.getRecord();
// inputbuf.getRecord();
// inputbuf.insert(new Record(Integer.MIN_VALUE, Integer.MIN_VALUE));
// inputbuf.insert(new Record(Integer.MIN_VALUE, Integer.MIN_VALUE));
// Externalsort.buildRuns(inputbuf, outputbuf, heaparray);
// int[] marray = { 0, 6651, 8192 };
// int[] mcopy = { 0, 6651, 8192 };
// Externalsort.mergeRuns(inputbuf, outputbuf, heaparray, marray, mcopy, 1,
// "runs.bin", "tester.bin");
// assertEquals(0, inputbuf.getSize());
// // data.close();
//
// }
//
}
