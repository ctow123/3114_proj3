import student.TestCase;

/**
 * Tests the simple methods of FlyweightNode
 * 
 * @author wesley and connor
 * @version 1.0 - using git so the versions are stored there
 *
 */
public class FlyweightNodeTest extends TestCase {

    private DNATreeOps dna;


    /**
     * Setup of our DNA tree
     */
    public void setUp() {
        dna = new DNATreeOps();
        dna.insert("ACGT", 0);
        dna.insert("AAAA", 0);
        dna.insert("AA", 0);
        dna.insert("AAACCCGG", 0);

    }


    /**
     * Tests the insert of a flyweightnode
     */
    public void testInsert() {
        assertEquals(2, dna.findLevel(dna.root, "ACGT".toCharArray(), 0));
        assertEquals(3, dna.findLevel(dna.root, "AA".toCharArray(), 0));
        assertEquals(4, dna.findLevel(dna.root, "AAAA".toCharArray(), 0));
        assertEquals(4, dna.findLevel(dna.root, "AAACCCGG".toCharArray(), 0));

    }
}
