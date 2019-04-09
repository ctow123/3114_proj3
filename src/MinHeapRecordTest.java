import student.TestCase;

/**
 * tests the methods of Minheap class
 * 
 * @version 1.0 - using git so the versions are stored there
 * @author connor
 *
 */
public class MinHeapRecordTest extends TestCase {
    private MinHeapRecord minheap;
    private MinHeapRecord minheap4096;
    private Record record1 = new Record(1, 10);
    private Record record2 = new Record(2, 20);
    private Record record3 = new Record(3, 30);
    private Record record4 = new Record(4, 40);
    private Record record5 = new Record(5, 50);
    private Record record6 = new Record(6, 60);
    private Record record7 = new Record(7, 70);
    private Record record8 = new Record(8, 80);
    private Record record9 = new Record(9, 90);
    private Record record10 = new Record(10, 100);
    private Record record11 = new Record(11, 110);


    /**
     * Setup variables for the test methods
     */
    public void setUp() {
        minheap = new MinHeapRecord(10);
        minheap4096 = new MinHeapRecord(4096);
    }


    /**
     * tests the setup fields
     */
    public void testFields() {
        assertEquals(0, minheap.size());
        assertEquals(0, minheap.realsize());
        assertEquals(10, minheap.maxsize());
        assertEquals(10, minheap.getSpecialInsert());
        assertFalse(minheap.isFull());

    }


    /**
     * leaf,child,parent, insert, remove, peek
     */
    public void testBasicHeapOps() {
        // insert
        assertTrue(minheap.insert(record1));
        assertTrue(minheap.insert(record2));
        assertTrue(minheap.insert(record6));
        assertTrue(minheap.insert(record4));
        assertTrue(minheap.insert(record5));
        assertTrue(minheap.insert(record3));
        assertTrue(minheap.insert(record7));
        assertTrue(minheap.insert(record8));
        assertTrue(minheap.insert(record9));
        assertTrue(minheap.insert(record10));
        assertFalse(minheap.insert(record11));
        assertTrue(minheap.isFull());
        // structure and peek
        assertEquals(10, minheap.peek().getKey(), .01);
        assertEquals(20, minheap.mainHeap()[minheap.leftChild(1)].getKey(),
            .01);
        assertEquals(30, minheap.mainHeap()[minheap.rightChild(1)].getKey(),
            .01);
        // minheap.print();
        assertEquals(2, minheap.parent(5));
        assertEquals(2, minheap.parent(4));
        assertEquals(4, minheap.leftChild(2));
        assertEquals(5, minheap.rightChild(2));
        // remove
        assertEquals(10, minheap.remove().getKey(), .1);
        assertEquals(9, minheap.size());
        assertEquals(9, minheap.realsize());
        assertEquals(20, minheap.remove().getKey(), .1);
        minheap.remove();

    }


    /**
     * specialInsert, removeSpecial, insertReorder
     */
    public void testRunOps() {
        // specialinsert
        assertNull(minheap.peek());
        int i = 0;
        while (i < 5) {
            minheap.specialInsert(new Record(i, i));
            i++;
        }
        assertEquals(0, minheap.size());
        assertEquals(5, minheap.realsize());
        assertEquals(10, minheap.maxsize());
        assertEquals(5, minheap.getSpecialInsert());
        assertFalse(minheap.isFull());
        minheap.insert(record1);
        assertEquals(1, minheap.size());
        assertEquals(6, minheap.realsize());
        assertEquals(10, minheap.maxsize());
        assertEquals(5, minheap.getSpecialInsert());
        assertFalse(minheap.isFull());
        i = 0;
        while (i < 4) {
            minheap.specialInsert(new Record(i + 5, i + 5));
            i++;
        }
        assertFalse(minheap.specialInsert(new Record(90, 90)));
        // removespecial
        assertEquals(0, minheap.removeSpecial(10).getKey(), .1);
        assertEquals(1, minheap.size());
        assertEquals(9, minheap.realsize());
        assertEquals(10, minheap.maxsize());
        assertEquals(1, minheap.getSpecialInsert());
        assertFalse(minheap.isFull());

        // insertReorder
        minheap.insertReorder(minheap.remove());
        assertEquals(1, minheap.size());
        assertEquals(9, minheap.realsize());
    }


    /**
     * insertRegular, isRunEmpty, findEmptySpot, runsMin, runCurrPos
     */
    public void testMergeOps() {
        // insertRegular

        int i = 0;
        while (i < 5) {
            minheap4096.insertRegular(new Record(i + 1, i + 1), 0);
            i++;
        }
        assertTrue(minheap4096.insertRegular(record1, 1));
        minheap4096.insertRegular(record2, 2);
        minheap4096.insertRegular(record3, 3);
        minheap4096.insertRegular(record4, 4);
        assertEquals(9, minheap4096.size());
        assertFalse(minheap4096.isRunEmpty(0));
        assertFalse(minheap4096.isRunEmpty(1));
        assertFalse(minheap4096.isRunEmpty(2));
        assertFalse(minheap4096.isRunEmpty(3));
        assertFalse(minheap4096.isRunEmpty(4));
        assertTrue(minheap4096.isRunEmpty(5));
        assertTrue(minheap4096.isRunEmpty(6));
        assertTrue(minheap4096.isRunEmpty(7));

        // find empty spot & runCurrPos
        assertEquals(6, minheap4096.findEmptySpot(1));
        assertEquals(514, minheap4096.findEmptySpot(513));
        assertEquals(1026, minheap4096.findEmptySpot(1025));
        assertEquals(2561, minheap4096.findEmptySpot(2561));

        assertEquals(1, minheap4096.runCurrPos(0));
        assertEquals(513, minheap4096.runCurrPos(1));
        assertEquals(1025, minheap4096.runCurrPos(2));

        // runsMin

        assertEquals(1, minheap4096.runsMin(8).getKey(), .1);
    }


    /**
     * tests merge operations more heavily
     */
    public void testMergeOps2() {
        int i = 0;
        while (i < 512) {
            minheap4096.insertRegular(new Record(i + 1, i + 1), 0);
            i++;
        }
        minheap4096.removeSpecial(1);
        assertEquals(2, minheap4096.runCurrPos(0));

        // assertEquals(513,minheap4096.runCurrPos(1));
        // assertEquals(1025,minheap4096.runCurrPos(2));

        // tests minifyheap
        assertTrue(minheap.insert(record1));
        assertTrue(minheap.insert(record2));
        assertTrue(minheap.insert(record3));
        minheap.remove();
        assertTrue(minheap.insert(record4));
        minheap.insertReorder(minheap.remove());
        assertEquals(3, minheap.size());
    }
}
