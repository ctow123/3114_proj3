import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import student.TestCase;

public class ExternalsortTest extends TestCase {
    private RandomAccessFile data;
    Externalsort sorter = new Externalsort();
    
    Buffer inputbuf = new Buffer(512);
    Buffer outputbuf = new Buffer(512);
    MinHeapRecord heaparray = new MinHeapRecord(4096);
    
    private MinHeapRecord minheap;
    /**
     * Setup variables for the test methods
     * @throws FileNotFoundException 
     */
    public void setUp() throws FileNotFoundException {
        minheap = new MinHeapRecord(10);
        data = new RandomAccessFile("sampleInput16test.bin", "r");
    }
    
    public void testBasic() {
        
    }
    
    /**
     * testing mergecalc and merge done
     */
    public void testMergeCalc() {
        int[] marray = {0,5,15,600};
        int[] mcopy = {0,5,15,600};
        int[] mdone = {5,15,600,600};
        assertEquals(5,Externalsort.calcMergeAmount(marray,mcopy,0));
        assertEquals(10,Externalsort.calcMergeAmount(marray,mcopy,1));
        assertEquals(512,Externalsort.calcMergeAmount(marray,mcopy,2));
        
        //test mergeDone
        
        assertTrue(Externalsort.mergeDone(mdone,mcopy,3));
        assertFalse(Externalsort.mergeDone(marray,mcopy,3));

    }
    
    public void testHeap() {
        int i =0;
        while(i < 10) {
            minheap.specialInsert(new Record(i+5,i+5));
            i++;
        }
        Externalsort.reorderHeap(minheap);
        assertEquals(10, minheap.size());
        assertEquals(10, minheap.realsize());
        assertEquals(10, minheap.maxsize());
        assertEquals(10, minheap.getSpecialInsert());
        assertTrue(minheap.isFull());
    }
    
    public void testMin() throws IOException {
        Externalsort.fillBuffer(0, inputbuf);
        
    }
}
