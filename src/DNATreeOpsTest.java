import java.io.*;
import student.TestCase;

/**
 * This tests the methods in DNATreeOps
 * 
 * @author Connor (ctowler3) and Wesley (miwwes)
 * @version 1.0 - using git so the versions are stored there
 *
 */
public class DNATreeOpsTest extends TestCase {

    private DNATreeOps dna;
    private DNATreeOps dna2;


    /**
     * Setup variables for the test methods
     */
    public void setUp() {
        dna = new DNATreeOps();
        dna.insert("ACGT", 0);
        dna.insert("AAAA", 0);
        dna.insert("AA", 0);
        dna.insert("AAACCCGG", 0);
        dna2 = new DNATreeOps();

    }


    /**
     * Tests the possibilities with insert
     */
    public void testInsert() {
        // basic inserts
        assertEquals(2, dna.findLevel(dna.root, "ACGT".toCharArray(), 0));
        assertEquals(3, dna.findLevel(dna.root, "AA".toCharArray(), 0));
        assertEquals(4, dna.findLevel(dna.root, "AAAA".toCharArray(), 0));
        assertEquals(4, dna.findLevel(dna.root, "AAACCCGG".toCharArray(), 0));

        // inserts with ACGT with empty dna tree then AAAA

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        dna2.root.printNew(0);
        String expectedOutput = "E\n";
        // Notice the \n for new line.
        assertEquals(expectedOutput, outContent.toString());
        assertTrue(dna2.root.isFly());
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        dna2.insert("ACGT", 0);
        dna2.root.printNew(0);
        expectedOutput = "ACGT\n";
        assertEquals(expectedOutput, outContent.toString());
        assertTrue(dna2.root.isLeaf());
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        dna2.insert("AAAA", 0);
        dna2.root.printNew(0);
        expectedOutput = "I\n  I\n    AAAA\n    ACGT\n    E\n   "
            + " E\n    E\n  E\n  E\n  E\n  E\n";
        assertEquals(expectedOutput, outContent.toString());
        assertTrue(dna2.root.isInternal());
        assertTrue(dna2.root.get('A').isInternal());
        assertTrue(dna2.root.get('C').isFly());

        dna.insert("AAAAA", 0);
        dna.insert("AAA", 0);
        assertEquals(2, dna.findLevel(dna.root, "ACGT".toCharArray(), 0));
        assertEquals(3, dna.findLevel(dna.root, "AA".toCharArray(), 0));
        assertEquals(5, dna.findLevel(dna.root, "AAAA".toCharArray(), 0));
        assertEquals(4, dna.findLevel(dna.root, "AAACCCGG".toCharArray(), 0));
        assertEquals(5, dna.findLevel(dna.root, "AAAAA".toCharArray(), 0));
        assertEquals(4, dna.findLevel(dna.root, "AAA".toCharArray(), 0));
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        dna.root.printNew(0);
        expectedOutput = "I\n  I\n    I\n      I\n        I\n     "
            + "     AAAAA\n          E\n          E\n          E\n    "
            + "      AAAA\n        AAACCCGG\n        E\n        E\n     "
            + "   AAA\n      E\n      E\n      E\n      AA\n    ACGT\n  "
            + "  E\n    E\n    E\n  E\n  E\n  E\n  E\n";
        assertEquals(expectedOutput, outContent.toString());

        // insert failing
        assertFalse(dna.insert("AAA", 0));
        assertFalse(dna.insert("ACGT", 0));
    }


    /**
     * Test edge cases of insert
     */
    public void testInsertHard() {
        dna2.root.printNew(0);
        assertTrue(dna2.insert("TTT", 0));
        dna2.root.printNew(0);
        dna2.insert("TTTT", 0);
        dna2.insert("TTGA", 0);
        dna2.root.printNew(0);

    }


    /**
     * Testing search
     */
    public void testSearch() {
        dna.insert("ACTGGGAA", 0);
        dna.insert("ACCTT", 0);
        dna.insert("ACTTA", 0);
        dna.insert("TATA", 0);
        dna.insert("TCG", 0);
        assertTrue(dna.search(dna.root, "ACGT", 0));
        assertTrue(dna.search(dna.root, "AAAA", 0));
        assertTrue(dna.search(dna.root, "AA", 0));
        assertTrue(dna.search(dna.root, "AAACCCGG", 0));
        assertTrue(dna.search(dna.root, "ACGT", 0));
        assertTrue(dna.search(dna.root, "ACCTT", 0));
        assertTrue(dna.search(dna.root, "ACTTA", 0));
        assertTrue(dna.search(dna.root, "TATA", 0));
        assertTrue(dna.search(dna.root, "TCG", 0));
        // removing
        dna.remove("AAACCCGG", 0);
        assertFalse(dna.search(dna.root, "AAACCCGG", 0));
        dna.remove("TATA", 0);
        assertFalse(dna.search(dna.root, "TATA", 0));
        assertTrue(dna.search(dna.root, "TCG", 0));
        dna.remove("ACTTA", 0);
        assertFalse(dna.search(dna.root, "ACTTA", 0));
        // not in tree
        assertFalse(dna.search(dna.root, "", 0));
        assertFalse(dna.search(dna.root, "GTGT", 0));
        assertFalse(dna.search(dna.root, "AAACCCGGT", 0));
        assertFalse(dna.search(dna.root, "CCCC", 0));

    }


    /**
     * Searching for multiple nodes
     */
    public void testSearchMulti() {
        assertNotNull(dna.searchMulti(dna.root, "AC", 0));
        assertNotNull(dna.searchMulti(dna.root, "AAAAA", 0));
        assertTrue(dna.root.get('A').get('G').isFly());
        assertNotNull(dna.searchMulti(dna.root, "AG", 0));
        assertNotNull(dna.searchMulti(dna.root, "AGGA", 0));
        // BaseNode temp =
        assertTrue((dna.searchMulti(dna.root, "AC", 0)).isLeaf());
        assertEquals(dna.searchMulti(dna.root, "AC", 0), dna.root.get('A').get(
            'C'));
        assertEquals(dna.searchMulti(dna.root, "AAA", 0), dna.root.get('A').get(
            'A').get('A'));

    }


    /**
     * Tests the edge cases and normal cases of remove
     */
    public void testRemove() {
        // complex remove collasping 2 internals
        dna.insert("AAA", 0);
        dna.remove("AAACCCGG", 0);
        assertEquals(4, dna.findLevel(dna.root, "AAAA".toCharArray(), 0));
        assertEquals(4, dna.root.countLeaf());
        assertEquals(21, dna.root.printCount(0));
        dna.remove("AA", 0);
        assertEquals(4, dna.findLevel(dna.root, "AAAA".toCharArray(), 0));
        assertEquals(3, dna.root.countLeaf());
        assertEquals(21, dna.root.printCount(0));
        dna.remove("AAAA", 0);
        assertEquals(2, dna.findLevel(dna.root, "AAA".toCharArray(), 0));
        assertEquals(2, dna.root.countLeaf());
        assertEquals(11, dna.root.printCount(0));
        // making root a leaf
        dna.remove("AAA", 0);
        assertEquals(0, dna.findLevel(dna.root, "ACGT".toCharArray(), 0));
        assertEquals(1, dna.root.countLeaf());
        assertEquals(1, dna.root.printCount(0));
        // making root a fly
        dna.remove("ACGT", 0);
        assertEquals(0, dna.root.countLeaf());
        assertEquals(1, dna.root.printCount(0));
        assertTrue(dna.root.isFly());
        // reseting
        dna.insert("AAACCCGG", 0);
        dna.insert("AAA", 0);
        dna.insert("ACGT", 0);
        dna.insert("AAAA", 0);
        dna.insert("AA", 0);

        // simple remove collasping 1 internal
        dna.remove("AAA", 0);
        dna.remove("AAAA", 0);

        assertEquals(3, dna.findLevel(dna.root, "AAACCCGG".toCharArray(), 0));
        assertEquals(3, dna.root.countLeaf());
        assertEquals(16, dna.root.printCount(0));

        // reseting
        dna.insert("AAA", 0);
        dna.insert("AAAA", 0);

        // changing leaf to fly 1
        dna.insert("AAA", 0);
        dna.remove("AAACCCGG", 0);
        assertEquals(4, dna.findLevel(dna.root, "AAAA".toCharArray(), 0));
        assertEquals(4, dna.root.countLeaf());
        assertEquals(21, dna.root.printCount(0));

        // reseting
        dna.insert("AAACCCGG", 0);

        // changing leaf to fly 2
        dna.remove("ACGT", 0);
        assertEquals(4, dna.findLevel(dna.root, "AAAA".toCharArray(), 0));
        assertEquals(4, dna.root.countLeaf());
        assertEquals(21, dna.root.printCount(0));

        // reseting
        dna.insert("ACGT", 0);

        // removing exact sequences in the $ node
        dna.remove("AAAA", 0);
        dna.remove("AAA", 0);
        assertEquals(3, dna.findLevel(dna.root, "AAACCCGG".toCharArray(), 0));
        assertEquals(3, dna.root.countLeaf());
        assertEquals(16, dna.root.printCount(0));

        // removing root
        assertTrue(dna2.insert("ACT", 0));
        dna2.remove("ACT", 0);
        assertFalse(dna2.search(dna2.root, "ACT", 0));
    }


    /**
     * Test more edge cases of remove
     */
    public void testRemove2() {
        dna.insert("ACTGGGAA", 0);
        // dna.remove("ACGT",0);
        dna.insert("ACCTT", 0);
        dna.insert("ACTTA", 0);
        dna.insert("TATA", 0);
        dna.insert("TCG", 0);
        // dna.root.printNew(0);
        assertTrue(dna.search(dna.root, "AAAA", 0));
        dna.remove("ACCTT", 0);
        // dna.root.printNew(0);
        dna.remove("ACTTA", 0);
        // dna.root.printNew(0);

    }


    /**
     * Test more edge cases of remvoe
     */
    public void testRemove3() {
        dna2.insert("AA", 0);
        dna2.insert("AAAA", 0);
        assertTrue(dna2.insert("AAAAC", 0));
        dna2.remove("AA", 0);
        dna2.remove("AAAAC", 0);
        // dna2.root.printNew(0);
    }


    /**
     * Tests the depth of the nodes in the tree
     */
    public void testFindLevel() {
        dna.insert("ACTGGGAA", 0);
        // dna.remove("ACGT",0);
        dna.insert("ACCTT", 0);
        dna.insert("ACTTA", 0);
        dna.insert("TATA", 0);
        dna.insert("TCG", 0);
        assertEquals(4, dna.findLevel(dna.root, "AAAA".toCharArray(), 0));
        assertEquals(4, dna.findLevel(dna.root, "ACTTA".toCharArray(), 0));
        assertEquals(4, dna.findLevel(dna.root, "ACTGGGAA".toCharArray(), 0));
        assertEquals(2, dna.findLevel(dna.root, "TATA".toCharArray(), 0));
        assertEquals(2, dna.findLevel(dna.root, "TCG".toCharArray(), 0));
        assertEquals(3, dna.findLevel(dna.root, "AA".toCharArray(), 0));
        assertEquals(3, dna.findLevel(dna.root, "ACCTT".toCharArray(), 0));
        assertEquals(3, dna.findLevel(dna.root, "ACGT".toCharArray(), 0));

        assertEquals(2, dna.findLevelExactNode(dna.root, "ATACC".toCharArray(),
            0));
        // random sequence searches
        // assertEquals(0, dna.findLevel(dna.root, "BEW".toCharArray(), 0));
        assertEquals(0, dna2.findLevel(dna2.root, "GGCG".toCharArray(), 0));
        dna2.insert("ACCC", 0);
        assertEquals(0, dna2.findLevel(dna2.root, "GGCG".toCharArray(), 0));
        dna2.insert("AAAC", 0);
        assertEquals(1, dna2.findLevel(dna2.root, "GGCG".toCharArray(), 0));
        // edge cases
        assertEquals(2, dna2.findLevel(dna2.root, "ACCTTAC".toCharArray(), 0));

        dna2.root.printNew(0);

    }

}
