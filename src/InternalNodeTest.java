import student.TestCase;

/**
 * This tests the methods of the internalnode class
 * 
 * @author connor and wesley
 * @version 1.0 - using git so the versions are stored there
 *
 */
public class InternalNodeTest extends TestCase {

    private DNATreeOps dna;


    /**
     * sets up the initial conditions
     */
    public void setUp() {
        dna = new DNATreeOps();
        dna.insert("ACGT", 0);
        dna.insert("AAAA", 0);
        dna.insert("AA", 0);
        dna.insert("AAACCCGG", 0);

    }


    /**
     * 
     */
    public void testMethods() {
        assertEquals(2, dna.findLevel(dna.root, "ACGT".toCharArray(), 0));
        assertEquals(3, dna.findLevel(dna.root, "AA".toCharArray(), 0));
        assertEquals(4, dna.findLevel(dna.root, "AAAA".toCharArray(), 0));
        assertEquals(4, dna.findLevel(dna.root, "AAACCCGG".toCharArray(), 0));
        assertEquals(dna.root.getbyNum(0), dna.root.get('A'));
        assertEquals(dna.root.getbyNum(1), dna.root.get('C'));
        assertEquals(dna.root.getbyNum(2), dna.root.get('G'));
        assertEquals(dna.root.getbyNum(3), dna.root.get('T'));
        assertEquals(dna.root.getbyNum(4), dna.root.get('$'));
        dna.insert("TATA", 0);

    }
}
