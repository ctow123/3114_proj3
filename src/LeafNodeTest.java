import student.TestCase;

/**
 * This tests the methods of the LeafNode class
 * 
 * @author connor and wesley
 * @version 1.0 - using git so the versions are stored there
 *
 */
public class LeafNodeTest extends TestCase {

    private DNATreeOps dna;


    /**
     * Setup variables for the test methods
     */
    public void setUp() {
        dna = new DNATreeOps();
        dna.insert("ACGT", 0);
        dna.insert("AAAA", 0);
        dna.insert("AA", 0);
        dna.insert("AAACCCGG", 0);

    }


    /**
     * tests redundant methods
     */
    public void testInsert() {
        assertEquals(2, dna.findLevel(dna.root, "ACGT".toCharArray(), 0));
        assertEquals(3, dna.findLevel(dna.root, "AA".toCharArray(), 0));
        assertEquals(4, dna.findLevel(dna.root, "AAAA".toCharArray(), 0));
        assertEquals(4, dna.findLevel(dna.root, "AAACCCGG".toCharArray(), 0));
        // assertNegative
        // assertNull(node1.left());
        // assertNull(node2.right());

    }


    /**
     * test the prints stats method
     */
    public void testPrintStats() {
        LeafNode leaf1 = new LeafNode("CCCC");
        leaf1.printNewStats(0);
        LeafNode leaf2 = new LeafNode("CCCCGGA");
        leaf2.printNewStats(0);
        LeafNode leaf3 = new LeafNode("TTTATT");
        leaf3.printNewStats(0);
        assertNotNull(leaf3);
    }
}
